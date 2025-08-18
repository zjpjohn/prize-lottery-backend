package com.prize.lottery.enums;


import com.prize.lottery.po.share.BaseLottoCensus;
import com.prize.lottery.vo.kl8.Kl8FullCensusVo;

import java.util.Optional;

public enum Kl8FullChart implements ChartCalculator {
    LEVEL_80("80", 80) {
        @Override
        public void assemble(Kl8FullCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setLevel80(v.getCensus()));
        }
    },
    LEVEL_120("120", 120) {
        @Override
        public void assemble(Kl8FullCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setLevel120(v.getCensus()));
        }
    },
    LEVEL_160("160", 160) {
        @Override
        public void assemble(Kl8FullCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setLevel160(v.getCensus()));
        }
    },
    LEVEL_200("200", 200) {
        @Override
        public void assemble(Kl8FullCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setLevel200(v.getCensus()));
        }
    },
    LEVEL_320("320", 320) {
        @Override
        public void assemble(Kl8FullCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setLevel320(v.getCensus()));
        }
    };

    private final String  name;
    private final Integer limit;

    Kl8FullChart(String name, Integer limit) {
        this.name  = name;
        this.limit = limit;
    }

    public String getName() {
        return name;
    }

    public Integer getLimit() {
        return limit;
    }

    @Override
    public Integer value() {
        return this.limit;
    }

    /**
     * 枚举值描述
     */
    @Override
    public String label() {
        return name;
    }


    public abstract void assemble(Kl8FullCensusVo census, BaseLottoCensus po);

}
