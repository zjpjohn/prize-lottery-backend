package com.prize.lottery.domain.omit.model;

import com.cloud.arch.utils.JsonUtils;
import com.cloud.arch.web.utils.Assert;
import com.google.common.base.Splitter;
import com.prize.lottery.domain.omit.value.OmitComputer;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.po.lottery.LotteryTrendOmitPo;
import com.prize.lottery.value.Omit;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Data
public class LotteryItemOmitDo {

    public static final String INIT_OMIT_PATH_PATTERN = "lottery/omits/%s_item_omit.json";

    private Long        id;
    private LotteryEnum type;
    private String      balls;
    private String      period;
    private Omit        cb1Amp;
    private Omit        cb1Aod;
    private Omit        cb1Bos;
    private Omit        cb1Oe;
    private Omit        cb2Amp;
    private Omit        cb2Aod;
    private Omit        cb2Bos;
    private Omit        cb2Oe;
    private Omit        cb3Amp;
    private Omit        cb3Aod;
    private Omit        cb3Bos;
    private Omit        cb3Oe;

    public static LotteryItemOmitDo load(LotteryEnum type) {
        String path     = String.format(INIT_OMIT_PATH_PATTERN, type.getType());
        URL    resource = LotteryTrendOmitPo.class.getClassLoader().getResource(path);
        Assert.notNull(resource, ResponseHandler.NO_INIT_OMIT);
        LotteryItemOmitDo trendOmit = JsonUtils.readValue(resource, LotteryItemOmitDo.class);
        trendOmit.setType(type);
        return trendOmit;
    }

    public LotteryItemOmitDo next(String next, String lottery) {
        String nextPeriod = type.nextPeriod(this.period);
        if (!nextPeriod.equals(next)) {
            log.warn("period[{}] with nextPeriod[{}] is not consistent next[{}]", period, nextPeriod, next);
            throw Assert.cast(ResponseHandler.OMIT_NOT_CONSISTENT);
        }
        List<Integer>     last     = intBalls(balls);
        List<Integer>     current  = intBalls(lottery);
        LotteryItemOmitDo itemOmit = new LotteryItemOmitDo();
        itemOmit.setType(this.type);
        itemOmit.setBalls(lottery);
        itemOmit.setPeriod(nextPeriod);
        itemOmit.setCb1Oe(OmitComputer.nextOmit(this.cb1Oe, calcOe(current, 0)));
        itemOmit.setCb2Oe(OmitComputer.nextOmit(this.cb2Oe, calcOe(current, 1)));
        itemOmit.setCb3Oe(OmitComputer.nextOmit(this.cb3Oe, calcOe(current, 2)));
        itemOmit.setCb1Bos(OmitComputer.nextOmit(this.cb1Bos, calcBos(current, 0)));
        itemOmit.setCb2Bos(OmitComputer.nextOmit(this.cb2Bos, calcBos(current, 1)));
        itemOmit.setCb3Bos(OmitComputer.nextOmit(this.cb3Bos, calcBos(current, 2)));
        itemOmit.setCb1Aod(OmitComputer.nextOmit(this.cb1Aod, calcAod(last, current, 0)));
        itemOmit.setCb2Aod(OmitComputer.nextOmit(this.cb2Aod, calcAod(last, current, 1)));
        itemOmit.setCb3Aod(OmitComputer.nextOmit(this.cb3Aod, calcAod(last, current, 2)));
        itemOmit.setCb1Amp(OmitComputer.nextOmit(this.cb1Amp, String.valueOf(calcAmp(last, current, 0))));
        itemOmit.setCb2Amp(OmitComputer.nextOmit(this.cb2Amp, String.valueOf(calcAmp(last, current, 1))));
        itemOmit.setCb3Amp(OmitComputer.nextOmit(this.cb3Amp, String.valueOf(calcAmp(last, current, 2))));
        return itemOmit;
    }

    private List<Integer> intBalls(String lottery) {
        return Splitter.on(Pattern.compile("\\s+"))
                       .trimResults()
                       .splitToStream(lottery)
                       .map(Integer::parseInt)
                       .collect(Collectors.toList());
    }

    private Integer calcAmp(List<Integer> last, List<Integer> current, int index) {
        return Math.abs(last.get(index) - current.get(index));
    }

    private String calcBos(List<Integer> balls, int index) {
        return balls.get(index) >= 5 ? "大" : "小";
    }

    private String calcOe(List<Integer> balls, int index) {
        return balls.get(index) % 2 == 0 ? "偶" : "奇";
    }

    private String calcAod(List<Integer> last, List<Integer> current, int index) {
        int delta = current.get(index) - last.get(index);
        return delta > 0 ? "升" : (delta < 0 ? "降" : "平");
    }

}
