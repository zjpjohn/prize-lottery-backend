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
public class LottoQlcOmitDo extends BaseOmitDo {

    public static final String INIT_OMIT_QLC_PATH = "lottery/omits/qlc_init_omit.json";

    private Long   id;
    private String period;
    private Omit   red;

    @Override
    public LotteryOmitDo toOmit() {
        LotteryOmitDo lotteryOmit = new LotteryOmitDo();
        lotteryOmit.setId(id);
        lotteryOmit.setType(this.getType());
        lotteryOmit.setPeriod(period);
        lotteryOmit.setRed(red);
        return lotteryOmit;
    }

    public static LottoQlcOmitDo load() {
        URL resource = LottoQlcOmitDo.class.getClassLoader().getResource(INIT_OMIT_QLC_PATH);
        Assert.notNull(resource, ResponseHandler.NO_INIT_OMIT);
        return JsonUtils.readValue(resource, LottoQlcOmitDo.class);
    }

    public LottoQlcOmitDo nextOmit(String period, String balls) {
        String lastPeriod = LotteryEnum.QLC.lastPeriod(period);
        Assert.state(lastPeriod.equals(this.getPeriod()), ResponseHandler.OMIT_NOT_CONSISTENT);

        List<String>   lotto   = OmitComputer.getBalls(balls);
        LottoQlcOmitDo qlcOmit = new LottoQlcOmitDo();
        qlcOmit.setPeriod(period);
        qlcOmit.setType(this.getType());
        qlcOmit.setRed(OmitComputer.nextOmit(red, lotto));
        return qlcOmit;
    }

}
