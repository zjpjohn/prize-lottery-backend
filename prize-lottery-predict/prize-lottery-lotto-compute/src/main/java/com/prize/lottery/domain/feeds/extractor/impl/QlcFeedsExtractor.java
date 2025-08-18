package com.prize.lottery.domain.feeds.extractor.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.prize.lottery.domain.feeds.extractor.FeedsExtractor;
import com.prize.lottery.domain.feeds.valobj.FeedTemplate;
import com.prize.lottery.enums.FeedsType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.enums.QlcChannel;
import com.prize.lottery.mapper.QlcIcaiMapper;
import com.prize.lottery.mapper.QlcMasterMapper;
import com.prize.lottery.po.master.MasterFeedsPo;
import com.prize.lottery.po.qlc.QlcIcaiPo;
import com.prize.lottery.vo.MasterFeedRateVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class QlcFeedsExtractor implements FeedsExtractor {

    private final LotteryEnum     type;
    private final QlcIcaiMapper   qlcIcaiMapper;
    private final QlcMasterMapper qlcMasterMapper;

    public QlcFeedsExtractor(QlcIcaiMapper qlcIcaiMapper, QlcMasterMapper qlcMasterMapper) {
        this.type            = LotteryEnum.QLC;
        this.qlcIcaiMapper   = qlcIcaiMapper;
        this.qlcMasterMapper = qlcMasterMapper;
    }

    @Override
    public LotteryEnum bizIndex() {
        return this.type;
    }

    @Override
    public LocalDateTime modifyTime(String masterId, String period) {
        return Optional.ofNullable(qlcIcaiMapper.getQlcICaiData(masterId, period))
                       .filter(data -> data.getCalcTime() == null)
                       .map(QlcIcaiPo::getGmtCreate)
                       .orElse(null);
    }

    @Override
    public MasterFeedsPo extract(String channel, String period, String masterId) {
        MasterFeedRateVo masterFeed = qlcMasterMapper.getQlcMasterFeed(channel, period, masterId);
        if (masterFeed == null) {
            return null;
        }
        switch (channel) {
            case "r18":
                return buildR18(masterFeed);
            case "r22":
                return buildR22(masterFeed);
            case "r3":
                return buildR3(masterFeed);
        }
        return null;
    }

    @Override
    public List<MasterFeedsPo> extract() {
        String period = qlcMasterMapper.latestQlcRatePeriod();
        if (StringUtils.isBlank(period)) {
            return Collections.emptyList();
        }
        List<MasterFeedRateVo> masterFeeds = qlcMasterMapper.getQlcMasterFeeds(period);
        Map<String, List<MasterFeedRateVo>> groupFeeds = masterFeeds.stream()
                                                                    .collect(Collectors.groupingBy(MasterFeedRateVo::getField));
        Set<String>         masterIds = Sets.newHashSet();
        List<MasterFeedsPo> feedList  = Lists.newArrayList();

        //18码内容
        groupFeeds.get(QlcChannel.RED18.getChannel()).forEach(rate -> {
            masterIds.add(rate.getMasterId());
            feedList.add(buildR18(rate));
        });

        //22码内容
        groupFeeds.get(QlcChannel.RED22.getChannel())
                  .stream()
                  .filter(rate -> !masterIds.contains(rate.getMasterId()))
                  .forEach(rate -> {
                      masterIds.add(rate.getMasterId());
                      feedList.add(buildR22(rate));
                  });
        //三胆内容
        groupFeeds.get(QlcChannel.RED3.getChannel())
                  .stream()
                  .filter(rate -> !masterIds.contains(rate.getMasterId()))
                  .forEach(rate -> {
                      masterIds.add(rate.getMasterId());
                      feedList.add(buildR3(rate));
                  });

        Collections.shuffle(feedList);
        return feedList;
    }

    public MasterFeedsPo buildR18(MasterFeedRateVo rate) {
        MasterFeedsPo feeds    = build(rate, FeedsType.COM);
        int           hitRate  = Double.valueOf(rate.getRate() * 100).intValue();
        String        rateText = FeedTemplate.superTitle(QlcChannel.RED18.getNameZh(), hitRate);
        feeds.setRateText(rateText);

        String hitContent = String.format("命中%d球", rate.getHit());
        String hitText    = FeedTemplate.hitContent(rate.getPeriod(), type, QlcChannel.RED18.getNameZh(), hitContent);
        feeds.setHitText(hitText);
        return feeds;
    }

    public MasterFeedsPo buildR22(MasterFeedRateVo rate) {
        MasterFeedsPo feeds    = build(rate, FeedsType.COM);
        int           hitRate  = Double.valueOf(rate.getRate() * 100).intValue();
        String        rateText = FeedTemplate.superTitle(QlcChannel.RED22.getNameZh(), hitRate);
        feeds.setRateText(rateText);

        String hitContent = String.format("命中%d球", rate.getHit());
        String hitText    = FeedTemplate.hitContent(rate.getPeriod(), type, QlcChannel.RED22.getNameZh(), hitContent);
        feeds.setHitText(hitText);
        return feeds;
    }

    public MasterFeedsPo buildR3(MasterFeedRateVo rate) {
        MasterFeedsPo feeds    = build(rate, FeedsType.DAN);
        int           hitRate  = Double.valueOf(rate.getRate() * 100).intValue();
        String        rateText = FeedTemplate.niuTitle(QlcChannel.RED3.getNameZh(), hitRate);
        feeds.setRateText(rateText);

        String hitContent = String.format("3胆命中%d球", rate.getHit());
        String hitText    = FeedTemplate.hitContent(rate.getPeriod(), type, QlcChannel.RED3.getNameZh(), hitContent);
        feeds.setHitText(hitText);
        return feeds;
    }

}
