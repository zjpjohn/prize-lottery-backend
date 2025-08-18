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
public class LottoKuaOmitDo {

    public static final String INIT_OMIT_PATH_PATTERN = "lottery/omits/%s_kua_omit.json";

    private Long        id;
    private LotteryEnum type;
    private String      period;
    private Omit        baseOmit;
    private Omit        ampOmit;
    private Omit        bmsOmit;
    private Omit        ottOmit;
    private Omit        ampAmp;

    /**
     * 导入初始和值遗漏值
     */
    public static LottoKuaOmitDo load(LotteryEnum type) {
        String initOmitPath = String.format(INIT_OMIT_PATH_PATTERN, type.getType());
        URL    resource     = LottoN3OmitDo.class.getClassLoader().getResource(initOmitPath);
        Assert.notNull(resource, ResponseHandler.NO_INIT_OMIT);
        LottoKuaOmitDo kuaOmit = JsonUtils.readValue(resource, LottoKuaOmitDo.class);
        kuaOmit.setType(type);
        return kuaOmit;
    }

    public LottoKuaOmitDo nextOmit(String next, String balls) {
        String nextPeriod = type.nextPeriod(this.period);
        if (!nextPeriod.equals(next)) {
            log.warn("period[{}] with nextPeriod[{}] is not consistent next[{}]", period, nextPeriod, next);
            throw Assert.cast(ResponseHandler.OMIT_NOT_CONSISTENT);
        }
        List<String>   lotto   = OmitComputer.getBalls(balls);
        int            kua     = OmitComputer.getKua(lotto);
        LottoKuaOmitDo kuaOmit = new LottoKuaOmitDo();
        kuaOmit.setPeriod(next);
        kuaOmit.setType(this.type);
        //大中小遗漏
        kuaOmit.setBmsOmit(OmitComputer.nextOmit(this.bmsOmit, this.getBms(kua)));
        //跨度基本遗漏
        kuaOmit.setBaseOmit(OmitComputer.nextOmit(this.baseOmit, String.valueOf(kua)));
        //012路跨度遗漏
        kuaOmit.setOttOmit(OmitComputer.nextOmit(this.ottOmit, String.valueOf(kua % 3)));
        //跨度振幅遗漏
        kuaOmit.setAmpOmit(OmitComputer.nextOmit(this.ampOmit, String.valueOf(this.calcAmp(kua))));
        //计算跨度振幅的振幅
        kuaOmit.setAmpAmp(OmitComputer.nextOmit(this.ampAmp, String.valueOf(this.calcAmpAmp(kua))));
        return kuaOmit;
    }

    private int getAmp() {
        return this.ampOmit.getValues()
                           .stream()
                           .filter(e -> e.getValue() == 0)
                           .findFirst()
                           .map(OmitValue::getKey)
                           .map(Integer::parseInt)
                           .orElse(0);
    }

    private int calcAmp(int kua) {
        return this.baseOmit.getValues()
                            .stream()
                            .filter(e -> e.getValue() == 0)
                            .findFirst()
                            .map(OmitValue::getKey)
                            .map(Integer::parseInt)
                            .map(e -> Math.abs(e - kua))
                            .orElse(0);
    }

    public int calcAmpAmp(Integer kua) {
        int amp = this.calcAmp(kua);
        return this.ampOmit.getValues()
                           .stream()
                           .filter(e -> e.getValue() == 0)
                           .findFirst()
                           .map(OmitValue::getKey)
                           .map(Integer::parseInt)
                           .map(e -> Math.abs(e - amp))
                           .orElse(0);
    }

    private String getBms(int sum) {
        if (sum <= 2) {
            return "小";
        }
        if (sum <= 6) {
            return "中";
        }
        return "大";
    }
}
