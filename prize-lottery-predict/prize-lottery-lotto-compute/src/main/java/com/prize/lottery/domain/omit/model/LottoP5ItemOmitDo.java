package com.prize.lottery.domain.omit.model;

import com.cloud.arch.utils.JsonUtils;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.omit.value.OmitComputer;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.value.Omit;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.List;

import static com.prize.lottery.infrast.utils.LotteryTrendUtil.*;


@Slf4j
@Data
public class LottoP5ItemOmitDo {

    public static final String INIT_OMIT_PATH_PATTERN = "lottery/omits/pl5_item_omit.json";

    private Long   id;
    private String balls;
    private String period;
    private Omit   cb1Amp;
    private Omit   cb1Aod;
    private Omit   cb1Bos;
    private Omit   cb1Oe;
    private Omit   cb2Amp;
    private Omit   cb2Aod;
    private Omit   cb2Bos;
    private Omit   cb2Oe;
    private Omit   cb3Amp;
    private Omit   cb3Aod;
    private Omit   cb3Bos;
    private Omit   cb3Oe;
    private Omit   cb4Amp;
    private Omit   cb4Aod;
    private Omit   cb4Bos;
    private Omit   cb4Oe;
    private Omit   cb5Amp;
    private Omit   cb5Aod;
    private Omit   cb5Bos;
    private Omit   cb5Oe;

    public static LottoP5ItemOmitDo load() {
        URL resource = LotteryItemOmitDo.class.getClassLoader().getResource(INIT_OMIT_PATH_PATTERN);
        Assert.notNull(resource, ResponseHandler.NO_INIT_OMIT);
        return JsonUtils.readValue(resource, LottoP5ItemOmitDo.class);
    }

    public LottoP5ItemOmitDo next(String next, String lottery) {
        String nextPeriod = LotteryEnum.PL5.nextPeriod(this.period);
        if (!nextPeriod.equals(next)) {
            log.warn("period[{}] with nextPeriod[{}] is not consistent next[{}]", period, nextPeriod, next);
            throw Assert.cast(ResponseHandler.OMIT_NOT_CONSISTENT);
        }
        List<Integer>     last     = intBalls(balls);
        List<Integer>     current  = intBalls(lottery);
        LottoP5ItemOmitDo itemOmit = new LottoP5ItemOmitDo();
        itemOmit.setBalls(lottery);
        itemOmit.setPeriod(nextPeriod);
        itemOmit.setCb1Oe(OmitComputer.nextOmit(this.cb1Oe, calcOe(current, 0)));
        itemOmit.setCb2Oe(OmitComputer.nextOmit(this.cb2Oe, calcOe(current, 1)));
        itemOmit.setCb3Oe(OmitComputer.nextOmit(this.cb3Oe, calcOe(current, 2)));
        itemOmit.setCb4Oe(OmitComputer.nextOmit(this.cb4Oe, calcOe(current, 3)));
        itemOmit.setCb5Oe(OmitComputer.nextOmit(this.cb5Oe, calcOe(current, 4)));
        itemOmit.setCb1Bos(OmitComputer.nextOmit(this.cb1Bos, calcBos(current, 0)));
        itemOmit.setCb2Bos(OmitComputer.nextOmit(this.cb2Bos, calcBos(current, 1)));
        itemOmit.setCb3Bos(OmitComputer.nextOmit(this.cb3Bos, calcBos(current, 2)));
        itemOmit.setCb4Bos(OmitComputer.nextOmit(this.cb4Bos, calcBos(current, 3)));
        itemOmit.setCb5Bos(OmitComputer.nextOmit(this.cb5Bos, calcBos(current, 4)));
        itemOmit.setCb1Aod(OmitComputer.nextOmit(this.cb1Aod, calcAod(last, current, 0)));
        itemOmit.setCb2Aod(OmitComputer.nextOmit(this.cb2Aod, calcAod(last, current, 1)));
        itemOmit.setCb3Aod(OmitComputer.nextOmit(this.cb3Aod, calcAod(last, current, 2)));
        itemOmit.setCb4Aod(OmitComputer.nextOmit(this.cb4Aod, calcAod(last, current, 3)));
        itemOmit.setCb5Aod(OmitComputer.nextOmit(this.cb5Aod, calcAod(last, current, 4)));
        itemOmit.setCb1Amp(OmitComputer.nextOmit(this.cb1Amp, String.valueOf(calcAmp(last, current, 0))));
        itemOmit.setCb2Amp(OmitComputer.nextOmit(this.cb2Amp, String.valueOf(calcAmp(last, current, 1))));
        itemOmit.setCb3Amp(OmitComputer.nextOmit(this.cb3Amp, String.valueOf(calcAmp(last, current, 2))));
        itemOmit.setCb4Amp(OmitComputer.nextOmit(this.cb4Amp, String.valueOf(calcAmp(last, current, 3))));
        itemOmit.setCb5Amp(OmitComputer.nextOmit(this.cb5Amp, String.valueOf(calcAmp(last, current, 4))));
        return itemOmit;
    }

}
