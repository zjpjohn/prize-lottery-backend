package com.prize.lottery.enums;

import com.prize.lottery.po.share.BaseLottoCensus;
import com.prize.lottery.vo.SyntheticFullCensusVo;
import lombok.Getter;

import java.util.Optional;

@Getter
public enum FullChartEnums implements ChartCalculator {

    LEVEL_100("100", 100) {
        @Override
        public void assemble(SyntheticFullCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setLevel100(v.getCensus()));
        }
    },
    LEVEL_200("200", 200) {
        @Override
        public void assemble(SyntheticFullCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setLevel200(v.getCensus()));
        }
    },
    LEVEL_400("400", 400) {
        @Override
        public void assemble(SyntheticFullCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setLevel400(v.getCensus()));
        }
    },
    LEVEL_600("600", 600) {
        @Override
        public void assemble(SyntheticFullCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setLevel600(v.getCensus()));
        }
    },
    LEVEL_800("800", 800) {
        @Override
        public void assemble(SyntheticFullCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setLevel800(v.getCensus()));
        }
    },
    LEVEL_1000("1000", 1000) {
        @Override
        public void assemble(SyntheticFullCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setLevel1000(v.getCensus()));
        }
    };

    private final String  name;
    private final Integer limit;

    FullChartEnums(String name, Integer limit) {
        this.name  = name;
        this.limit = limit;
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

    public abstract void assemble(SyntheticFullCensusVo census, BaseLottoCensus po);

}
