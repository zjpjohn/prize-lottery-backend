package com.prize.lottery.domain.omit.model;

import com.cloud.arch.utils.JsonUtils;
import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Sets;
import com.prize.lottery.domain.omit.value.OmitComputer;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.value.Omit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.List;
import java.util.Set;

@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
public class LottoN3OmitDo extends BaseOmitDo {

    public static final String INIT_OMIT_PATH_PATTERN = "lottery/omits/%s_init_omit.json";

    private Long   id;
    private String period;
    private Omit   red;
    private Omit   cb1;
    private Omit   cb2;
    private Omit   cb3;
    private Omit   extra;

    @Override
    public LotteryOmitDo toOmit() {
        LotteryOmitDo lotteryOmit = new LotteryOmitDo();
        lotteryOmit.setId(id);
        lotteryOmit.setType(this.getType());
        lotteryOmit.setPeriod(period);
        lotteryOmit.setRed(red);
        lotteryOmit.setCb1(cb1);
        lotteryOmit.setCb2(cb2);
        lotteryOmit.setCb3(cb3);
        lotteryOmit.setExtra(extra);
        return lotteryOmit;
    }

    public static LottoN3OmitDo load(LotteryEnum type) {
        String initOmitPath = String.format(INIT_OMIT_PATH_PATTERN, type.getType());
        URL    resource     = LottoN3OmitDo.class.getClassLoader().getResource(initOmitPath);
        Assert.notNull(resource, ResponseHandler.NO_INIT_OMIT);
        return JsonUtils.readValue(resource, LottoN3OmitDo.class);
    }

    public LottoN3OmitDo nextOmit(String period, String balls) {
        String lastPeriod = this.getType().lastPeriod(period);
        Assert.state(lastPeriod.equals(this.getPeriod()), ResponseHandler.OMIT_NOT_CONSISTENT);

        List<String>  lotto     = OmitComputer.getBalls(balls);
        LottoN3OmitDo lottoOmit = new LottoN3OmitDo();
        lottoOmit.setType(this.getType());
        lottoOmit.setPeriod(period);
        //号码基础遗漏
        lottoOmit.setRed(OmitComputer.nextN3Omit(red, lotto));
        //百位遗漏
        lottoOmit.setCb1(OmitComputer.nextOmit(cb1, lotto.get(0)));
        //十位遗漏
        lottoOmit.setCb2(OmitComputer.nextOmit(cb2, lotto.get(1)));
        //个位遗漏
        lottoOmit.setCb3(OmitComputer.nextOmit(cb3, lotto.get(2)));
        //号码分布遗漏状态:组六、组三
        lottoOmit.setExtra(lottoPatten(lotto));
        return lottoOmit;
    }

    private Omit lottoPatten(List<String> balls) {
        Set<String> sets = Sets.newHashSet(balls);
        return OmitComputer.nextOmit(extra, String.valueOf(sets.size()));
    }

}

