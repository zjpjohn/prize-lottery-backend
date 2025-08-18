package com.prize.lottery.application.vo;

import com.google.common.collect.Multimap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
public class N3ItemCensusVo {

    private String                      period;
    private Map<Long, List<CensusCell>> k1;
    private Map<Long, List<CensusCell>> k2;
    private Map<Long, List<CensusCell>> d1;
    private Map<Long, List<CensusCell>> d2;
    private Map<Long, List<CensusCell>> d3;
    private Map<Long, List<CensusCell>> c7;

    @Data
    public static class ItemCensus {

        private Multimap<Long, String> k1;
        private Multimap<Long, String> k2;
        private Multimap<Long, String> d1;
        private Multimap<Long, String> d2;
        private Multimap<Long, String> d3;
        private Multimap<Long, String> c7;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CensusCell {

        private String key;
        private Long   count;

    }
}
