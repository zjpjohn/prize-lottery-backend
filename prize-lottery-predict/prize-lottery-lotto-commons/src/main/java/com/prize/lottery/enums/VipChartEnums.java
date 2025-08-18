package com.prize.lottery.enums;


import com.prize.lottery.po.share.BaseLottoCensus;
import com.prize.lottery.vo.SyntheticVipCensusVo;

import java.util.Optional;

public enum VipChartEnums implements ChartCalculator {

    LEVEL_10("10", 10) {
        @Override
        public void assemble(SyntheticVipCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setLevel10(v.getCensus()));
        }
    },
    LEVEL_20("20", 20) {
        @Override
        public void assemble(SyntheticVipCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setLevel20(v.getCensus()));
        }
    },
    LEVEL_50("50", 50) {
        @Override
        public void assemble(SyntheticVipCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setLevel50(v.getCensus()));
        }
    },
    LEVEL_100("100", 100) {
        @Override
        public void assemble(SyntheticVipCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setLevel100(v.getCensus()));
        }
    },
    LEVEL_ALL("all", 150) {
        @Override
        public void assemble(SyntheticVipCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setLevel150(v.getCensus()));
        }
    };

    private final String  name;
    private final Integer limit;

    VipChartEnums(String name, Integer limit) {
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


    public abstract void assemble(SyntheticVipCensusVo census, BaseLottoCensus po);
}
