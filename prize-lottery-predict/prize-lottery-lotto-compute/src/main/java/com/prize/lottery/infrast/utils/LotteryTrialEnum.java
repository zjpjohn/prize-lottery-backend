package com.prize.lottery.infrast.utils;

import com.cloud.arch.http.HttpRequest;
import com.prize.lottery.domain.lottery.model.LotteryTrialDo;
import com.prize.lottery.enums.LotteryEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public enum LotteryTrialEnum {
    FC3D_TRIAL(LotteryEnum.FC3D) {
        @Override
        protected String apiUri(Integer size) {
            return String.format(FC3D_KS_API, size);
        }

        @Override
        protected LotteryTrialDo parseItem(Elements el, String period) {
            Element kaiTag = el.get(2);
            String  kai    = kaiTag.text();
            if (StringUtils.isBlank(kai)) {
                return null;
            }
            Elements shiTags = el.get(3).getElementsByTag("b");
            String   shi     = null;
            if (shiTags != null && !shiTags.isEmpty()) {
                Element shiTag = shiTags.get(0);
                shi = shiTag.text();
            }
            if (StringUtils.isNotBlank(shi)) {
                return new LotteryTrialDo(this.getLottery(), period, kai, shi);
            }
            return new LotteryTrialDo(this.getLottery(), period, kai);
        }
    },
    PL3_TRIAL(LotteryEnum.PL3) {
        @Override
        protected String apiUri(Integer size) {
            return String.format(PL3_SHI_API, size);
        }

        @Override
        protected LotteryTrialDo parseItem(Elements el, String period) {
            Element shiTag = el.get(2).getElementsByTag("b").get(0);
            String  shi    = shiTag.text();
            if (StringUtils.isBlank(shi)) {
                return null;
            }
            return new LotteryTrialDo(this.getLottery(), period, null, shi);
        }
    };

    private static final String      FC3D_KS_API = "https://www.00038.cn/kjh/3d/sjh.htm?size=%d";
    private static final String      PL3_SHI_API = "https://www.00038.cn/kjh/p3/sjh.htm?size=%d";
    private final        LotteryEnum lottery;

    LotteryTrialEnum(LotteryEnum lottery) {
        this.lottery = lottery;
    }

    public LotteryEnum getLottery() {
        return lottery;
    }

    public List<LotteryTrialDo> fetch(Integer size) {
        try {
            String   response = HttpRequest.instance().get(this.apiUri(size));
            Document doc      = Jsoup.parse(response);
            Elements trs      = doc.select("table.kjhTable > tbody > tr");
            if (trs == null || trs.isEmpty()) {
                return null;
            }
            return trs.stream().map(v -> {
                Elements tds       = v.getElementsByTag("td");
                Element  periodTag = tds.get(0);
                String   period    = periodTag.text();
                return this.parseItem(tds, period);
            }).filter(Objects::nonNull).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("抓取[{}]试机号异常:", this.getLottery().getNameZh(), e);
            throw new RuntimeException(e);
        }
    }

    protected abstract String apiUri(Integer size);

    protected abstract LotteryTrialDo parseItem(Elements el, String period);

    public static LotteryTrialEnum valueOf(LotteryEnum type) {
        return Arrays.stream(values())
                     .filter(v -> v.lottery == type)
                     .findFirst()
                     .orElseThrow(() -> new RuntimeException("彩种不存在开试机号."));
    }

}
