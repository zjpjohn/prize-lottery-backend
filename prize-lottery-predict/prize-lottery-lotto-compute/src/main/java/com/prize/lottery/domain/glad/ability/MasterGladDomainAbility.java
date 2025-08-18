package com.prize.lottery.domain.glad.ability;

import com.cloud.arch.executor.EnumerableExecutorFactory;
import com.google.common.collect.Lists;
import com.prize.lottery.domain.glad.domain.MasterGladDo;
import com.prize.lottery.domain.glad.event.GladExtractEvent;
import com.prize.lottery.domain.glad.extractor.GladExtractor;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.mapper.MasterGladMapper;
import com.prize.lottery.po.master.MasterGladPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class MasterGladDomainAbility {

    private final MasterGladMapper                                      gladMapper;
    private final EnumerableExecutorFactory<LotteryEnum, GladExtractor> executors;

    public void removeGlads(Integer day) {
        gladMapper.removeBeforeTime(LocalDateTime.now().minusDays(day));
    }

    /**
     * 提取全部彩种的专家预测喜讯
     */
    public void extract() {
        List<CompletableFuture<?>> futures = Lists.newArrayList();
        for (GladExtractor extractor : executors.executors()) {
            CompletableFuture<?> runAsync = CompletableFuture.runAsync(() -> {
                List<MasterGladPo> poList = extractor.extract()
                                                     .stream()
                                                     .map(MasterGladDo::convert)
                                                     .collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(poList)) {
                    gladMapper.addMasterGladList(poList);
                }
            });
            futures.add(runAsync);
        }
        CompletableFuture<?>[] completableFutures = futures.toArray(new CompletableFuture[0]);
        CompletableFuture.allOf(completableFutures).join();
    }

    /**
     * 提取指定彩种的专家预测喜讯
     */
    @EventListener(GladExtractEvent.class)
    public void extract(GladExtractEvent event) {
        List<MasterGladDo> gladList = executors.ofNullable(event.getLottery())
                                               .map(GladExtractor::extract)
                                               .orElseGet(Collections::emptyList);
        if (!CollectionUtils.isEmpty(gladList)) {
            List<MasterGladPo> gladPoList = gladList.stream().map(MasterGladDo::convert).collect(Collectors.toList());
            gladMapper.addMasterGladList(gladPoList);
        }
    }

}
