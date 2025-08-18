package com.prize.lottery.domain.omit.model;

import com.cloud.arch.utils.JsonUtils;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.omit.value.OmitComputer;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.infrast.utils.LotteryTrendUtil;
import com.prize.lottery.po.lottery.LotteryTrendOmitPo;
import com.prize.lottery.value.Omit;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;

@Slf4j
@Data
public class LotteryTrendOmitDo {

    public static final String INIT_OMIT_PATH_PATTERN = "lottery/omits/%s_trend_omit.json";

    private Long        id;
    private LotteryEnum type;
    private String      period;
    private Omit        formOmit;
    private Omit        ottOmit;
    private Omit        modeOmit;
    private Omit        bsOmit;
    private Omit        oeOmit;

    public static LotteryTrendOmitDo load(LotteryEnum type) {
        String path     = String.format(INIT_OMIT_PATH_PATTERN, type.getType());
        URL    resource = LotteryTrendOmitPo.class.getClassLoader().getResource(path);
        Assert.notNull(resource, ResponseHandler.NO_INIT_OMIT);
        LotteryTrendOmitDo trendOmit = JsonUtils.readValue(resource, LotteryTrendOmitDo.class);
        trendOmit.setType(type);
        return trendOmit;
    }

    public LotteryTrendOmitDo nextOmit(String next, String balls) {
        String nextPeriod = type.nextPeriod(this.period);
        if (!nextPeriod.equals(next)) {
            log.warn("period[{}] with nextPeriod[{}] is not consistent next[{}]", period, nextPeriod, next);
            throw Assert.cast(ResponseHandler.OMIT_NOT_CONSISTENT);
        }
        LotteryTrendOmitDo trendOmit = new LotteryTrendOmitDo();
        trendOmit.setType(type);
        trendOmit.setPeriod(nextPeriod);
        trendOmit.setFormOmit(OmitComputer.nextOmit(this.formOmit, LotteryTrendUtil.calcForm(balls).getValue()));
        trendOmit.setOttOmit(OmitComputer.nextOmit(this.ottOmit, LotteryTrendUtil.calcOtt(balls).getValue()));
        trendOmit.setModeOmit(OmitComputer.nextOmit(this.modeOmit, LotteryTrendUtil.calcMode(balls).getValue()));
        trendOmit.setBsOmit(OmitComputer.nextOmit(this.bsOmit, LotteryTrendUtil.calcBs(balls).getValue()));
        trendOmit.setOeOmit(OmitComputer.nextOmit(this.oeOmit, LotteryTrendUtil.calcOe(balls).getValue()));
        return trendOmit;
    }

}
