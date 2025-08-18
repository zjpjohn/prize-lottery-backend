package com.prize.lottery.value;

import lombok.Data;

@Data
public class AnalyzeValue {

    private String key;
    private Long   value;

    public AnalyzeValue(String key, Long value) {
        this.key   = key;
        this.value = value;
    }

    public static AnalyzeValue of(String key, Long value) {
        return new AnalyzeValue(key, value);
    }

}
