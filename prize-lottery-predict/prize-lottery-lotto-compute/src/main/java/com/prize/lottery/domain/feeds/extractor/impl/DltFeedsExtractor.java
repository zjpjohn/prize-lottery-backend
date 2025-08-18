package com.prize.lottery.domain.feeds.extractor.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.prize.lottery.domain.feeds.extractor.FeedsExtractor;
import com.prize.lottery.domain.feeds.valobj.FeedTemplate;
import com.prize.lottery.enums.DltChannel;
import com.prize.lottery.enums.FeedsType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.mapper.DltIcaiMapper;
import com.prize.lottery.mapper.DltMasterMapper;
import com.prize.lottery.po.dlt.DltIcaiPo;
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
public class DltFeedsExtractor implements FeedsExtractor {

    private final LotteryEnum     type;
    private final DltIcaiMapper   dltIcaiMapper;
    private final DltMasterMapper dltMasterMapper;

    public DltFeedsExtractor(DltIcaiMapper dltIcaiMapper, DltMasterMapper dltMasterMapper) {
        this.type            = LotteryEnum.DLT;
        this.dltIcaiMapper   = dltIcaiMapper;
        this.dltMasterMapper = dltMasterMapper;
    }

    @Override
    public LotteryEnum bizIndex() {
        return this.type;
    }

    @Override
    public LocalDateTime modifyTime(String masterId, String period) {
        return Optional.ofNullable(dltIcaiMapper.getDltICaiData(masterId, period))
                       .filter(data -> data.getCalcTime() == null)
                       .map(DltIcaiPo::getGmtCreate)
                       .orElse(null);
    }

    @Override
    public MasterFeedsPo extract(String channel, String period, String masterId) {
        MasterFeedRateVo masterFeed = dltMasterMapper.getDltMasterFeed(channel, period, masterId);
        if (masterFeed == null) {
            return null;
        }
        switch (channel) {
            case "r20":
                return buildR20(masterFeed);
            case "rk3":
                return buildRk3(masterFeed);
            case "b6":
                return buildB6(masterFeed);
        }
        return null;
    }

    @Override
    public List<MasterFeedsPo> extract() {
        String period = dltMasterMapper.latestDltRatePeriod();
        if (StringUtils.isBlank(period)) {
            return Collections.emptyList();
        }
        List<MasterFeedRateVo> masterFeeds = dltMasterMapper.getDltMasterFeeds(period);
        Map<String, List<MasterFeedRateVo>> groupFeeds = masterFeeds.stream()
                                                                    .collect(Collectors.groupingBy(MasterFeedRateVo::getField));
        Set<String>         masterIds = Sets.newHashSet();
        List<MasterFeedsPo> feedList  = Lists.newArrayList();

        //红球20码
        groupFeeds.get(DltChannel.RED20.getChannel()).forEach(rate -> {
            masterIds.add(rate.getMasterId());
            feedList.add(buildR20(rate));
        });

        //红球杀三码
        groupFeeds.get(DltChannel.RK3.getChannel())
                  .stream()
                  .filter(rate -> !masterIds.contains(rate.getMasterId()))
                  .forEach(rate -> {
                      masterIds.add(rate.getMasterId());
                      feedList.add(buildRk3(rate));
                  });
        ///蓝球6码
        groupFeeds.get(DltChannel.BLUE6.getChannel())
                  .stream()
                  .filter(rate -> !masterIds.contains(rate.getMasterId()))
                  .forEach(rate -> {
                      masterIds.add(rate.getMasterId());
                      feedList.add(buildB6(rate));
                  });
        Collections.shuffle(feedList);
        return feedList;
    }

    public MasterFeedsPo buildR20(MasterFeedRateVo rate) {
        MasterFeedsPo feeds = build(rate, FeedsType.COM);

        int    hitRate  = Double.valueOf(rate.getRate() * 100).intValue();
        String rateText = FeedTemplate.superTitle(DltChannel.RED20.getNameZh(), hitRate);
        feeds.setRateText(rateText);

        String hitContent = String.format("命中%d球", rate.getHit());
        String hitText    = FeedTemplate.hitContent(rate.getPeriod(), type, DltChannel.RED20.getNameZh(), hitContent);
        feeds.setHitText(hitText);
        return feeds;
    }

    public MasterFeedsPo buildRk3(MasterFeedRateVo rate) {
        MasterFeedsPo feeds = build(rate, FeedsType.KILL);

        int    hitRate  = Double.valueOf(rate.getRate() * 100).intValue();
        String rateText = FeedTemplate.superTitle(DltChannel.RK3.getNameZh(), hitRate);
        feeds.setRateText(rateText);

        String hitText = FeedTemplate.hitContent(rate.getPeriod(), type, DltChannel.RK3.getNameZh(), "杀码命中");
        feeds.setHitText(hitText);
        return feeds;
    }

    public MasterFeedsPo buildB6(MasterFeedRateVo rate) {
        MasterFeedsPo feeds = build(rate, FeedsType.BLUE);

        int    hitRate  = Double.valueOf(rate.getRate() * 100).intValue();
        String rateText = FeedTemplate.niuTitle(DltChannel.BLUE6.getNameZh(), hitRate);
        feeds.setRateText(rateText);

        String hitContent = String.format("命中%d球", rate.getHit());
        String hitText    = FeedTemplate.hitContent(rate.getPeriod(), type, "蓝球六码", hitContent);
        feeds.setHitText(hitText);
        return feeds;
    }
}
