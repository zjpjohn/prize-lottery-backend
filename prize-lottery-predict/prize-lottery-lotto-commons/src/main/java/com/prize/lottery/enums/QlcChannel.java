package com.prize.lottery.enums;


import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import com.prize.lottery.po.qlc.QlcIcaiPo;
import com.prize.lottery.po.qlc.QlcMasterRankPo;
import com.prize.lottery.po.qlc.QlcMasterRatePo;
import com.prize.lottery.po.share.BaseLottoCensus;
import com.prize.lottery.value.ForecastValue;
import com.prize.lottery.value.ICaiForecast;
import com.prize.lottery.value.RankValue;
import com.prize.lottery.value.StatHitValue;
import com.prize.lottery.vo.qlc.QlcChartCensusVo;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@TypeHandler(type = Type.ENUM)
public enum QlcChannel implements Value<String> {

    RED1("r1", "独胆") {
        @Override
        public ForecastValue forecastValue(QlcIcaiPo data) {
            return data.getRed1();
        }

        @Override
        public void calcHit(ForecastValue value, List<String> reds) {
            SsqChannel.RED1.calcHit(value, reds, null);
        }

        @Override
        public void calcRate(List<QlcIcaiPo> data, QlcMasterRatePo rate) {
            rate.calcRed1(data);
        }

        @Override
        public void toConvert(QlcIcaiPo data, ICaiForecast forecast) {
            data.setRed1(forecast.get(this.getNameZh()));
        }

        @Override
        public void calcRank(QlcMasterRatePo rate, QlcMasterRankPo rank) {
            rank.calcRed1Rank(rate);
        }

        @Override
        public double rateThrottle() {
            return 0.45;
        }

        @Override
        public RankValue rankValue(QlcMasterRankPo rank) {
            return rank.getRed1();
        }

        @Override
        public StatHitValue rateValue(QlcMasterRatePo rate) {
            return rate.getR1Ne();
        }

        @Override
        public void assemble(QlcChartCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setR1(v.getCensus()));
        }
    },
    RED2("r2", "双胆") {
        @Override
        public ForecastValue forecastValue(QlcIcaiPo data) {
            return data.getRed2();
        }

        @Override
        public void calcHit(ForecastValue value, List<String> reds) {
            SsqChannel.RED1.calcHit(value, reds, null);
        }

        @Override
        public void toConvert(QlcIcaiPo data, ICaiForecast forecast) {
            data.setRed2(forecast.get(this.getNameZh()));
        }

        @Override
        public void calcRate(List<QlcIcaiPo> data, QlcMasterRatePo rate) {
            rate.calcRed2(data);
        }

        @Override
        public void calcRank(QlcMasterRatePo rate, QlcMasterRankPo rank) {
            rank.calcRed2Rank(rate);
        }

        @Override
        public double rateThrottle() {
            return 0.55;
        }

        @Override
        public RankValue rankValue(QlcMasterRankPo rank) {
            return rank.getRed2();
        }

        @Override
        public StatHitValue rateValue(QlcMasterRatePo rate) {
            return rate.getR2Me();
        }

        @Override
        public void assemble(QlcChartCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setR2(v.getCensus()));
        }
    },
    RED3("r3", "三胆") {
        @Override
        public ForecastValue forecastValue(QlcIcaiPo data) {
            return data.getRed3();
        }

        @Override
        public void calcHit(ForecastValue value, List<String> reds) {
            SsqChannel.RED1.calcHit(value, reds, null);
        }

        @Override
        public void calcRate(List<QlcIcaiPo> data, QlcMasterRatePo rate) {
            rate.calcRed3(data);
        }

        @Override
        public void toConvert(QlcIcaiPo data, ICaiForecast forecast) {
            data.setRed3(forecast.get(this.getNameZh()));
        }

        @Override
        public void calcRank(QlcMasterRatePo rate, QlcMasterRankPo rank) {
            rank.calcRed3Rank(rate);
        }

        @Override
        public double rateThrottle() {
            return 0.75;
        }

        @Override
        public RankValue rankValue(QlcMasterRankPo rank) {
            return rank.getRed3();
        }

        @Override
        public StatHitValue rateValue(QlcMasterRatePo rate) {
            return rate.getR3Hi();
        }

        @Override
        public void assemble(QlcChartCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setR3(v.getCensus()));
        }
    },
    RED12("r12", "12码") {
        @Override
        public int qThrottle() {
            return 4;
        }

        @Override
        public ForecastValue forecastValue(QlcIcaiPo data) {
            return data.getRed12();
        }

        @Override
        public void calcHit(ForecastValue value, List<String> reds) {
            SsqChannel.RED1.calcHit(value, reds, null);
        }

        @Override
        public void toConvert(QlcIcaiPo data, ICaiForecast forecast) {
            data.setRed12(forecast.get(this.getNameZh()));
        }

        @Override
        public void calcRate(List<QlcIcaiPo> data, QlcMasterRatePo rate) {
            rate.calcRed12(data);
        }

        @Override
        public void calcRank(QlcMasterRatePo rate, QlcMasterRankPo rank) {
            rank.calcRed12Rank(rate);
        }

        @Override
        public double rateThrottle() {
            return 0.85;
        }

        @Override
        public RankValue rankValue(QlcMasterRankPo rank) {
            return rank.getRed12();
        }

        @Override
        public StatHitValue rateValue(QlcMasterRatePo rate) {
            return rate.getR12Me();
        }

        @Override
        public void assemble(QlcChartCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setR12(v.getCensus()));
        }
    },
    RED18("r18", "18码") {
        @Override
        public int qThrottle() {
            return 5;
        }

        @Override
        public ForecastValue forecastValue(QlcIcaiPo data) {
            return data.getRed18();
        }

        @Override
        public void calcHit(ForecastValue value, List<String> reds) {
            SsqChannel.RED1.calcHit(value, reds, null);
        }

        @Override
        public void calcRate(List<QlcIcaiPo> data, QlcMasterRatePo rate) {
            rate.calcRed18(data);
        }

        @Override
        public void toConvert(QlcIcaiPo data, ICaiForecast forecast) {
            data.setRed18(forecast.get(this.getNameZh()));
        }

        @Override
        public void calcRank(QlcMasterRatePo rate, QlcMasterRankPo rank) {
            rank.calcRed18Rank(rate);
        }

        @Override
        public double rateThrottle() {
            return 0.95;
        }

        @Override
        public RankValue rankValue(QlcMasterRankPo rank) {
            return rank.getRed18();
        }

        @Override
        public StatHitValue rateValue(QlcMasterRatePo rate) {
            return rate.getR18Hi();
        }

        @Override
        public void assemble(QlcChartCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setR18(v.getCensus()));
        }
    },
    RED22("r22", "22码") {
        @Override
        public int qThrottle() {
            return 6;
        }

        @Override
        public ForecastValue forecastValue(QlcIcaiPo data) {
            return data.getRed22();
        }

        @Override
        public void calcHit(ForecastValue value, List<String> reds) {
            SsqChannel.RED1.calcHit(value, reds, null);
        }

        @Override
        public void toConvert(QlcIcaiPo data, ICaiForecast forecast) {
            data.setRed22(forecast.get(this.getNameZh()));
        }

        @Override
        public void calcRate(List<QlcIcaiPo> data, QlcMasterRatePo rate) {
            rate.calcRed22(data);
        }

        @Override
        public void calcRank(QlcMasterRatePo rate, QlcMasterRankPo rank) {
            rank.calcRed22Rank(rate);
        }

        @Override
        public double rateThrottle() {
            return 0.95;
        }

        @Override
        public RankValue rankValue(QlcMasterRankPo rank) {
            return rank.getRed22();
        }

        @Override
        public StatHitValue rateValue(QlcMasterRatePo rate) {
            return rate.getR22Hi();
        }

        @Override
        public void assemble(QlcChartCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setR22(v.getCensus()));
        }
    },
    RK3("k3", "杀三码") {
        @Override
        public ForecastValue forecastValue(QlcIcaiPo data) {
            return data.getKill3();
        }

        @Override
        public void toConvert(QlcIcaiPo data, ICaiForecast forecast) {
            data.setKill3(forecast.get(this.getNameZh()));
        }

        @Override
        public void calcHit(ForecastValue value, List<String> reds) {
            SsqChannel.RK3.calcHit(value, reds, null);
        }

        @Override
        public void calcRate(List<QlcIcaiPo> data, QlcMasterRatePo rate) {
            rate.calcKill3(data);
        }

        @Override
        public void calcRank(QlcMasterRatePo rate, QlcMasterRankPo rank) {
            rank.calcKill3Rank(rate);
        }

        @Override
        public double rateThrottle() {
            return 0.65;
        }

        @Override
        public RankValue rankValue(QlcMasterRankPo rank) {
            return rank.getKill3();
        }

        @Override
        public StatHitValue rateValue(QlcMasterRatePo rate) {
            return rate.getK3Me();
        }

        @Override
        public void assemble(QlcChartCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setK3(v.getCensus()));
        }
    },
    RK6("k6", "杀六码") {
        @Override
        public ForecastValue forecastValue(QlcIcaiPo data) {
            return data.getKill6();
        }

        @Override
        public void toConvert(QlcIcaiPo data, ICaiForecast forecast) {
            data.setKill6(forecast.get(this.getNameZh()));
        }

        @Override
        public void calcHit(ForecastValue value, List<String> reds) {
            SsqChannel.RK3.calcHit(value, reds, null);
        }

        @Override
        public void calcRate(List<QlcIcaiPo> data, QlcMasterRatePo rate) {
            rate.calcKill6(data);
        }

        @Override
        public void calcRank(QlcMasterRatePo rate, QlcMasterRankPo rank) {
            rank.calcKill6Rank(rate);
        }

        @Override
        public double rateThrottle() {
            return 0.45;
        }

        @Override
        public RankValue rankValue(QlcMasterRankPo rank) {
            return rank.getKill6();
        }

        @Override
        public StatHitValue rateValue(QlcMasterRatePo rate) {
            return rate.getK6Me();
        }

        @Override
        public void assemble(QlcChartCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setK6(v.getCensus()));
        }
    };

    private final String channel;
    private final String nameZh;

    QlcChannel(String channel, String nameZh) {
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

    public boolean isHotChannel() {
        return true;
    }

    public boolean isVipChannel() {
        return true;
    }

    public double rateThrottle() {
        return 1.0;
    }

    public abstract ForecastValue forecastValue(QlcIcaiPo data);

    public abstract void calcHit(ForecastValue value, List<String> reds);

    public abstract void calcRate(List<QlcIcaiPo> data, QlcMasterRatePo rate);

    public abstract void calcRank(QlcMasterRatePo rate, QlcMasterRankPo rank);

    public abstract RankValue rankValue(QlcMasterRankPo rank);

    public abstract StatHitValue rateValue(QlcMasterRatePo rate);

    public abstract void toConvert(QlcIcaiPo data, ICaiForecast forecast);

    public void assemble(QlcChartCensusVo census, BaseLottoCensus po) {
    }

    public int qThrottle() {
        return 1;
    }

    public static QlcChannel findOf(String type) {
        return Arrays.stream(values())
                     .filter(v -> v.getChannel().equals(type))
                     .findFirst()
                     .orElseThrow(() -> new RuntimeException("数据字段错误."));
    }
}
