package com.prize.lottery.enums;

import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import com.prize.lottery.po.pl3.Pl3IcaiPo;
import com.prize.lottery.po.pl3.Pl3MasterRankPo;
import com.prize.lottery.po.pl3.Pl3MasterRatePo;
import com.prize.lottery.po.share.BaseLottoCensus;
import com.prize.lottery.value.ForecastValue;
import com.prize.lottery.value.ICaiForecast;
import com.prize.lottery.value.RankValue;
import com.prize.lottery.value.StatHitValue;
import com.prize.lottery.vo.NumberThreeCensusVo;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@TypeHandler(type = Type.ENUM)
public enum Pl3Channel implements Value<String> {
    DAN1("d1", "独胆") {
        @Override
        public void calcHit(ForecastValue value, List<String> lottery) {
            Fc3dChannel.DAN1.calcHit(value, lottery);
        }

        @Override
        public ForecastValue forecastValue(Pl3IcaiPo icai) {
            return icai.getDan1();
        }

        @Override
        public void calcRank(Pl3MasterRatePo rate, Pl3MasterRankPo rank) {
            rank.calcD1Rank(rate);
        }

        @Override
        public RankValue rankValue(Pl3MasterRankPo rank) {
            return rank.getDan1();
        }

        @Override
        public StatHitValue rateValue(Pl3MasterRatePo rate) {
            return rate.getD1Me();
        }

        @Override
        public void toConvert(Pl3IcaiPo data, ICaiForecast forecast) {
            data.setDan1(forecast.get(this.getNameZh()));
        }

        @Override
        public void calcRate(List<Pl3IcaiPo> data, Pl3MasterRatePo rate) {
            rate.calcDan1(data);
        }

        @Override
        public void assemble(NumberThreeCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setD1(v.getCensus()));
        }
    },
    DAN2("d2", "双胆") {
        @Override
        public void calcHit(ForecastValue value, List<String> lottery) {
            Fc3dChannel.DAN1.calcHit(value, lottery);
        }

        @Override
        public ForecastValue forecastValue(Pl3IcaiPo icai) {
            return icai.getDan2();
        }

        @Override
        public void calcRank(Pl3MasterRatePo rate, Pl3MasterRankPo rank) {
            rank.calcD2Rank(rate);
        }

        @Override
        public RankValue rankValue(Pl3MasterRankPo rank) {
            return rank.getDan2();
        }

        @Override
        public void toConvert(Pl3IcaiPo data, ICaiForecast forecast) {
            data.setDan2(forecast.get(this.getNameZh()));
        }

        @Override
        public StatHitValue rateValue(Pl3MasterRatePo rate) {
            return rate.getD2Me();
        }

        @Override
        public double rateThrottle() {
            return 0.80;
        }

        @Override
        public void calcRate(List<Pl3IcaiPo> data, Pl3MasterRatePo rate) {
            rate.calcDan2(data);
        }

        @Override
        public void assemble(NumberThreeCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setD2(v.getCensus()));
        }
    },
    DAN3("d3", "三胆") {
        @Override
        public void calcHit(ForecastValue value, List<String> lottery) {
            Fc3dChannel.DAN1.calcHit(value, lottery);
        }

        @Override
        public ForecastValue forecastValue(Pl3IcaiPo icai) {
            return icai.getDan3();
        }

        @Override
        public void calcRank(Pl3MasterRatePo rate, Pl3MasterRankPo rank) {
            rank.calcD3Rank(rate);
        }

        @Override
        public RankValue rankValue(Pl3MasterRankPo rank) {
            return rank.getDan3();
        }

        @Override
        public void toConvert(Pl3IcaiPo data, ICaiForecast forecast) {
            data.setDan3(forecast.get(this.getNameZh()));
        }

        @Override
        public StatHitValue rateValue(Pl3MasterRatePo rate) {
            return rate.getD3Hi();
        }

        @Override
        public double rateThrottle() {
            return 0.93;
        }

        @Override
        public void calcRate(List<Pl3IcaiPo> data, Pl3MasterRatePo rate) {
            rate.calcDan3(data);
        }

        @Override
        public void assemble(NumberThreeCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setD3(v.getCensus()));
        }
    },
    COM5("c5", "五码组选") {
        @Override
        public int qThrottle() {
            return 2;
        }

        @Override
        public void calcHit(ForecastValue value, List<String> lottery) {
            Fc3dChannel.DAN1.calcHit(value, lottery);
        }

        @Override
        public ForecastValue forecastValue(Pl3IcaiPo icai) {
            return icai.getCom5();
        }

        @Override
        public void calcRank(Pl3MasterRatePo rate, Pl3MasterRankPo rank) {
            rank.calcC5Rank(rate);
        }

        @Override
        public void toConvert(Pl3IcaiPo data, ICaiForecast forecast) {
            data.setCom5(forecast.get(this.getNameZh()));
        }

        @Override
        public RankValue rankValue(Pl3MasterRankPo rank) {
            return rank.getCom5();
        }

        @Override
        public StatHitValue rateValue(Pl3MasterRatePo rate) {
            return rate.getC5Me();
        }

        @Override
        public double rateThrottle() {
            return 0.80;
        }

        @Override
        public void calcRate(List<Pl3IcaiPo> data, Pl3MasterRatePo rate) {
            rate.calcCom5(data);
        }

        @Override
        public void assemble(NumberThreeCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setC5(v.getCensus()));
        }
    },
    COM6("c6", "六码组选") {
        @Override
        public int qThrottle() {
            return 2;
        }

        @Override
        public void calcHit(ForecastValue value, List<String> lottery) {
            Fc3dChannel.DAN1.calcHit(value, lottery);
        }

        @Override
        public ForecastValue forecastValue(Pl3IcaiPo icai) {
            return icai.getCom6();
        }

        @Override
        public void calcRank(Pl3MasterRatePo rate, Pl3MasterRankPo rank) {
            rank.calcC6Rank(rate);
        }

        @Override
        public void toConvert(Pl3IcaiPo data, ICaiForecast forecast) {
            data.setCom6(forecast.get(this.getNameZh()));
        }

        @Override
        public RankValue rankValue(Pl3MasterRankPo rank) {
            return rank.getCom6();
        }

        @Override
        public StatHitValue rateValue(Pl3MasterRatePo rate) {
            return rate.getC6Hi();
        }

        @Override
        public double rateThrottle() {
            return 0.86;
        }

        @Override
        public void calcRate(List<Pl3IcaiPo> data, Pl3MasterRatePo rate) {
            rate.calcCom6(data);
        }

        @Override
        public void assemble(NumberThreeCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setC6(v.getCensus()));
        }
    },
    COM7("c7", "七码组选") {
        @Override
        public int qThrottle() {
            return 2;
        }

        @Override
        public void calcHit(ForecastValue value, List<String> lottery) {
            Fc3dChannel.DAN1.calcHit(value, lottery);
        }

        @Override
        public ForecastValue forecastValue(Pl3IcaiPo icai) {
            return icai.getCom7();
        }

        @Override
        public void calcRank(Pl3MasterRatePo rate, Pl3MasterRankPo rank) {
            rank.calcC7Rank(rate);
        }

        @Override
        public RankValue rankValue(Pl3MasterRankPo rank) {
            return rank.getCom7();
        }

        @Override
        public void toConvert(Pl3IcaiPo data, ICaiForecast forecast) {
            data.setCom7(forecast.get(this.getNameZh()));
        }

        @Override
        public StatHitValue rateValue(Pl3MasterRatePo rate) {
            return rate.getC7Hi();
        }

        @Override
        public double rateThrottle() {
            return 0.93;
        }

        @Override
        public void calcRate(List<Pl3IcaiPo> data, Pl3MasterRatePo rate) {
            rate.calcCom7(data);
        }

        @Override
        public void assemble(NumberThreeCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setC7(v.getCensus()));
        }
    },
    KILL1("k1", "杀一") {
        @Override
        public void calcHit(ForecastValue value, List<String> lottery) {
            Fc3dChannel.KILL1.calcHit(value, lottery);
        }

        @Override
        public ForecastValue forecastValue(Pl3IcaiPo icai) {
            return icai.getKill1();
        }

        @Override
        public void calcRank(Pl3MasterRatePo rate, Pl3MasterRankPo rank) {
            rank.calcK1Rank(rate);
        }

        @Override
        public RankValue rankValue(Pl3MasterRankPo rank) {
            return rank.getKill1();
        }

        @Override
        public void toConvert(Pl3IcaiPo data, ICaiForecast forecast) {
            data.setKill1(forecast.get(this.getNameZh()));
        }

        @Override
        public StatHitValue rateValue(Pl3MasterRatePo rate) {
            return rate.getK1Hi();
        }

        @Override
        public double rateThrottle() {
            return 0.93;
        }

        @Override
        public void calcRate(List<Pl3IcaiPo> data, Pl3MasterRatePo rate) {
            rate.calcKill1(data);
        }

        @Override
        public void assemble(NumberThreeCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setK1(v.getCensus()));
        }
    },
    KILL2("k2", "杀二") {
        @Override
        public void calcHit(ForecastValue value, List<String> lottery) {
            Fc3dChannel.KILL1.calcHit(value, lottery);
        }

        @Override
        public ForecastValue forecastValue(Pl3IcaiPo icai) {
            return icai.getKill2();
        }

        @Override
        public void calcRank(Pl3MasterRatePo rate, Pl3MasterRankPo rank) {
            rank.calcK2Rank(rate);
        }

        @Override
        public RankValue rankValue(Pl3MasterRankPo rank) {
            return rank.getKill2();
        }

        @Override
        public void toConvert(Pl3IcaiPo data, ICaiForecast forecast) {
            data.setKill2(forecast.get(this.getNameZh()));
        }

        @Override
        public StatHitValue rateValue(Pl3MasterRatePo rate) {
            return rate.getK2Me();
        }

        @Override
        public double rateThrottle() {
            return 0.80;
        }

        @Override
        public void calcRate(List<Pl3IcaiPo> data, Pl3MasterRatePo rate) {
            rate.calcKill2(data);
        }

        @Override
        public void assemble(NumberThreeCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setK2(v.getCensus()));
        }
    },
    COMB3("cb3", "定位3*3*3") {
        @Override
        public void calcHit(ForecastValue value, List<String> lottery) {
            Fc3dChannel.COMB3.calcHit(value, lottery);
        }

        @Override
        public ForecastValue forecastValue(Pl3IcaiPo icai) {
            return icai.getComb3();
        }

        @Override
        public void calcRank(Pl3MasterRatePo rate, Pl3MasterRankPo rank) {
            rank.calcCb3Rank(rate);
        }

        @Override
        public void toConvert(Pl3IcaiPo data, ICaiForecast forecast) {
            data.setComb3(forecast.get(this.getNameZh()));
        }

        @Override
        public ChartValue chartValue() {
            return ChartValue.DUPLEX;
        }

        @Override
        public boolean isVipChannel() {
            return false;
        }

        @Override
        public boolean isHotChannel() {
            return false;
        }

        @Override
        public boolean isSingle() {
            return false;
        }

        @Override
        public RankValue rankValue(Pl3MasterRankPo rank) {
            return rank.getComb3();
        }

        @Override
        public StatHitValue rateValue(Pl3MasterRatePo rate) {
            return rate.getCb3Me();
        }

        @Override
        public void calcRate(List<Pl3IcaiPo> data, Pl3MasterRatePo rate) {
            rate.calcComb3(data);
        }
    },
    COMB4("cb4", "定位4*4*4") {
        @Override
        public void calcHit(ForecastValue value, List<String> lottery) {
            Fc3dChannel.COMB3.calcHit(value, lottery);
        }

        @Override
        public ForecastValue forecastValue(Pl3IcaiPo icai) {
            return icai.getComb4();
        }

        @Override
        public void calcRank(Pl3MasterRatePo rate, Pl3MasterRankPo rank) {
            rank.calcCb4Rank(rate);
        }

        @Override
        public void toConvert(Pl3IcaiPo data, ICaiForecast forecast) {
            data.setComb4(forecast.get(this.getNameZh()));
        }

        @Override
        public ChartValue chartValue() {
            return COMB3.chartValue();
        }

        @Override
        public boolean isVipChannel() {
            return false;
        }

        @Override
        public boolean isHotChannel() {
            return false;
        }

        @Override
        public boolean isSingle() {
            return false;
        }

        @Override
        public RankValue rankValue(Pl3MasterRankPo rank) {
            return rank.getComb4();
        }

        @Override
        public StatHitValue rateValue(Pl3MasterRatePo rate) {
            return rate.getCb4Me();
        }

        @Override
        public void calcRate(List<Pl3IcaiPo> data, Pl3MasterRatePo rate) {
            rate.calcComb4(data);
        }
    },
    COMB5("cb5", "定位5*5*5") {
        @Override
        public void calcHit(ForecastValue value, List<String> lottery) {
            Fc3dChannel.COMB3.calcHit(value, lottery);
        }

        @Override
        public ForecastValue forecastValue(Pl3IcaiPo icai) {
            return icai.getComb5();
        }

        @Override
        public void calcRank(Pl3MasterRatePo rate, Pl3MasterRankPo rank) {
            rank.calcCb5Rank(rate);
        }

        @Override
        public ChartValue chartValue() {
            return COMB3.chartValue();
        }

        @Override
        public void toConvert(Pl3IcaiPo data, ICaiForecast forecast) {
            data.setComb5(forecast.get(this.getNameZh()));
        }

        @Override
        public boolean isVipChannel() {
            return false;
        }

        @Override
        public boolean isHotChannel() {
            return false;
        }

        @Override
        public boolean isSingle() {
            return false;
        }

        @Override
        public RankValue rankValue(Pl3MasterRankPo rank) {
            return rank.getComb5();
        }

        @Override
        public StatHitValue rateValue(Pl3MasterRatePo rate) {
            return rate.getCb5Me();
        }

        @Override
        public void calcRate(List<Pl3IcaiPo> data, Pl3MasterRatePo rate) {
            rate.calcComb5(data);
        }
    };

    private final String channel;
    private final String nameZh;

    Pl3Channel(String channel, String nameZh) {
        this.channel = channel;
        this.nameZh  = nameZh;
    }

    public String getChannel() {
        return channel;
    }

    public String getNameZh() {
        return nameZh;
    }

    @Override
    public String value() {
        return this.channel;
    }

    /**
     * 枚举值描述
     */
    @Override
    public String label() {
        return nameZh;
    }

    public ChartValue chartValue() {
        return ChartValue.PLAIN;
    }

    public boolean isVipChannel() {
        return true;
    }

    public boolean isHotChannel() {
        return true;
    }

    public boolean isSingle() {
        return true;
    }

    public double rateThrottle() {
        return 1.0;
    }

    public abstract void calcHit(ForecastValue value, List<String> lottery);

    public abstract ForecastValue forecastValue(Pl3IcaiPo icai);

    public abstract StatHitValue rateValue(Pl3MasterRatePo rate);

    public abstract void calcRate(List<Pl3IcaiPo> data, Pl3MasterRatePo rate);

    public abstract void calcRank(Pl3MasterRatePo rate, Pl3MasterRankPo rank);

    public abstract RankValue rankValue(Pl3MasterRankPo rank);

    public abstract void toConvert(Pl3IcaiPo data, ICaiForecast forecast);

    public void assemble(NumberThreeCensusVo census, BaseLottoCensus po) {
    }

    public int qThrottle() {
        return 1;
    }

    public static Pl3Channel findOf(String type) {
        return Arrays.stream(values())
                     .filter(v -> v.getChannel().equals(type))
                     .findFirst()
                     .orElseThrow(() -> new RuntimeException("数据字段错误."));
    }
}
