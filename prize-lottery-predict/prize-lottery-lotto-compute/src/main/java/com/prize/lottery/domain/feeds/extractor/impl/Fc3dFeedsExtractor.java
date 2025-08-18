package com.prize.lottery.domain.feeds.extractor.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.prize.lottery.domain.feeds.extractor.FeedsExtractor;
import com.prize.lottery.domain.feeds.valobj.FeedTemplate;
import com.prize.lottery.enums.Fc3dChannel;
import com.prize.lottery.enums.FeedsType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.mapper.Fc3dIcaiMapper;
import com.prize.lottery.mapper.Fc3dMasterMapper;
import com.prize.lottery.po.fc3d.Fc3dIcaiPo;
import com.prize.lottery.po.master.MasterFeedsPo;
import com.prize.lottery.vo.MasterFeedRateVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class Fc3dFeedsExtractor implements FeedsExtractor {

    private final LotteryEnum      type;
    private final Fc3dIcaiMapper   fc3dIcaiMapper;
    private final Fc3dMasterMapper fc3dMasterMapper;

    public Fc3dFeedsExtractor(Fc3dIcaiMapper fc3dIcaiMapper, Fc3dMasterMapper fc3dMasterMapper) {
        this.type             = LotteryEnum.FC3D;
        this.fc3dIcaiMapper   = fc3dIcaiMapper;
        this.fc3dMasterMapper = fc3dMasterMapper;
    }

    @Override
    public LotteryEnum bizIndex() {
        return this.type;
    }

    @Override
    public LocalDateTime modifyTime(String masterId, String period) {
        return Optional.ofNullable(fc3dIcaiMapper.getFc3dICaiData(masterId, period))
                       .filter(data -> data.getCalcTime() == null)
                       .map(Fc3dIcaiPo::getGmtCreate)
                       .orElse(null);
    }

    @Override
    public MasterFeedsPo extract(String channel, String period, String masterId) {
        MasterFeedRateVo masterFeed = fc3dMasterMapper.getFc3dMasterFeed(channel, period, masterId);
        if (masterFeed == null) {
            return null;
        }
        switch (channel) {
            case "c7":
                return buildCom7(masterFeed);
            case "d3":
                return buildDan3(masterFeed);
            case "k1":
                return buildKill1(masterFeed);
        }
        return null;
    }

    @Override
    public List<MasterFeedsPo> extract() {
        String period = fc3dMasterMapper.latestFc3dRatePeriod();
        if (StringUtils.isBlank(period)) {
            return Collections.emptyList();
        }
        List<MasterFeedRateVo> masterFeeds = fc3dMasterMapper.getFc3dMasterFeeds(period);
        Map<String, List<MasterFeedRateVo>> groupFeeds = masterFeeds.stream()
                                                                    .collect(Collectors.groupingBy(MasterFeedRateVo::getField));
        Set<String>         masterIds = Sets.newHashSet();
        List<MasterFeedsPo> feedList  = Lists.newArrayList();

        //七码内容
        groupFeeds.get(Fc3dChannel.COM7.getChannel()).forEach(rate -> {
            masterIds.add(rate.getMasterId());
            feedList.add(buildCom7(rate));
        });
        //杀一码内容
        groupFeeds.get(Fc3dChannel.KILL1.getChannel())
                  .stream()
                  .filter(rate -> !masterIds.contains(rate.getMasterId()))
                  .forEach(rate -> {
                      masterIds.add(rate.getMasterId());
                      feedList.add(buildKill1(rate));
                  });
        //三胆内容
        groupFeeds.get(Fc3dChannel.DAN3.getChannel())
                  .stream()
                  .filter(rate -> !masterIds.contains(rate.getMasterId()))
                  .forEach(rate -> {
                      masterIds.add(rate.getMasterId());
                      feedList.add(buildDan3(rate));
                  });
        Collections.shuffle(feedList);
        return feedList;
    }

    public MasterFeedsPo buildCom7(MasterFeedRateVo rate) {
        MasterFeedsPo feeds    = build(rate, FeedsType.COM);
        int           hitRate  = Double.valueOf(rate.getRate() * 100).intValue();
        String        rateText = FeedTemplate.superTitle(Fc3dChannel.COM7.getNameZh(), hitRate);
        feeds.setRateText(rateText);

        String hitContent = String.format("命中%d球", rate.getHit());
        String hitText    = FeedTemplate.hitContent(rate.getPeriod(), type, Fc3dChannel.COM7.getNameZh(), hitContent);
        feeds.setHitText(hitText);
        return feeds;
    }

    public MasterFeedsPo buildKill1(MasterFeedRateVo rate) {
        MasterFeedsPo feeds    = build(rate, FeedsType.KILL);
        int           hitRate  = Double.valueOf(rate.getRate() * 100).intValue();
        String        rateText = FeedTemplate.superTitle("杀一码", hitRate);
        feeds.setRateText(rateText);

        String hitText = FeedTemplate.hitContent(rate.getPeriod(), type, Fc3dChannel.KILL1.getNameZh(), "杀码命中");
        feeds.setHitText(hitText);
        return feeds;
    }

    public MasterFeedsPo buildDan3(MasterFeedRateVo rate) {
        MasterFeedsPo feeds    = build(rate, FeedsType.DAN);
        int           hitRate  = Double.valueOf(rate.getRate() * 100).intValue();
        String        rateText = FeedTemplate.niuTitle(Fc3dChannel.DAN3.getNameZh(), hitRate);
        feeds.setRateText(rateText);

        String hitContent = String.format("3胆命中%d球", rate.getHit());
        String hitText    = FeedTemplate.hitContent(rate.getPeriod(), type, Fc3dChannel.DAN3.getNameZh(), hitContent);
        feeds.setHitText(hitText);
        return feeds;
    }

}
