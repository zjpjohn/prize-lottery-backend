package com.prize.lottery.domain.feeds.extractor.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.prize.lottery.domain.feeds.extractor.FeedsExtractor;
import com.prize.lottery.domain.feeds.valobj.FeedTemplate;
import com.prize.lottery.enums.FeedsType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.enums.SsqChannel;
import com.prize.lottery.mapper.SsqIcaiMapper;
import com.prize.lottery.mapper.SsqMasterMapper;
import com.prize.lottery.po.master.MasterFeedsPo;
import com.prize.lottery.po.ssq.SsqIcaiPo;
import com.prize.lottery.vo.MasterFeedRateVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SsqFeedsExtractor implements FeedsExtractor {

    private final LotteryEnum     type;
    private final SsqIcaiMapper   ssqIcaiMapper;
    private final SsqMasterMapper ssqMasterMapper;

    public SsqFeedsExtractor(SsqIcaiMapper ssqIcaiMapper, SsqMasterMapper ssqMasterMapper) {
        this.type            = LotteryEnum.SSQ;
        this.ssqIcaiMapper   = ssqIcaiMapper;
        this.ssqMasterMapper = ssqMasterMapper;
    }

    @Override
    public LotteryEnum bizIndex() {
        return this.type;
    }

    @Override
    public LocalDateTime modifyTime(String masterId, String period) {
        return Optional.ofNullable(ssqIcaiMapper.getSsqICaiData(masterId, period))
                       .filter(data -> data.getCalcTime() == null)
                       .map(SsqIcaiPo::getGmtCreate)
                       .orElse(null);
    }

    @Override
    public MasterFeedsPo extract(String channel, String period, String masterId) {
        MasterFeedRateVo masterFeed = ssqMasterMapper.getSsqMasterFeed(channel, period, masterId);
        if (masterFeed == null) {
            return null;
        }
        switch (channel) {
            case "r25":
                return buildR25(masterFeed);
            case "r20":
                return buildR20(masterFeed);
            case "rk3":
                return buildRk3(masterFeed);
        }
        return null;
    }

    @Override
    public List<MasterFeedsPo> extract() {
        String period = ssqMasterMapper.latestSsqRatePeriod();
        if (StringUtils.isBlank(period)) {
            return Collections.emptyList();
        }
        List<MasterFeedRateVo> masterFeeds = ssqMasterMapper.getSsqMasterFeeds(period);
        Map<String, List<MasterFeedRateVo>> groupFeeds = masterFeeds.stream()
                                                                    .collect(Collectors.groupingBy(MasterFeedRateVo::getField));
        Set<String>         masterIds = Sets.newHashSet();
        List<MasterFeedsPo> feedList  = Lists.newArrayList();

        //红球25码
        groupFeeds.get(SsqChannel.RED25.getChannel()).forEach(rate -> {
            masterIds.add(rate.getMasterId());
            feedList.add(buildR25(rate));
        });
        //红球杀三码
        groupFeeds.get(SsqChannel.RK3.getChannel())
                  .stream()
                  .filter(rate -> !masterIds.contains(rate.getMasterId()))
                  .forEach(rate -> {
                      masterIds.add(rate.getMasterId());
                      feedList.add(buildRk3(rate));
                  });
        //红球20码
        groupFeeds.get(SsqChannel.RED20.getChannel())
                  .stream()
                  .filter(rate -> !masterIds.contains(rate.getMasterId()))
                  .forEach(rate -> {
                      masterIds.add(rate.getMasterId());
                      feedList.add(buildR20(rate));
                  });
        Collections.shuffle(feedList);
        return feedList;
    }

    public MasterFeedsPo buildR25(MasterFeedRateVo rate) {
        MasterFeedsPo feeds = build(rate, FeedsType.COM);

        int    hitRate  = Double.valueOf(rate.getRate() * 100).intValue();
        String rateText = FeedTemplate.superTitle(SsqChannel.RED25.getNameZh(), hitRate);
        feeds.setRateText(rateText);

        String hitContent = String.format("命中%d球", rate.getHit());
        String hitText    = FeedTemplate.hitContent(rate.getPeriod(), type, SsqChannel.RED25.getNameZh(), hitContent);
        feeds.setHitText(hitText);
        return feeds;
    }

    public MasterFeedsPo buildR20(MasterFeedRateVo rate) {
        MasterFeedsPo feeds = build(rate, FeedsType.COM);

        int    hitRate  = Double.valueOf(rate.getRate() * 100).intValue();
        String rateText = FeedTemplate.niuTitle(SsqChannel.RED20.getNameZh(), hitRate);
        feeds.setRateText(rateText);

        String hitContent = String.format("命中%d球", rate.getHit());
        String hitText    = FeedTemplate.hitContent(rate.getPeriod(), type, SsqChannel.RED20.getNameZh(), hitContent);
        feeds.setHitText(hitText);
        return feeds;
    }

    public MasterFeedsPo buildRk3(MasterFeedRateVo rate) {
        MasterFeedsPo feeds = build(rate, FeedsType.KILL);

        int    hitRate  = Double.valueOf(rate.getRate() * 100).intValue();
        String rateText = FeedTemplate.niuTitle(SsqChannel.RED20.getNameZh(), hitRate);
        feeds.setRateText(rateText);

        String hitContent = String.format("命中%d球", rate.getHit());
        String hitText    = FeedTemplate.hitContent(rate.getPeriod(), type, SsqChannel.RED20.getNameZh(), hitContent);
        feeds.setHitText(hitText);
        return feeds;
    }

}
