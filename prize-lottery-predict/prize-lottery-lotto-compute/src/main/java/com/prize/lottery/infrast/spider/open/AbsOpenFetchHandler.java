package com.prize.lottery.infrast.spider.open;

import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.mapper.MasterInfoMapper;
import com.prize.lottery.po.master.MasterLotteryPo;
import com.prize.lottery.value.ICaiForecast;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;

public abstract class AbsOpenFetchHandler implements FetchEventHandler {

    private final   Random           random;
    protected final LotteryEnum      lottery;
    protected final MasterInfoMapper masterInfoMapper;

    public AbsOpenFetchHandler(LotteryEnum lottery, MasterInfoMapper masterInfoMapper) {
        this.lottery          = lottery;
        this.masterInfoMapper = masterInfoMapper;
        this.random           = new Random();
    }

    public LocalDateTime getRandomTime(int bound) {
        return Instant.ofEpochMilli(System.currentTimeMillis() - random.nextInt(bound))
                      .atZone(ZoneOffset.ofHours(8))
                      .toLocalDateTime();
    }

    /**
     * 处理获取的预测数据
     *
     * @param data 预测数据
     */
    @Override
    public void handle(ICaiForecast data) {
        MasterLotteryPo lottery = new MasterLotteryPo();
        lottery.setType(data.getType().getType());
        lottery.setMasterId(data.getMasterId());
        lottery.setLatest(data.getPeriod());
        masterInfoMapper.editMasterLatestPeriod(lottery);
    }

    /**
     * 选三型胆码标识器
     *
     * @param period 期号
     */
    public DanMarker danMarker(String period) {
        return null;
    }

}
