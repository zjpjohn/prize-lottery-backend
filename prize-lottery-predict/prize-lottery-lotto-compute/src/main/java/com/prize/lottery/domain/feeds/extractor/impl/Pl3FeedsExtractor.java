package com.prize.lottery.domain.feeds.extractor.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.prize.lottery.domain.feeds.extractor.FeedsExtractor;
import com.prize.lottery.domain.feeds.valobj.FeedTemplate;
import com.prize.lottery.enums.FeedsType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.enums.Pl3Channel;
import com.prize.lottery.mapper.Pl3IcaiMapper;
import com.prize.lottery.mapper.Pl3MasterMapper;
import com.prize.lottery.po.master.MasterFeedsPo;
import com.prize.lottery.po.pl3.Pl3IcaiPo;
import com.prize.lottery.vo.MasterFeedRateVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class Pl3FeedsExtractor implements FeedsExtractor {

    private final LotteryEnum     type;
    private final Pl3IcaiMapper   pl3IcaiMapper;
    private final Pl3MasterMapper pl3MasterMapper;

    public Pl3FeedsExtractor(Pl3IcaiMapper pl3IcaiMapper, Pl3MasterMapper pl3MasterMapper) {
        this.type            = LotteryEnum.PL3;
        this.pl3IcaiMapper   = pl3IcaiMapper;
        this.pl3MasterMapper = pl3MasterMapper;
    }

    @Override
    public LotteryEnum bizIndex() {
        return this.type;
    }

    @Override
    public LocalDateTime modifyTime(String masterId, String period) {
        return Optional.ofNullable(pl3IcaiMapper.getPl3ICaiData(masterId, period))
                       .filter(data -> data.getCalcTime() == null)
                       .map(Pl3IcaiPo::getGmtCreate)
                       .orElse(null);
    }

    @Override
    public MasterFeedsPo extract(String channel, String period, String masterId) {
        MasterFeedRateVo masterFeed = pl3MasterMapper.getPl3MasterFeed(channel, period, masterId);
        if (masterFeed == null) {
            return null;
        }
        switch (channel) {
            case "c7":
                return buildC7(masterFeed);
            case "d3":
                return buildD3(masterFeed);
            case "k1":
                return buildK1(masterFeed);
        }
        return null;
    }

    @Override
    public List<MasterFeedsPo> extract() {
        String period = pl3MasterMapper.latestPl3RatePeriod();
        if (StringUtils.isBlank(period)) {
            return Collections.emptyList();
        }
        List<MasterFeedRateVo> masterFeeds = pl3MasterMapper.getPl3MasterFeeds(period);
        Map<String, List<MasterFeedRateVo>> groupFeeds = masterFeeds.stream()
                                                                    .collect(Collectors.groupingBy(MasterFeedRateVo::getField));
        Set<String>         masterIds = Sets.newHashSet();
        List<MasterFeedsPo> feedList  = Lists.newArrayList();

        //七码内容
        groupFeeds.get(Pl3Channel.COM7.getChannel()).forEach(rate -> {
            masterIds.add(rate.getMasterId());
            feedList.add(buildC7(rate));
        });
        //杀一码内容
        groupFeeds.get(Pl3Channel.KILL1.getChannel())
                  .stream()
                  .filter(rate -> !masterIds.contains(rate.getMasterId()))
                  .forEach(rate -> {
                      masterIds.add(rate.getMasterId());
                      feedList.add(buildK1(rate));
                  });
        //三胆内容
        groupFeeds.get(Pl3Channel.DAN3.getChannel())
                  .stream()
                  .filter(rate -> !masterIds.contains(rate.getMasterId()))
                  .forEach(rate -> {
                      masterIds.add(rate.getMasterId());
                      feedList.add(buildD3(rate));
                  });

        Collections.shuffle(feedList);
        return feedList;
    }

    public MasterFeedsPo buildC7(MasterFeedRateVo rate) {
        MasterFeedsPo feeds    = build(rate, FeedsType.COM);
        int           hitRate  = Double.valueOf(rate.getRate() * 100).intValue();
        String        rateText = FeedTemplate.superTitle(Pl3Channel.COM7.getNameZh(), hitRate);
        feeds.setRateText(rateText);

        String hitText = FeedTemplate.hitContent(rate.getPeriod(), type, Pl3Channel.COM7.getNameZh(), "预测中奖");
        feeds.setHitText(hitText);
        return feeds;
    }

    public MasterFeedsPo buildD3(MasterFeedRateVo rate) {
        MasterFeedsPo feeds    = build(rate, FeedsType.DAN);
        int           hitRate  = Double.valueOf(rate.getRate() * 100).intValue();
        String        rateText = FeedTemplate.niuTitle(Pl3Channel.DAN3.getNameZh(), hitRate);
        feeds.setRateText(rateText);

        String hitContent = String.format("3胆命中%d球", rate.getHit());
        String hitText    = FeedTemplate.hitContent(rate.getPeriod(), type, Pl3Channel.DAN3.getNameZh(), hitContent);
        feeds.setHitText(hitText);
        return feeds;
    }

    public MasterFeedsPo buildK1(MasterFeedRateVo rate) {
        MasterFeedsPo feeds    = build(rate, FeedsType.KILL);
        int           hitRate  = Double.valueOf(rate.getRate() * 100).intValue();
        String        rateText = FeedTemplate.superTitle("杀一码", hitRate);
        feeds.setRateText(rateText);

        String hitText = FeedTemplate.hitContent(rate.getPeriod(), type, Pl3Channel.KILL1.getNameZh(), "杀码命中");
        feeds.setHitText(hitText);
        return feeds;
    }
}
