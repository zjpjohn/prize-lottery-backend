package com.prize.lottery.domain.omit.model;

import com.cloud.arch.utils.JsonUtils;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.omit.value.OmitComputer;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.infrast.utils.LotteryTrendUtil;
import com.prize.lottery.po.lottery.LotteryTrendOmitPo;
import com.prize.lottery.value.Omit;
import com.prize.lottery.value.OmitValue;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.URL;
import java.util.List;

@Slf4j
@Data
public class LotteryMatchOmitDo {

    public static final String INIT_OMIT_PATH_PATTERN = "lottery/omits/%s_match_omit.json";

    private Long        id;
    private LotteryEnum type;
    private String      period;
    private Omit        codeOmit;
    private Omit        comOmit;
    private OmitValue   allOmit;

    public static LotteryMatchOmitDo load(LotteryEnum type) {
        String path     = String.format(INIT_OMIT_PATH_PATTERN, type.getType());
        URL    resource = LotteryTrendOmitPo.class.getClassLoader().getResource(path);
        Assert.notNull(resource, ResponseHandler.NO_INIT_OMIT);
        LotteryMatchOmitDo trendOmit = JsonUtils.readValue(resource, LotteryMatchOmitDo.class);
        trendOmit.setType(type);
        return trendOmit;
    }

    public LotteryMatchOmitDo nextOmit(String next, String balls) {
        String nextPeriod = type.nextPeriod(this.period);
        if (!nextPeriod.equals(next)) {
            log.warn("period[{}] with nextPeriod[{}] is not consistent next[{}]", period, nextPeriod, next);
            throw Assert.cast(ResponseHandler.OMIT_NOT_CONSISTENT);
        }
        LotteryMatchOmitDo omit = new LotteryMatchOmitDo();
        omit.setType(type);
        omit.setPeriod(nextPeriod);
        //对码号码遗漏
        String duiMa = LotteryTrendUtil.duiMa(balls);
        omit.setCodeOmit(OmitComputer.nextOmit(this.codeOmit, duiMa));
        //计算对码总遗漏
        OmitValue value = OmitValue.of(duiMa, StringUtils.isBlank(duiMa) ? this.allOmit.getValue() + 1 : 0);
        omit.setAllOmit(value);
        //奖号对码化遗漏
        List<String> duiMaList = LotteryTrendUtil.duiMaList(balls);
        omit.setComOmit(OmitComputer.nextOmit(this.comOmit, duiMaList));
        return omit;
    }

}
