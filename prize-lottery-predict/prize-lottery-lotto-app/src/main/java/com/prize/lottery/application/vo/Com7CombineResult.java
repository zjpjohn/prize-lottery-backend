package com.prize.lottery.application.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Com7CombineResult {

    private final List<ResultItem> data;
    private final Integer          size;

    public Com7CombineResult(List<Pair<String, Long>> data) {
        this.data = this.convert(data);
        this.size = data.size();
    }

    private List<ResultItem> convert(List<Pair<String, Long>> pairs) {
        return pairs.stream().map(p -> new ResultItem(p.getKey(), p.getValue())).collect(Collectors.toList());
    }

    @Getter
    @AllArgsConstructor
    public static class ResultItem {
        private final String key;
        private final Long   value;
    }
}
