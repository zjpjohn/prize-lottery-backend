package com.prize.lottery.domain.omit.model;

import com.cloud.arch.utils.JsonUtils;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.omit.value.Kl8Rules;
import com.prize.lottery.domain.omit.value.OmitComputer;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.value.Omit;
import com.prize.lottery.value.OmitValue;
import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class LottoKl8OmitDo {

    public static final String INIT_OMIT_KL8_PATH = "lottery/omits/kl8_init_omit.json";

    private Long   id;
    private String period;
    private Omit   ballOmit;
    private Omit   bsOmit;
    private Omit   oeOmit;
    private Omit   kuaOmit;
    private Omit   sumOmit;
    private Omit   tailOmit;

    /**
     * 加载快乐8初始化遗漏数据
     */
    public static LottoKl8OmitDo load() {
        URL resource = LottoKl8OmitDo.class.getClassLoader().getResource(INIT_OMIT_KL8_PATH);
        Assert.notNull(resource, ResponseHandler.NO_INIT_OMIT);
        return JsonUtils.readValue(resource, LottoKl8OmitDo.class);
    }

    /**
     * 根据上一期遗漏数据计算本期开奖遗漏
     *
     * @param balls 本期开奖数据
     */
    public LottoKl8OmitDo nextOmit(String period, String balls) {
        //判断上下期是否连续一致
        String lastPeriod = LotteryEnum.KL8.lastPeriod(period);
        Assert.state(lastPeriod.equals(this.getPeriod()), ResponseHandler.OMIT_NOT_CONSISTENT);

        List<String>   lotto   = OmitComputer.getBalls(balls);
        LottoKl8OmitDo kl8Omit = new LottoKl8OmitDo();
        kl8Omit.setPeriod(period);
        //计算基础遗漏
        kl8Omit.setBallOmit(OmitComputer.nextOmit(ballOmit, lotto));
        //计算跨度遗漏
        Pair<Integer, String> kua         = Kl8Rules.calcKua(lotto);
        Omit                  nextKuaOmit = OmitComputer.nextOmit(kuaOmit, kua.getValue());
        setExtraValue(nextKuaOmit, kua.getValue(), String.valueOf(kua.getKey()));
        kl8Omit.setKuaOmit(nextKuaOmit);
        //计算和值遗漏
        Pair<Integer, String> sum         = Kl8Rules.calcSum(lotto);
        Omit                  nextSumOmit = OmitComputer.nextOmit(sumOmit, sum.getValue());
        setExtraValue(nextSumOmit, sum.getValue(), String.valueOf(sum.getKey()));
        kl8Omit.setSumOmit(nextSumOmit);
        //计算大小比遗漏
        kl8Omit.setBsOmit(OmitComputer.nextOmit(bsOmit, Kl8Rules.calcBsRatio(lotto)));
        //计算奇偶比遗漏
        kl8Omit.setOeOmit(OmitComputer.nextOmit(oeOmit, Kl8Rules.calcOeRatio(lotto)));
        //计算当期尾数遗漏
        kl8Omit.calcTailOmit(lotto);
        return kl8Omit;
    }

    /**
     * 计算号码尾数遗漏
     */
    public void calcTailOmit(List<String> balls) {
        Map<Integer, Long> counts = balls.stream()
                                         .map(Integer::parseInt)
                                         .collect(Collectors.groupingBy(e -> e % 10, Collectors.counting()));
        List<OmitValue> values = IntStream.range(0, 10)
                                          .mapToObj(i -> Optional.ofNullable(counts.get(i))
                                                                 .map(e -> OmitValue.of(String.valueOf(i), e.intValue()))
                                                                 .orElse(OmitValue.of(String.valueOf(i), 0)))
                                          .collect(Collectors.toList());
        this.tailOmit = new Omit(values);

    }

    private void setExtraValue(Omit omit, String key, String value) {
        omit.getValues().stream().filter(v -> v.getKey().equals(key)).findFirst().ifPresent(v -> v.setExtra(value));
    }

}
