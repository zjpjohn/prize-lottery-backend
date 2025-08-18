package com.prize.lottery.enums;


import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import com.prize.lottery.po.share.BaseLottoCensus;
import com.prize.lottery.po.ssq.SsqIcaiPo;
import com.prize.lottery.po.ssq.SsqMasterRankPo;
import com.prize.lottery.po.ssq.SsqMasterRatePo;
import com.prize.lottery.utils.ICaiConstants;
import com.prize.lottery.value.ForecastValue;
import com.prize.lottery.value.ICaiForecast;
import com.prize.lottery.value.RankValue;
import com.prize.lottery.value.StatHitValue;
import com.prize.lottery.vo.ssq.SsqChartCensusVo;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@TypeHandler(type = Type.ENUM)
public enum SsqChannel implements Value<String> {

    RED1("r1", "红球独胆") {
        @Override
        public ForecastValue forecastValue(SsqIcaiPo icai) {
            return icai.getRed1();
        }

        @Override
        public void calcRate(List<SsqIcaiPo> data, SsqMasterRatePo rate) {
            rate.calcRed1(data);
        }

        @Override
        public void calcRank(SsqMasterRatePo rate, SsqMasterRankPo rank) {
            rank.calcRed1Rank(rate);
        }

        @Override
        public RankValue rankValue(SsqMasterRankPo rank) {
            return rank.getRed1();
        }

        @Override
        public void toConvert(SsqIcaiPo data, ICaiForecast forecast) {
            data.setRed1(forecast.get(this.getNameZh()));
        }

        @Override
        public StatHitValue rateValue(SsqMasterRatePo rate) {
            return rate.getR1Me();
        }

        @Override
        public double rateThrottle() {
            return 0.40;
        }

        @Override
        public void calcHit(ForecastValue value, List<String> reds, String blue) {
            int    hit  = 0;
            String data = value.getData();
            for (String ball : reds) {
                if (data.contains(ball)) {
                    hit  = hit + 1;
                    data = data.replace(ball, "[" + ball + "]");
                }
            }
            value.setHitData(data);
            value.setDataHit(hit);
        }

        @Override
        public void assemble(SsqChartCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setR1(v.getCensus()));
        }
    },
    RED2("r2", "红球双胆") {
        @Override
        public ForecastValue forecastValue(SsqIcaiPo icai) {
            return icai.getRed2();
        }

        @Override
        public void calcHit(ForecastValue value, List<String> reds, String blue) {
            RED1.calcHit(value, reds, blue);
        }

        @Override
        public void calcRank(SsqMasterRatePo rate, SsqMasterRankPo rank) {
            rank.calcRed2Rank(rate);
        }

        @Override
        public void calcRate(List<SsqIcaiPo> data, SsqMasterRatePo rate) {
            rate.calcRed2(data);
        }

        @Override
        public void toConvert(SsqIcaiPo data, ICaiForecast forecast) {
            data.setRed2(forecast.get(this.getNameZh()));
        }

        @Override
        public double rateThrottle() {
            return 0.55;
        }

        @Override
        public RankValue rankValue(SsqMasterRankPo rank) {
            return rank.getRed2();
        }

        @Override
        public StatHitValue rateValue(SsqMasterRatePo rate) {
            return rate.getR2Me();
        }

        @Override
        public void assemble(SsqChartCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setR2(v.getCensus()));
        }
    },
    RED3("r3", "红球三胆") {
        @Override
        public ForecastValue forecastValue(SsqIcaiPo icai) {
            return icai.getRed3();
        }

        @Override
        public void calcHit(ForecastValue value, List<String> reds, String blue) {
            RED1.calcHit(value, reds, blue);
        }

        @Override
        public void calcRank(SsqMasterRatePo rate, SsqMasterRankPo rank) {
            rank.calcRed3Rank(rate);
        }

        @Override
        public void toConvert(SsqIcaiPo data, ICaiForecast forecast) {
            data.setRed3(forecast.get(this.getNameZh()));
        }

        @Override
        public void calcRate(List<SsqIcaiPo> data, SsqMasterRatePo rate) {
            rate.calcRed3(data);
        }

        @Override
        public double rateThrottle() {
            return 0.75;
        }

        @Override
        public RankValue rankValue(SsqMasterRankPo rank) {
            return rank.getRed3();
        }

        @Override
        public StatHitValue rateValue(SsqMasterRatePo rate) {
            return rate.getR3Me();
        }

        @Override
        public void assemble(SsqChartCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setR3(v.getCensus()));
        }
    },
    RED12("r12", "红球12码") {
        @Override
        public ForecastValue forecastValue(SsqIcaiPo icai) {
            return icai.getRed12();
        }

        @Override
        public void calcHit(ForecastValue value, List<String> reds, String blue) {
            RED1.calcHit(value, reds, blue);
        }

        @Override
        public void calcRank(SsqMasterRatePo rate, SsqMasterRankPo rank) {
            rank.calcRed12Rank(rate);
        }

        @Override
        public void calcRate(List<SsqIcaiPo> data, SsqMasterRatePo rate) {
            rate.calcRed12(data);
        }

        @Override
        public void toConvert(SsqIcaiPo data, ICaiForecast forecast) {
            data.setRed12(forecast.get(this.getNameZh()));
        }

        @Override
        public double rateThrottle() {
            return 0.60;
        }

        @Override
        public RankValue rankValue(SsqMasterRankPo rank) {
            return rank.getRed12();
        }

        @Override
        public StatHitValue rateValue(SsqMasterRatePo rate) {
            return rate.getR12Me();
        }

        @Override
        public void assemble(SsqChartCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setR12(v.getCensus()));
        }
    },
    RED20("r20", "红球20码") {
        @Override
        public ForecastValue forecastValue(SsqIcaiPo icai) {
            return icai.getRed20();
        }

        @Override
        public void calcHit(ForecastValue value, List<String> reds, String blue) {
            RED1.calcHit(value, reds, blue);
        }

        @Override
        public void calcRank(SsqMasterRatePo rate, SsqMasterRankPo rank) {
            rank.calcRed20Rank(rate);
        }

        @Override
        public void calcRate(List<SsqIcaiPo> data, SsqMasterRatePo rate) {
            rate.calcRed20(data);
        }

        @Override
        public void toConvert(SsqIcaiPo data, ICaiForecast forecast) {
            data.setRed20(forecast.get(this.getNameZh()));
        }

        @Override
        public double rateThrottle() {
            return 0.75;
        }

        @Override
        public RankValue rankValue(SsqMasterRankPo rank) {
            return rank.getRed20();
        }

        @Override
        public StatHitValue rateValue(SsqMasterRatePo rate) {
            return rate.getR20Me();
        }

        @Override
        public void assemble(SsqChartCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setR20(v.getCensus()));
        }
    },
    RED25("r25", "红球25码") {
        @Override
        public ForecastValue forecastValue(SsqIcaiPo icai) {
            return icai.getRed25();
        }

        @Override
        public void calcHit(ForecastValue value, List<String> reds, String blue) {
            RED1.calcHit(value, reds, blue);
        }

        @Override
        public void calcRank(SsqMasterRatePo rate, SsqMasterRankPo rank) {
            rank.calcRed25Rank(rate);
        }

        @Override
        public void toConvert(SsqIcaiPo data, ICaiForecast forecast) {
            data.setRed25(forecast.get(this.getNameZh()));
        }

        @Override
        public void calcRate(List<SsqIcaiPo> data, SsqMasterRatePo rate) {
            rate.calcRed25(data);
        }

        @Override
        public double rateThrottle() {
            return 0.75;
        }

        @Override
        public RankValue rankValue(SsqMasterRankPo rank) {
            return rank.getRed25();
        }

        @Override
        public StatHitValue rateValue(SsqMasterRatePo rate) {
            return rate.getR25Me();
        }

        @Override
        public void assemble(SsqChartCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setR25(v.getCensus()));
        }

    },
    RK3("rk3", "红球杀三") {
        @Override
        public ForecastValue forecastValue(SsqIcaiPo icai) {
            return icai.getRedKill3();
        }

        @Override
        public void calcRate(List<SsqIcaiPo> data, SsqMasterRatePo rate) {
            rate.calcRk3(data);
        }

        @Override
        public void calcRank(SsqMasterRatePo rate, SsqMasterRankPo rank) {
            rank.calcRedKill3Rank(rate);
        }

        @Override
        public void toConvert(SsqIcaiPo data, ICaiForecast forecast) {
            data.setRedKill3(forecast.get(this.getNameZh()));
        }

        @Override
        public RankValue rankValue(SsqMasterRankPo rank) {
            return rank.getRedKill3();
        }


        @Override
        public StatHitValue rateValue(SsqMasterRatePo rate) {
            return rate.getRk3Me();
        }

        @Override
        public double rateThrottle() {
            return 0.65;
        }

        @Override
        public void calcHit(ForecastValue value, List<String> reds, String blue) {
            int      hit   = 0;
            String[] balls = value.getData().split("\\s+");
            for (int i = 0; i < balls.length; i++) {
                String ball = balls[i].trim();
                if (!reds.contains(ball)) {
                    hit      = hit + 1;
                    balls[i] = "[" + ball + "]";
                }
            }
            value.setDataHit(balls.length == hit ? 1 : 0);
            value.setHitData(String.join(" ", balls));
        }

        @Override
        public void assemble(SsqChartCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setRk3(v.getCensus()));
        }
    },
    RK6("rk6", "红球杀六") {
        @Override
        public ForecastValue forecastValue(SsqIcaiPo icai) {
            return icai.getRedKill6();
        }

        @Override
        public void calcHit(ForecastValue value, List<String> reds, String blue) {
            RK3.calcHit(value, reds, blue);
        }


        @Override
        public void toConvert(SsqIcaiPo data, ICaiForecast forecast) {
            data.setRedKill6(forecast.get(this.getNameZh()));
        }

        @Override
        public void calcRank(SsqMasterRatePo rate, SsqMasterRankPo rank) {
            rank.calcRedKill6Rank(rate);
        }

        @Override
        public void calcRate(List<SsqIcaiPo> data, SsqMasterRatePo rate) {
            rate.calcRk6(data);
        }

        @Override
        public double rateThrottle() {
            return 0.55;
        }

        @Override
        public RankValue rankValue(SsqMasterRankPo rank) {
            return rank.getRedKill6();
        }

        @Override
        public StatHitValue rateValue(SsqMasterRatePo rate) {
            return rate.getRk6Me();
        }

        @Override
        public void assemble(SsqChartCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setRk6(v.getCensus()));
        }
    },
    BLUE3("b3", "蓝球定三") {
        @Override
        public ForecastValue forecastValue(SsqIcaiPo icai) {
            return icai.getBlue3();
        }

        @Override
        public void calcRate(List<SsqIcaiPo> data, SsqMasterRatePo rate) {
            rate.calcB3(data);
        }

        @Override
        public void calcRank(SsqMasterRatePo rate, SsqMasterRankPo rank) {
            rank.calcBlue3Rank(rate);
        }


        @Override
        public void toConvert(SsqIcaiPo data, ICaiForecast forecast) {
            data.setBlue3(forecast.get(this.getNameZh()));
        }

        @Override
        public RankValue rankValue(SsqMasterRankPo rank) {
            return rank.getBlue3();
        }

        @Override
        public StatHitValue rateValue(SsqMasterRatePo rate) {
            return rate.getB3Me();
        }

        @Override
        public double rateThrottle() {
            return 0.45;
        }

        @Override
        public List<String> keys() {
            return ICaiConstants.SSQ_BLUE_BALLS;
        }

        @Override
        public void calcHit(ForecastValue value, List<String> reds, String blue) {
            int    hit  = 0;
            String data = value.getData();
            if (data.contains(blue)) {
                hit  = 1;
                data = data.replace(blue, "[" + blue + "]");
            }
            value.setDataHit(hit);
            value.setHitData(data);
        }

        @Override
        public void assemble(SsqChartCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setB3(v.getCensus()));
        }
    },
    BLUE5("b5", "蓝球定五") {
        @Override
        public ForecastValue forecastValue(SsqIcaiPo icai) {
            return icai.getBlue5();
        }

        @Override
        public void calcHit(ForecastValue value, List<String> reds, String blue) {
            BLUE3.calcHit(value, reds, blue);
        }

        @Override
        public void calcRank(SsqMasterRatePo rate, SsqMasterRankPo rank) {
            rank.calcBlue6Rank(rate);
        }

        @Override
        public void calcRate(List<SsqIcaiPo> data, SsqMasterRatePo rate) {
            rate.calcB5(data);
        }

        @Override
        public void toConvert(SsqIcaiPo data, ICaiForecast forecast) {
            data.setBlue5(forecast.get(this.getNameZh()));
        }

        @Override
        public double rateThrottle() {
            return 0.55;
        }

        @Override
        public List<String> keys() {
            return ICaiConstants.SSQ_BLUE_BALLS;
        }

        @Override
        public RankValue rankValue(SsqMasterRankPo rank) {
            return rank.getBlue5();
        }

        @Override
        public StatHitValue rateValue(SsqMasterRatePo rate) {
            return rate.getB5Me();
        }

        @Override
        public void assemble(SsqChartCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setB5(v.getCensus()));
        }
    },
    BK("bk", "蓝球杀五") {
        @Override
        public ForecastValue forecastValue(SsqIcaiPo icai) {
            return icai.getBlueKill();
        }

        @Override
        public void calcRate(List<SsqIcaiPo> data, SsqMasterRatePo rate) {
            rate.calcBk(data);
        }

        @Override
        public void calcRank(SsqMasterRatePo rate, SsqMasterRankPo rank) {
            rank.calcBlueKillRank(rate);
        }

        @Override
        public void toConvert(SsqIcaiPo data, ICaiForecast forecast) {
            data.setBlueKill(forecast.get(this.getNameZh()));
        }

        @Override
        public double rateThrottle() {
            return 0.85;
        }

        @Override
        public List<String> keys() {
            return ICaiConstants.SSQ_BLUE_BALLS;
        }

        @Override
        public RankValue rankValue(SsqMasterRankPo rank) {
            return rank.getBlueKill();
        }

        @Override
        public StatHitValue rateValue(SsqMasterRatePo rate) {
            return rate.getBkMe();
        }

        @Override
        public void assemble(SsqChartCensusVo census, BaseLottoCensus po) {
            Optional.ofNullable(po).ifPresent(v -> census.setBk(v.getCensus()));
        }

        @Override
        public void calcHit(ForecastValue value, List<String> reds, String blue) {
            int      hit   = 0;
            String[] balls = value.getData().split("\\s+");
            for (int i = 0; i < balls.length; i++) {
                String ball = balls[i].trim();
                if (!blue.equals(ball)) {
                    hit      = hit + 1;
                    balls[i] = "[" + ball + "]";
                }
            }
            value.setDataHit(balls.length == hit ? 1 : 0);
            value.setHitData(String.join(" ", balls));
        }
    };

    //渠道标识
    private final String channel;
    //渠道名称
    private final String nameZh;

    SsqChannel(String channel, String nameZh) {
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
        return channel;
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

    public abstract ForecastValue forecastValue(SsqIcaiPo icai);

    public abstract void calcHit(ForecastValue value, List<String> reds, String blue);

    public abstract void calcRate(List<SsqIcaiPo> data, SsqMasterRatePo rate);

    public abstract void calcRank(SsqMasterRatePo rate, SsqMasterRankPo rank);

    public abstract RankValue rankValue(SsqMasterRankPo rank);

    public abstract StatHitValue rateValue(SsqMasterRatePo rate);

    public abstract void toConvert(SsqIcaiPo data, ICaiForecast forecast);

    public List<String> keys() {
        return ICaiConstants.SSQ_RED_BALLS;
    }

    public void assemble(SsqChartCensusVo census, BaseLottoCensus po) {
    }

    public static SsqChannel findOf(String type) {
        return Arrays.stream(values())
                     .filter(v -> v.getChannel().equals(type))
                     .findFirst()
                     .orElseThrow(() -> new RuntimeException("数据字段错误."));
    }
}
