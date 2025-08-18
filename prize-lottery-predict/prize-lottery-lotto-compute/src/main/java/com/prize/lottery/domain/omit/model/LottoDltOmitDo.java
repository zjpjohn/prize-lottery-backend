package com.prize.lottery.domain.omit.model;

import com.cloud.arch.utils.JsonUtils;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.omit.value.OmitComputer;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.value.Omit;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.net.URL;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class LottoDltOmitDo extends BaseOmitDo {

    public static final String INIT_OMIT_DLT_PATH = "lottery/omits/dlt_init_omit.json";

    private Long   id;
    private String period;
    private Omit   red;
    private Omit   blue;

    @Override
    public LotteryOmitDo toOmit() {
        LotteryOmitDo lotteryOmit = new LotteryOmitDo();
        lotteryOmit.setId(id);
        lotteryOmit.setType(this.getType());
        lotteryOmit.setPeriod(period);
        lotteryOmit.setRed(red);
        lotteryOmit.setBlue(blue);
        return lotteryOmit;
    }

    public static LottoDltOmitDo load() {
        URL resource = LottoDltOmitDo.class.getClassLoader().getResource(INIT_OMIT_DLT_PATH);
        Assert.notNull(resource, ResponseHandler.NO_INIT_OMIT);
        return JsonUtils.readValue(resource, LottoDltOmitDo.class);
    }

    public LottoDltOmitDo nextOmit(String period, String redBall, String blueBall) {
        String lastPeriod = LotteryEnum.DLT.lastPeriod(period);
        Assert.state(lastPeriod.equals(this.getPeriod()), ResponseHandler.OMIT_NOT_CONSISTENT);

        List<String>   reds    = OmitComputer.getBalls(redBall);
        List<String>   blues   = OmitComputer.getBalls(blueBall);
        LottoDltOmitDo ssqOmit = new LottoDltOmitDo();
        ssqOmit.setPeriod(period);
        ssqOmit.setType(this.getType());
        ssqOmit.setRed(OmitComputer.nextOmit(red, reds));
        ssqOmit.setBlue(OmitComputer.nextOmit(blue, blues));
        return ssqOmit;
    }

}
