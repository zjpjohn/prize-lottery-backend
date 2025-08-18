package com.prize.lottery.enums;

import com.prize.lottery.po.share.BaseLottoCensus;
import com.prize.lottery.vo.SyntheticItemCensusVo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@Getter
@AllArgsConstructor
public enum ItemChartEnums implements ChartCalculator {
    LEVEL_10("10", 10) {
        @Override
        public void assemble(SyntheticItemCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setLevel10(v.getCensus()));
        }
    },
    LEVEL_20("20", 20) {
        @Override
        public void assemble(SyntheticItemCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setLevel20(v.getCensus()));
        }
    },
    LEVEL_50("50", 50) {
        @Override
        public void assemble(SyntheticItemCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setLevel50(v.getCensus()));
        }
    },
    LEVEL_100("100", 100) {
        @Override
        public void assemble(SyntheticItemCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setLevel100(v.getCensus()));
        }
    },
    LEVEL_150("150", 150) {
        @Override
        public void assemble(SyntheticItemCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setLevel150(v.getCensus()));
        }
    },
    LEVEL_200("200", 200) {
        @Override
        public void assemble(SyntheticItemCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setLevel200(v.getCensus()));
        }
    };

    private final String  name;
    private final Integer limit;

    @Override
    public Integer value() {
        return this.limit;
    }

    @Override
    public String label() {
        return this.name;
    }

    public abstract void assemble(SyntheticItemCensusVo census, BaseLottoCensus po);

}
