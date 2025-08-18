package com.prize.lottery.plugins.access.domain;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.util.StringUtils;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class WhiteBlackInfo {
    //method集合
    public static final List<String>      METHODS           = Arrays.asList("get", "post", "put", "delete");
    //代表全部方法正则字符串
    public static final String            ALL_METHODS_REGEX = "*";
    //方法分隔符
    public static final String            METHOD_DELIMITER  = ",";
    //路径正则匹配器
    public static final PathPatternParser PARSER            = new PathPatternParser();

    //请求uri白名单
    private Map<String, String> whiteList;
    //请求ip黑名单
    private List<String>        blackList;

    public List<WhitePattern> whitList() {
        return this.whiteList.entrySet().stream()
                .map(entry -> {
                    String       value   = entry.getValue();
                    List<String> methods = Lists.newArrayList();
                    if (ALL_METHODS_REGEX.equals(value.trim())) {
                        methods.addAll(METHODS);
                    } else {
                        List<String> values = Splitter.on(METHOD_DELIMITER).trimResults()
                                .omitEmptyStrings().splitToList(value);
                        methods.addAll(values);
                    }
                    return new WhitePattern(PARSER.parse(entry.getKey()), methods);
                }).collect(Collectors.toList());
    }

}
