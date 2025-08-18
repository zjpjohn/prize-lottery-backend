package com.prize.lottery.infrast.spider.around;

import com.cloud.arch.http.HttpRequest;
import com.google.common.collect.Maps;
import com.prize.lottery.enums.AroundType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.po.lottery.LotteryAroundPo;
import com.prize.lottery.value.AroundValue;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Fc3dAroundFetcher {

    public static final String REQUEST_URI   = "http://m.cocololo.com/3dsj/3draodantu.html";
    public static final String PARAM_PATTERN = "?seeod=%s";
    public static final String USER_AGENT    = "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Mobile Safari/537.36";

    public static LotteryAroundPo fetch(String period) {
        String uri = REQUEST_URI;
        if (StringUtils.isNotBlank(period)) {
            uri = uri + String.format(PARAM_PATTERN, period);
        }
        Map<String, String> header = Maps.newHashMap();
        header.put("User-Agent", USER_AGENT);
        String    result      = HttpRequest.instance().get(uri, header, Charset.forName("gb2312"));
        Document  document    = Jsoup.parse(result);
        Elements  elements    = document.select("body > center > table");
        Element   element     = elements.get(2).select("tbody > tr > td").get(1);
        String    text        = element.text();
        String[]  split       = text.split("\\s+");
        LocalDate lottoDate   = parseDate(split[0]);
        String    lottoPeriod = split[1].replace("期", "");
        List<AroundValue.AroundCell> cells = elements.get(4)
                                                     .select("tbody > tr")
                                                     .stream()
                                                     .flatMap(Fc3dAroundFetcher::parseTr)
                                                     .collect(Collectors.toList());
        LotteryAroundPo around = new LotteryAroundPo();
        around.setPeriod(lottoPeriod);
        around.setLottoDate(lottoDate);
        around.setType(AroundType.KAI);
        around.setLotto(LotteryEnum.FC3D);
        around.setAround(new AroundValue(cells));
        return around;
    }

    private static LocalDate parseDate(String date) {
        String   replaced = date.replace("年", " ").replace("月", " ").replace("日", " ");
        String[] split    = replaced.split("\\s+");
        return LocalDate.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
    }

    private static Stream<AroundValue.AroundCell> parseTr(Element tr) {
        return tr.select("td")
                 .stream()
                 .filter(e -> e.hasAttr("style") && StringUtils.isNotBlank(e.text()))
                 .map(Fc3dAroundFetcher::parseTd);
    }

    private static AroundValue.AroundCell parseTd(Element td) {
        String style = td.attr("style");
        if (style.contains("#ccc00")) {
            return new AroundValue.AroundCell(td.text(), 4);
        }
        if (style.contains("#0099ff")) {
            return new AroundValue.AroundCell(td.text(), 3);
        }
        if (style.contains("#ff0000")) {
            return new AroundValue.AroundCell(td.text(), 2);
        }
        return new AroundValue.AroundCell(td.text(), 1);
    }

}
