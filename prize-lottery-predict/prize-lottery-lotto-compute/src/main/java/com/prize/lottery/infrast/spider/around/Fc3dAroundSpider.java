package com.prize.lottery.infrast.spider.around;

import com.prize.lottery.delay.AbsDelayTaskExecutor;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.mapper.LotteryDanMapper;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.po.lottery.LotteryAroundPo;
import com.prize.lottery.po.lottery.LotteryInfoPo;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class Fc3dAroundSpider extends AbsDelayTaskExecutor<AroundTask> {

    private final LotteryDanMapper  aroundMapper;
    private final LotteryInfoMapper lotteryMapper;

    public Fc3dAroundSpider(Executor executor, LotteryDanMapper aroundMapper, LotteryInfoMapper lotteryMapper) {
        super(executor);
        this.aroundMapper  = aroundMapper;
        this.lotteryMapper = lotteryMapper;
    }

    @Override
    public void executeRequest(AroundTask request) {
        this.fetchAround(request.getPeriod());
    }

    /**
     * 抓取指定期的绕胆数据
     */
    public void fetchAround(String period) {
        LotteryAroundPo around  = Fc3dAroundFetcher.fetch(period);
        LotteryInfoPo   lottery = lotteryMapper.getLotteryInfo(LotteryEnum.FC3D.getType(), around.getPeriod());
        if (lottery != null) {
            List<String> balls = lottery.redBalls();
            around.setResult(around.getAround().calc(balls));
        }
        aroundMapper.addLotteryAround(around);
    }

    /**
     * 抓取指定期3D绕胆图
     */
    public void fetchAround(Integer size, Integer range) {
        String period = lotteryMapper.latestPeriod(LotteryEnum.FC3D.getType());
        List<String> periods = IntStream.range(0, size)
                                        .mapToObj((i) -> LotteryEnum.FC3D.lastPeriod(period, i))
                                        .collect(Collectors.toList());
        fetchAsync(periods, System.currentTimeMillis(), range);
    }

    /**
     * 异步抓取绕胆数据
     *
     * @param periods 期号集合
     * @param millis  开始抓取时间
     * @param range   分散时间范围
     */
    public void fetchAsync(List<String> periods, long millis, int range) {
        int    bound  = range * 1000;
        Random random = new Random();
        for (String period : periods) {
            long       timestamp = millis + random.nextInt(bound);
            AroundTask task      = new AroundTask(timestamp, period);
            this.delayExe(task);
        }
    }
}
