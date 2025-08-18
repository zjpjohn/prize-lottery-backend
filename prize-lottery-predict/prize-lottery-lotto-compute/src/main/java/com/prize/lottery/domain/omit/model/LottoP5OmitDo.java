package com.prize.lottery.domain.omit.model;

import com.cloud.arch.utils.JsonUtils;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.omit.value.OmitComputer;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.value.Omit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.List;

@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
public class LottoP5OmitDo extends BaseOmitDo {

    public static final String INIT_OMIT_PATH_PATTERN = "lottery/omits/pl5_init_omit.json";

    private Long   id;
    private String period;
    private Omit   red;
    private Omit   cb1;
    private Omit   cb2;
    private Omit   cb3;
    private Omit   cb4;
    private Omit   cb5;

    @Override
    public LotteryOmitDo toOmit() {
        LotteryOmitDo omit = new LotteryOmitDo();
        omit.setType(this.getType());
        omit.setPeriod(period);
        omit.setRed(red);
        omit.setCb1(cb1);
        omit.setCb2(cb2);
        omit.setCb3(cb3);
        omit.setCb4(cb4);
        omit.setCb5(cb5);
        return omit;
    }

    public static LottoP5OmitDo load() {
        URL resource = LottoN3OmitDo.class.getClassLoader().getResource(INIT_OMIT_PATH_PATTERN);
        Assert.notNull(resource, ResponseHandler.NO_INIT_OMIT);
        return JsonUtils.readValue(resource, LottoP5OmitDo.class);
    }

    public LottoP5OmitDo nextOmit(String period, String balls) {
        String lastPeriod = this.getType().lastPeriod(period);
        Assert.state(lastPeriod.equals(this.getPeriod()), ResponseHandler.OMIT_NOT_CONSISTENT);

        List<String>  lotto     = OmitComputer.getBalls(balls);
        LottoP5OmitDo lottoOmit = new LottoP5OmitDo();
        lottoOmit.setType(this.getType());
        lottoOmit.setPeriod(period);
        //号码基础遗漏
        lottoOmit.setRed(OmitComputer.nextN3Omit(red, lotto));
        //万位位遗漏
        lottoOmit.setCb1(OmitComputer.nextOmit(cb1, lotto.get(0)));
        //千位遗漏
        lottoOmit.setCb2(OmitComputer.nextOmit(cb2, lotto.get(1)));
        //百位遗漏
        lottoOmit.setCb3(OmitComputer.nextOmit(cb3, lotto.get(2)));
        //十位遗漏
        lottoOmit.setCb4(OmitComputer.nextOmit(cb4, lotto.get(3)));
        //个位遗漏
        lottoOmit.setCb5(OmitComputer.nextOmit(cb5, lotto.get(4)));
        return lottoOmit;
    }

}
