package com.prize.lottery.enums;


import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import com.prize.lottery.po.dlt.DltIcaiPo;
import com.prize.lottery.po.dlt.DltMasterRankPo;
import com.prize.lottery.po.dlt.DltMasterRatePo;
import com.prize.lottery.po.share.BaseLottoCensus;
import com.prize.lottery.utils.ICaiConstants;
import com.prize.lottery.value.ForecastValue;
import com.prize.lottery.value.ICaiForecast;
import com.prize.lottery.value.RankValue;
import com.prize.lottery.value.StatHitValue;
import com.prize.lottery.vo.dlt.DltChartCensusVo;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@TypeHandler(type = Type.ENUM)
public enum DltChannel implements Value<String> {
    RED1("r1", "红球独胆") {
        @Override
        public void calcHit(ForecastValue value, List<String> reds, List<String> blues) {
            SsqChannel.RED1.calcHit(value, reds, null);
        }

        @Override
        public boolean isVipChannel() {
            return false;
        }

        @Override
        public double rateThrottle() {
            return 0.40;
        }

        @Override
        public void toConvert(DltIcaiPo data, ICaiForecast forecast) {
            data.setRed1(forecast.get(this.getNameZh()));
        }

        @Override
        public ForecastValue forecastValue(DltIcaiPo data) {
            return data.getRed1();
        }

        @Override
        public void calcRate(List<DltIcaiPo> data, DltMasterRatePo rate) {
            rate.calcRed1(data);
        }

        @Override
        public void calcRank(DltMasterRatePo rate, DltMasterRankPo rank) {
            rank.calcRed1Rank(rate);
        }

        @Override
        public RankValue rankValue(DltMasterRankPo rank) {
            return rank.getRed1();
        }

        @Override
        public StatHitValue rateValue(DltMasterRatePo rate) {
            return rate.getR1Ne();
        }

        @Override
        public void assemble(DltChartCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setR1(v.getCensus()));
        }
    },
    RED2("r2", "红球双胆") {
        @Override
        public void calcHit(ForecastValue value, List<String> reds, List<String> blues) {
            RED1.calcHit(value, reds, blues);
        }

        @Override
        public ForecastValue forecastValue(DltIcaiPo data) {
            return data.getRed2();
        }

        @Override
        public void toConvert(DltIcaiPo data, ICaiForecast forecast) {
            data.setRed2(forecast.get(this.getNameZh()));
        }

        @Override
        public double rateThrottle() {
            return 0.50;
        }

        @Override
        public void calcRate(List<DltIcaiPo> data, DltMasterRatePo rate) {
            rate.calcRed2(data);
        }

        @Override
        public void calcRank(DltMasterRatePo rate, DltMasterRankPo rank) {
            rank.calcRed2Rank(rate);
        }

        @Override
        public RankValue rankValue(DltMasterRankPo rank) {
            return rank.getRed2();
        }

        @Override
        public StatHitValue rateValue(DltMasterRatePo rate) {
            return rate.getR2Me();
        }

        @Override
        public void assemble(DltChartCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setR2(v.getCensus()));
        }
    },
    RED3("r3", "红球三胆") {
        @Override
        public void calcHit(ForecastValue value, List<String> reds, List<String> blues) {
            RED1.calcHit(value, reds, blues);
        }

        @Override
        public ForecastValue forecastValue(DltIcaiPo data) {
            return data.getRed3();
        }

        @Override
        public void toConvert(DltIcaiPo data, ICaiForecast forecast) {
            data.setRed3(forecast.get(this.getNameZh()));
        }

        @Override
        public double rateThrottle() {
            return 0.70;
        }

        @Override
        public void calcRate(List<DltIcaiPo> data, DltMasterRatePo rate) {
            rate.calcRed3(data);
        }

        @Override
        public void calcRank(DltMasterRatePo rate, DltMasterRankPo rank) {
            rank.calcRed3Rank(rate);
        }

        @Override
        public RankValue rankValue(DltMasterRankPo rank) {
            return rank.getRed3();
        }

        @Override
        public StatHitValue rateValue(DltMasterRatePo rate) {
            return rate.getR3Me();
        }

        @Override
        public void assemble(DltChartCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setR3(v.getCensus()));
        }
    },
    RED10("r10", "红球10码") {
        @Override
        public void calcHit(ForecastValue value, List<String> reds, List<String> blues) {
            RED1.calcHit(value, reds, blues);
        }

        @Override
        public ForecastValue forecastValue(DltIcaiPo data) {
            return data.getRed10();
        }

        @Override
        public void toConvert(DltIcaiPo data, ICaiForecast forecast) {
            data.setRed10(forecast.get(this.getNameZh()));
        }

        @Override
        public double rateThrottle() {
            return 0.70;
        }

        @Override
        public void calcRate(List<DltIcaiPo> data, DltMasterRatePo rate) {
            rate.calcRed10(data);
        }

        @Override
        public void calcRank(DltMasterRatePo rate, DltMasterRankPo rank) {
            rank.calcRed10Rank(rate);
        }

        @Override
        public RankValue rankValue(DltMasterRankPo rank) {
            return rank.getRed10();
        }

        @Override
        public StatHitValue rateValue(DltMasterRatePo rate) {
            return rate.getR10Me();
        }

        @Override
        public void assemble(DltChartCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setR10(v.getCensus()));
        }
    },
    RED20("r20", "红球20码") {
        @Override
        public void calcHit(ForecastValue value, List<String> reds, List<String> blues) {
            RED1.calcHit(value, reds, blues);
        }

        @Override
        public ForecastValue forecastValue(DltIcaiPo data) {
            return data.getRed20();
        }

        @Override
        public void toConvert(DltIcaiPo data, ICaiForecast forecast) {
            data.setRed20(forecast.get(this.getNameZh()));
        }

        @Override
        public double rateThrottle() {
            return 0.60;
        }

        @Override
        public void calcRate(List<DltIcaiPo> data, DltMasterRatePo rate) {
            rate.calcRed20(data);
        }

        @Override
        public void calcRank(DltMasterRatePo rate, DltMasterRankPo rank) {
            rank.calcRed20Rank(rate);
        }

        @Override
        public RankValue rankValue(DltMasterRankPo rank) {
            return rank.getRed20();
        }

        @Override
        public StatHitValue rateValue(DltMasterRatePo rate) {
            return rate.getR20Me();
        }

        @Override
        public void assemble(DltChartCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setR20(v.getCensus()));
        }
    },
    RK3("rk3", "红球杀三") {
        @Override
        public void calcHit(ForecastValue value, List<String> reds, List<String> blues) {
            SsqChannel.RK3.calcHit(value, reds, null);
        }

        @Override
        public ForecastValue forecastValue(DltIcaiPo data) {
            return data.getRedKill3();
        }

        @Override
        public void toConvert(DltIcaiPo data, ICaiForecast forecast) {
            data.setRedKill3(forecast.get(this.getNameZh()));
        }

        @Override
        public double rateThrottle() {
            return 0.80;
        }

        @Override
        public void calcRate(List<DltIcaiPo> data, DltMasterRatePo rate) {
            rate.calcRk3(data);
        }

        @Override
        public void calcRank(DltMasterRatePo rate, DltMasterRankPo rank) {
            rank.calcRk3Rank(rate);
        }

        @Override
        public RankValue rankValue(DltMasterRankPo rank) {
            return rank.getRedKill3();
        }

        @Override
        public StatHitValue rateValue(DltMasterRatePo rate) {
            return rate.getRk3Me();
        }

        @Override
        public void assemble(DltChartCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setRk3(v.getCensus()));
        }
    },
    RK6("rk6", "红球杀六") {
        @Override
        public void calcHit(ForecastValue value, List<String> reds, List<String> blues) {
            SsqChannel.RK3.calcHit(value, reds, null);
        }

        @Override
        public ForecastValue forecastValue(DltIcaiPo data) {
            return data.getRedKill6();
        }

        @Override
        public void toConvert(DltIcaiPo data, ICaiForecast forecast) {
            data.setRedKill6(forecast.get(this.getNameZh()));
        }

        @Override
        public double rateThrottle() {
            return 0.70;
        }

        @Override
        public void calcRate(List<DltIcaiPo> data, DltMasterRatePo rate) {
            rate.calcRk6(data);
        }

        @Override
        public void calcRank(DltMasterRatePo rate, DltMasterRankPo rank) {
            rank.calcRk6Rank(rate);
        }

        @Override
        public RankValue rankValue(DltMasterRankPo rank) {
            return rank.getRedKill6();
        }

        @Override
        public StatHitValue rateValue(DltMasterRatePo rate) {
            return rate.getRk6Me();
        }

        @Override
        public void assemble(DltChartCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setRk6(v.getCensus()));
        }
    },
    BLUE1("b1", "蓝球定一") {
        @Override
        public void calcHit(ForecastValue value, List<String> reds, List<String> blues) {
            RED1.calcHit(value, blues, null);
        }

        @Override
        public void toConvert(DltIcaiPo data, ICaiForecast forecast) {
            data.setBlue1(forecast.get(this.getNameZh()));
        }

        @Override
        public List<String> keys() {
            return ICaiConstants.DLT_BLUE_BALLS;
        }

        @Override
        public boolean isVipChannel() {
            return false;
        }

        @Override
        public double rateThrottle() {
            return 0.45;
        }

        @Override
        public ForecastValue forecastValue(DltIcaiPo data) {
            return data.getBlue1();
        }

        @Override
        public void calcRate(List<DltIcaiPo> data, DltMasterRatePo rate) {
            rate.calcBlue1(data);
        }

        @Override
        public void calcRank(DltMasterRatePo rate, DltMasterRankPo rank) {
            rank.calcBlue1Rank(rate);
        }

        @Override
        public RankValue rankValue(DltMasterRankPo rank) {
            return rank.getBlue1();
        }

        @Override
        public StatHitValue rateValue(DltMasterRatePo rate) {
            return rate.getB1Ne();
        }

        @Override
        public void assemble(DltChartCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setB1(v.getCensus()));
        }
    },
    BLUE2("b2", "蓝球定二") {
        @Override
        public void calcHit(ForecastValue value, List<String> reds, List<String> blues) {
            BLUE1.calcHit(value, reds, blues);
        }

        @Override
        public void toConvert(DltIcaiPo data, ICaiForecast forecast) {
            data.setBlue2(forecast.get(this.getNameZh()));
        }

        @Override
        public List<String> keys() {
            return ICaiConstants.DLT_BLUE_BALLS;
        }

        @Override
        public ForecastValue forecastValue(DltIcaiPo data) {
            return data.getBlue2();
        }

        @Override
        public double rateThrottle() {
            return 0.65;
        }

        @Override
        public void calcRate(List<DltIcaiPo> data, DltMasterRatePo rate) {
            rate.calcBlue2(data);
        }

        @Override
        public void calcRank(DltMasterRatePo rate, DltMasterRankPo rank) {
            rank.calcBlue2Rank(rate);
        }

        @Override
        public RankValue rankValue(DltMasterRankPo rank) {
            return rank.getBlue2();
        }

        @Override
        public StatHitValue rateValue(DltMasterRatePo rate) {
            return rate.getB2Me();
        }

        @Override
        public void assemble(DltChartCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setB2(v.getCensus()));
        }
    },
    BLUE6("b6", "蓝球定六") {
        @Override
        public void calcHit(ForecastValue value, List<String> reds, List<String> blues) {
            BLUE1.calcHit(value, reds, blues);
        }

        @Override
        public List<String> keys() {
            return ICaiConstants.DLT_BLUE_BALLS;
        }

        @Override
        public void toConvert(DltIcaiPo data, ICaiForecast forecast) {
            data.setBlue6(forecast.get(this.getNameZh()));
        }

        @Override
        public ForecastValue forecastValue(DltIcaiPo data) {
            return data.getBlue6();
        }

        @Override
        public double rateThrottle() {
            return 0.95;
        }

        @Override
        public void calcRate(List<DltIcaiPo> data, DltMasterRatePo rate) {
            rate.calcBlue6(data);
        }

        @Override
        public void calcRank(DltMasterRatePo rate, DltMasterRankPo rank) {
            rank.calcBlue6Rank(rate);
        }

        @Override
        public RankValue rankValue(DltMasterRankPo rank) {
            return rank.getBlue6();
        }

        @Override
        public StatHitValue rateValue(DltMasterRatePo rate) {
            return rate.getB6Me();
        }

        @Override
        public void assemble(DltChartCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setB6(v.getCensus()));
        }
    },
    BK("bk", "蓝球杀三") {
        @Override
        public void calcHit(ForecastValue value, List<String> reds, List<String> blues) {
            RK3.calcHit(value, blues, null);
        }

        @Override
        public void toConvert(DltIcaiPo data, ICaiForecast forecast) {
            data.setBlueKill3(forecast.get(this.getNameZh()));
        }

        @Override
        public List<String> keys() {
            return ICaiConstants.DLT_BLUE_BALLS;
        }

        @Override
        public ForecastValue forecastValue(DltIcaiPo data) {
            return data.getBlueKill3();
        }

        @Override
        public double rateThrottle() {
            return 0.75;
        }

        @Override
        public void calcRate(List<DltIcaiPo> data, DltMasterRatePo rate) {
            rate.calcBk(data);
        }

        @Override
        public void calcRank(DltMasterRatePo rate, DltMasterRankPo rank) {
            rank.calcBlueKillRank(rate);
        }

        @Override
        public RankValue rankValue(DltMasterRankPo rank) {
            return rank.getBlueKill();
        }

        @Override
        public StatHitValue rateValue(DltMasterRatePo rate) {
            return rate.getBkMe();
        }

        @Override
        public void assemble(DltChartCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setBk(v.getCensus()));
        }
    };

    private final String channel;
    private final String nameZh;

    DltChannel(String channel, String nameZh) {
        this.channel = channel;
        this.nameZh  = nameZh;
    }

    public String getChannel() {
        return channel;
    }

    public String getNameZh() {
        return nameZh;
    }

    /**
     * 获取枚举变量唯一值
     */
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

    public boolean isHotChannel() {
        return true;
    }

    public boolean isVipChannel() {
        return true;
    }

    public double rateThrottle() {
        return 1.0;
    }

    public abstract ForecastValue forecastValue(DltIcaiPo data);

    public abstract void calcHit(ForecastValue value, List<String> reds, List<String> blues);

    public abstract void calcRate(List<DltIcaiPo> data, DltMasterRatePo rate);

    public abstract void calcRank(DltMasterRatePo rate, DltMasterRankPo rank);

    public abstract RankValue rankValue(DltMasterRankPo rank);

    public abstract StatHitValue rateValue(DltMasterRatePo rate);

    public abstract void toConvert(DltIcaiPo data, ICaiForecast forecast);

    public List<String> keys() {
        return ICaiConstants.DLT_RED_BALLS;
    }

    public void assemble(DltChartCensusVo census, BaseLottoCensus po) {
    }

    public static DltChannel findOf(String type) {
        return Arrays.stream(values())
                     .filter(v -> v.getChannel().equals(type))
                     .findFirst()
                     .orElseThrow(() -> new RuntimeException("数据字段错误."));
    }
}
