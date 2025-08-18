package com.prize.lottery.po.lottery;

import com.google.common.base.Splitter;
import com.prize.lottery.enums.LotteryEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Data
public class LotteryInfoPo {

    /**
     * 主键
     */
    private Long          id;
    /**
     * 彩票类型
     */
    private String        type;
    /**
     * 开奖期号
     */
    private String        period;
    /**
     * 红球号码
     */
    private String        red;
    /**
     * 篮球开奖号码
     */
    private String        blue;
    /**
     * 选三型组选号码
     */
    private String        com;
    /**
     * 上一次同开奖号期号
     */
    private String        last;
    /**
     * 试机号
     */
    private String        shi;
    /**
     * 开机号
     */
    private String        kai;
    /**
     * 试机号时间
     */
    private LocalDateTime trialTime;
    /**
     * 开奖时间
     */
    private LocalDate     lotDate;
    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    public int getLastDelta() {
        if (LotteryEnum.FC3D.getType().equals(type)) {
            return LotteryEnum.FC3D.delta(period, last);
        }
        if (LotteryEnum.PL3.getType().equals(type)) {
            return LotteryEnum.PL3.delta(period, last);
        }
        return 0;
    }

    public String calcNum3Com() {
        this.com = Splitter.on(Pattern.compile("\\s+"))
                           .trimResults()
                           .splitToStream(red)
                           .sorted()
                           .collect(Collectors.joining(" "));
        return this.com;
    }

    public List<String> redBalls() {
        if (StringUtils.isBlank(red)) {
            return Collections.emptyList();
        }
        return Splitter.on(Pattern.compile("\\s+")).trimResults().splitToList(this.red);
    }

    public List<String> shiBalls() {
        if (StringUtils.isBlank(shi)) {
            return Collections.emptyList();
        }
        return Splitter.on(Pattern.compile("\\s+")).trimResults().splitToList(this.shi);
    }

    public List<String> blueBalls() {
        if (StringUtils.isBlank(red)) {
            return Collections.emptyList();
        }
        return Splitter.on(Pattern.compile("\\s+")).trimResults().splitToList(this.blue);
    }
}
