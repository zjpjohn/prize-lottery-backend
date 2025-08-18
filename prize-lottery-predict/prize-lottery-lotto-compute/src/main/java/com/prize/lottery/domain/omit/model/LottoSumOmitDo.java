package com.prize.lottery.domain.omit.model;

import com.cloud.arch.utils.JsonUtils;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.omit.value.OmitComputer;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.value.Omit;
import com.prize.lottery.value.OmitValue;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.List;

@Slf4j
@Data
public class LottoSumOmitDo {

    public static final String INIT_OMIT_PATH_PATTERN = "lottery/omits/%s_sum_omit.json";

    private Long        id;
    private LotteryEnum type;
    private String      period;
    private Omit        baseOmit;
    private Omit        tailOmit;
    private Omit        bmsOmit;
    private Omit        ottOmit;
    private Omit        tailAmp;

    /**
     * 导入初始和值遗漏值
     */
    public static LottoSumOmitDo load(LotteryEnum type) {
        String initOmitPath = String.format(INIT_OMIT_PATH_PATTERN, type.getType());
        URL    resource     = LottoN3OmitDo.class.getClassLoader().getResource(initOmitPath);
        Assert.notNull(resource, ResponseHandler.NO_INIT_OMIT);
        LottoSumOmitDo sumOmit = JsonUtils.readValue(resource, LottoSumOmitDo.class);
        sumOmit.setType(type);
        return sumOmit;
    }

    /**
     * 计算下一期和值遗漏
     *
     * @param next  下一期期号
     * @param balls 开奖号码
     */
    public LottoSumOmitDo nextOmit(String next, String balls) {
        String nextPeriod = type.nextPeriod(this.period);
        Assert.state(nextPeriod.equals(next), ResponseHandler.OMIT_NOT_CONSISTENT);
        List<String>   lotto     = OmitComputer.getBalls(balls);
        int            sum       = OmitComputer.getSum(lotto);
        LottoSumOmitDo lottoOmit = new LottoSumOmitDo();
        lottoOmit.setPeriod(next);
        lottoOmit.setType(this.type);
        //和值基本遗漏
        lottoOmit.setBaseOmit(OmitComputer.nextOmit(this.baseOmit, sumText(sum)));
        //和值012路遗漏
        lottoOmit.setOttOmit(OmitComputer.nextOmit(this.ottOmit, String.valueOf(sum % 3)));
        //和值大中小路遗漏
        lottoOmit.setBmsOmit(OmitComputer.nextOmit(this.bmsOmit, this.getBms(sum)));
        //和尾及和尾振幅遗漏
        int sumTail = sum % 10;
        lottoOmit.setTailOmit(OmitComputer.nextOmit(this.tailOmit, String.valueOf(sumTail)));
        lottoOmit.setTailAmp(OmitComputer.nextOmit(this.tailAmp, String.valueOf(this.calcTailAmp(sumTail))));
        return lottoOmit;
    }

    private int calcTailAmp(int tail) {
        return this.tailOmit.getValues()
                            .stream()
                            .filter(e -> e.getValue() == 0)
                            .findFirst()
                            .map(OmitValue::getKey)
                            .map(Integer::parseInt)
                            .map(e -> Math.abs(e - tail))
                            .orElse(0);
    }

    private String sumText(int sum) {
        if (type != LotteryEnum.PL5) {
            return String.valueOf(sum);
        }
        if (sum < 0) {
            return "0+";
        }
        if (sum < 8) {
            return "5+";
        }
        if (sum >= 40) {
            return "40+";
        }
        if (sum >= 37) {
            return "37+";
        }
        return String.valueOf(sum);
    }

    private String getBms(int sum) {
        return type == LotteryEnum.PL3 ? p5Bms(sum) : n3Bms(sum);
    }

    private String n3Bms(int sum) {
        return sum <= 9 ? "小" : (sum <= 17 ? "中" : "大");
    }

    private String p5Bms(int sum) {
        return sum <= 14 ? "小" : (sum <= 29 ? "中" : "大");
    }

}
