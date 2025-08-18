package com.prize.lottery.domain.feeds.ability;

import com.cloud.arch.executor.EnumerableExecutorFactory;
import com.prize.lottery.domain.feeds.event.MasterFeedEvent;
import com.prize.lottery.domain.feeds.extractor.FeedsExtractor;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.mapper.MasterFeedsMapper;
import com.prize.lottery.po.master.MasterFeedsPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MasterFeedsDomainAbility {

    private final MasterFeedsMapper                                      mapper;
    private final EnumerableExecutorFactory<LotteryEnum, FeedsExtractor> executors;

    /**
     * 设置信息流发布时间
     *
     * @param feedList 信息流集合
     * @param range    时间范围
     */
    private void shuffleTime(List<MasterFeedsPo> feedList, int range) {
        LocalDateTime now    = LocalDateTime.now();
        Random        random = new Random();
        feedList.forEach(feed -> {
            LocalDateTime time = now.minusSeconds(random.nextInt(range));
            feed.setGmtCreate(time);
            feed.setGmtModify(time);
        });
    }

    /**
     * 提取全部彩种信息流
     */
    @Transactional
    public void extract() {
        //提取最新期的专家信息流
        List<MasterFeedsPo> feedList = executors.executors()
                                                .parallelStream()
                                                .flatMap(ex -> ex.extract().stream())
                                                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(feedList)) {
            return;
        }
        Collections.shuffle(feedList);
        shuffleTime(feedList, 28800);
        mapper.addMasterFeeds(feedList);
        //删除旧的专家信息流
        List<Map<String, String>> maxPeriods = mapper.getFeedsMaxPeriods();
        if (!CollectionUtils.isEmpty(maxPeriods)) {
            mapper.removeTypedFeedsLtPeriods(maxPeriods);
        }
    }

    /**
     * 提取指定彩种专家信息流
     */
    @Transactional
    public void extract(LotteryEnum type) {
        List<MasterFeedsPo> feedList = executors.ofNullable(type)
                                                .map(FeedsExtractor::extract)
                                                .orElseGet(Collections::emptyList);
        if (CollectionUtils.isEmpty(feedList)) {
            log.warn("没有提取到最新期的专家信息流");
            return;
        }
        shuffleTime(feedList, 7200);
        mapper.addMasterFeeds(feedList);
        //删除就的专家信息流
        String maxPeriod = mapper.getFeedsMaxPeriod(type.getType());
        if (StringUtils.hasText(maxPeriod)) {
            mapper.removeFeedsLtPeriod(type.getType(), maxPeriod);
        }
    }

    @Async
    @EventListener(MasterFeedEvent.class)
    public void modify(MasterFeedEvent event) {
        MasterFeedsPo masterFeed = mapper.getMasterFeedsByUk(event.getType(), event.getMasterId());
        if (masterFeed == null) {
            return;
        }
        //当信息流的期号比计算命中率的期号小，删除本条信息流
        String lastPeriod = event.getType().lastPeriod(event.getPeriod());
        if (lastPeriod.compareTo(masterFeed.getPeriod()) > 0) {
            mapper.removeFeedsRecord(event.getType().getType(), event.getMasterId());
            return;
        }
        executors.ofNullable(event.getType())
                 .map(ex -> ex.modify(event.getMasterId(), event.getPeriod()))
                 .ifPresent(feed -> {
                     feed.setId(masterFeed.getId());
                     mapper.editMasterFeeds(feed);
                 });
    }

    public void clearFeeds(LotteryEnum type, Double rate) {
        mapper.removeMasterFeeds(type, rate, null, null);
    }

}
