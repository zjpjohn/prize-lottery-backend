package com.prize.lottery.domain.lottery.model;

import com.prize.lottery.domain.lottery.value.LevelValue;
import com.prize.lottery.enums.LotteryEnum;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Getter
public class LotteryInfoDo {

    private String           period;
    //彩种类型
    private LotteryEnum      lottery;
    //红球号码
    private String           red;
    //篮球号码
    private String           blue;
    //本期销售额
    private Long             sales;
    //本期中奖金额
    private Long             award;
    //奖池剩余金额
    private Long             pool;
    //开奖日期
    private LocalDate        lotDate;
    //中奖等级
    private List<LevelValue> levels;

    public void setPeriod(String period) {
        this.period = period;
    }

    public void setLottery(LotteryEnum lottery) {
        this.lottery = lottery;
    }

    public void setRed(String red) {
        if (StringUtils.isBlank(red)) {
            throw new IllegalArgumentException("lottery red ball must not be null.");
        }
        this.red = red;
        if (red.contains(",")) {
            this.red = String.join(" ", red.trim().split(","));
        }
    }

    public void setBlue(String blue) {
        if (StringUtils.isNotBlank(blue)) {
            this.blue = blue;
        }
    }

    public void setSales(String sales) {
        if (StringUtils.isBlank(sales) || "_".equals(sales)) {
            return;
        }
        this.sales = Long.valueOf(sales.replaceAll(",", ""));
    }

    public void setAward(String awards) {
        if (StringUtils.isBlank(awards) || "_".equals(awards)) {
            return;
        }
        this.award = Long.valueOf(awards.replaceAll(",", ""));
    }

    public void setPool(String pools) {
        if (StringUtils.isBlank(pools) || "_".equals(pools)) {
            return;
        }
        if (pools.contains(".")) {
            pools = pools.substring(0, pools.indexOf("."));
        }
        this.pool = Long.valueOf(pools.replaceAll(",", ""));
    }

    public void setLotDate(String date) {
        if (StringUtils.isNotBlank(date)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            this.lotDate = LocalDate.parse(date.substring(0, 10), formatter);
        }
    }

    public void setLevels(List<LevelValue> levels) {
        this.levels = levels;
    }

}
