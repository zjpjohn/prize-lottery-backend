package com.prize.lottery.domain.omit.model;

import com.cloud.arch.utils.JsonUtils;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.value.DanIndex;
import lombok.Data;

import java.net.URL;
import java.util.List;

@Data
public class LotteryDanDo {

    public static final String INIT_DAN_OMIT_PATH = "lottery/omits/%s_dan_omit.json";

    private Long        id;
    private LotteryEnum type;
    private String      period;
    private DanIndex    index1;
    private DanIndex    index2;
    private DanIndex    index3;
    private DanIndex    index4;
    private DanIndex    index5;
    private DanIndex    index6;
    private DanIndex    index7;
    private DanIndex    index8;
    private DanIndex    index9;

    /**
     * 导入初始胆码遗漏数据
     *
     * @param type 彩票类型
     */
    public static LotteryDanDo load(LotteryEnum type) {
        String path     = String.format(INIT_DAN_OMIT_PATH, type.getType());
        URL    resource = LotteryDanDo.class.getClassLoader().getResource(path);
        Assert.notNull(resource, ResponseHandler.NO_INIT_OMIT);

        LotteryDanDo lotteryDan = JsonUtils.readValue(resource, LotteryDanDo.class);
        lotteryDan.setType(type);
        return lotteryDan;
    }

    /**
     * 计算下一期预测胆码
     *
     * @param balls  本期开奖号
     * @param period 本期开奖期号
     */
    public LotteryDanDo next(List<Integer> balls, String period) {
        String lastPeriod = this.type.lastPeriod(period);
        Assert.state(lastPeriod.equals(this.getPeriod()), ResponseHandler.OMIT_NOT_CONSISTENT);

        LotteryDanDo lotteryDan = new LotteryDanDo();
        lotteryDan.setType(this.type);
        lotteryDan.setPeriod(period);
        lotteryDan.setIndex1(calcSumTail(balls, this.index1.getOmit()));
        lotteryDan.setIndex2(calcMin(balls, this.index2.getOmit()));
        lotteryDan.setIndex3(calcMax(balls, this.index3.getOmit()));
        lotteryDan.setIndex4(calcMaxMiddle(balls, this.index4.getOmit()));
        lotteryDan.setIndex5(calcMinMiddle(balls, this.index5.getOmit()));
        lotteryDan.setIndex6(DanIndex.of(balls.get(0), this.index6.getOmit()));
        lotteryDan.setIndex7(DanIndex.of(balls.get(1), this.index7.getOmit()));
        lotteryDan.setIndex8(DanIndex.of(balls.get(2), this.index8.getOmit()));
        lotteryDan.setIndex9(calcMultiTail(balls, this.index9.getOmit()));
        return lotteryDan;
    }

    /**
     * 计算上一期胆码是否命中
     */
    public void calcLastHit(List<Integer> balls) {
        this.index1.calc(balls);
        this.index2.calc(balls);
        this.index3.calc(balls);
        this.index4.calc(balls);
        this.index5.calc(balls);
        this.index6.calc(balls);
        this.index7.calc(balls);
        this.index8.calc(balls);
        this.index9.calc(balls);
    }

    private DanIndex calcSumTail(List<Integer> balls, Integer omit) {
        Integer sum  = balls.stream().reduce(0, Integer::sum);
        int     tail = sum % 10;
        return DanIndex.of(tail, omit);
    }

    private DanIndex calcMin(List<Integer> balls, Integer omit) {
        Integer min = balls.stream().min(Integer::compareTo).orElse(0);
        return DanIndex.of(min, omit);
    }

    private DanIndex calcMax(List<Integer> balls, Integer omit) {
        Integer max = balls.stream().max(Integer::compareTo).orElse(0);
        return DanIndex.of(max, omit);
    }

    private DanIndex calcMaxMiddle(List<Integer> balls, Integer omit) {
        List<Integer> list   = balls.stream().sorted(Integer::compareTo).toList();
        Integer       max    = list.get(2);
        Integer       middle = list.get(1);
        return DanIndex.of((max + middle) % 10, omit);
    }

    private DanIndex calcMinMiddle(List<Integer> balls, Integer omit) {
        List<Integer> list   = balls.stream().sorted(Integer::compareTo).toList();
        Integer       min    = list.get(0);
        Integer       middle = list.get(1);
        return DanIndex.of((min + middle) % 10, omit);
    }

    private DanIndex calcMultiTail(List<Integer> balls, Integer omit) {
        Integer multi = balls.stream().reduce(1, (a, b) -> a * b);
        return DanIndex.of(multi % 10, omit);
    }

}
