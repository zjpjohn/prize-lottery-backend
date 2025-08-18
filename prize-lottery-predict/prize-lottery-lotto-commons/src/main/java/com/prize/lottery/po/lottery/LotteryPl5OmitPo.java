package com.prize.lottery.po.lottery;

import com.prize.lottery.value.Omit;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LotteryPl5OmitPo {

    private Long          id;
    private String        period;
    private String        balls;
    private Omit          cb1Amp;
    private Omit          cb1Aod;
    private Omit          cb1Bos;
    private Omit          cb1Oe;
    private Omit          cb2Amp;
    private Omit          cb2Aod;
    private Omit          cb2Bos;
    private Omit          cb2Oe;
    private Omit          cb3Amp;
    private Omit          cb3Aod;
    private Omit          cb3Bos;
    private Omit          cb3Oe;
    private Omit          cb4Amp;
    private Omit          cb4Aod;
    private Omit          cb4Bos;
    private Omit          cb4Oe;
    private Omit          cb5Amp;
    private Omit          cb5Aod;
    private Omit          cb5Bos;
    private Omit          cb5Oe;
    private LocalDateTime gmtCreate;

    /**
     * 分位基础遗漏数据
     */
    private Omit cb1;
    private Omit cb2;
    private Omit cb3;
    private Omit cb4;
    private Omit cb5;

}
