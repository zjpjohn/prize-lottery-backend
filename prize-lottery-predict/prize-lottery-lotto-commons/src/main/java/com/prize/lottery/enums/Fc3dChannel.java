package com.prize.lottery.enums;


import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import com.google.common.collect.Maps;
import com.prize.lottery.po.fc3d.Fc3dIcaiPo;
import com.prize.lottery.po.fc3d.Fc3dLottoCensusPo;
import com.prize.lottery.po.fc3d.Fc3dMasterRankPo;
import com.prize.lottery.po.fc3d.Fc3dMasterRatePo;
import com.prize.lottery.utils.ICaiConstants;
import com.prize.lottery.utils.WeightCalculator;
import com.prize.lottery.value.ForecastValue;
import com.prize.lottery.value.ICaiForecast;
import com.prize.lottery.value.RankValue;
import com.prize.lottery.value.StatHitValue;
import com.prize.lottery.vo.NumberThreeCensusVo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@TypeHandler(type = Type.ENUM)
public enum Fc3dChannel implements Value<String> {

    DAN1("d1", "独胆") {

        final Map<Integer, Integer> weightTable = Maps.newHashMap();
        {
            weightTable.put(0, 0);
            weightTable.put(1, 3);
        }

        @Override
        public void calcHit(ForecastValue value, List<String> lottery) {
            int                  hit  = 0;
            String               data = value.getData();
            Map<String, Integer> map  = ICaiConstants.judgeLottery(lottery);
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                if (data.contains(entry.getKey())) {
                    hit  = hit + entry.getValue();
                    data = data.replaceAll(entry.getKey(), "[" + entry.getKey() + "]");
                }
            }
            value.setDataHit(Math.min(hit, data.length()));
            value.setHitData(data);
        }

        @Override
        public Map<Integer, Integer> getWeightTable() {
            return weightTable;
        }

        @Override
        public ForecastValue forecastValue(Fc3dIcaiPo fc3dIcai) {
            return fc3dIcai.getDan1();
        }

        @Override
        public void toConvert(Fc3dIcaiPo data, ICaiForecast forecast) {
            data.setDan1(forecast.get(this.getNameZh()));
        }

        @Override
        public StatHitValue rateValue(Fc3dMasterRatePo rate) {
            return rate.getD1Me();
        }

        @Override
        public void calcRate(List<Fc3dIcaiPo> data, Fc3dMasterRatePo rate) {
            rate.calcDan1(data);
        }

        @Override
        public void setWeight(Fc3dMasterRankPo rank, double weight) {
            rank.setDan1(new RankValue(weight));
        }

        @Override
        public Double calcWeight(int hit, Fc3dMasterRankPo last) {
            Double oldVal = Optional.ofNullable(last).map(v -> v.getDan1().getWeight()).orElse(0.0);
            return WeightCalculator.compute(oldVal, weightValue(hit));
        }

        @Override
        public void calcRank(Fc3dMasterRatePo rate, Fc3dMasterRankPo rank) {
            rank.calcD1Rank(rate);
        }

        @Override
        public RankValue rankValue(Fc3dMasterRankPo rank) {
            return rank.getDan1();
        }

        @Override
        public void assemble(NumberThreeCensusVo census, Fc3dLottoCensusPo po) {
            Optional.ofNullable(po).ifPresent(v -> census.setD1(v.getCensus()));
        }
    },
    DAN2("d2", "双胆") {

        final Map<Integer, Integer> weightTable = Maps.newHashMap();
        {
            weightTable.put(0, 0);
            weightTable.put(1, 2);
            weightTable.put(2, 3);
        }

        @Override
        public void calcHit(ForecastValue value, List<String> lottery) {
            DAN1.calcHit(value, lottery);
        }

        @Override
        public Map<Integer, Integer> getWeightTable() {
            return weightTable;
        }

        @Override
        public double rateThrottle() {
            return 0.80;
        }

        @Override
        public ForecastValue forecastValue(Fc3dIcaiPo fc3dIcai) {
            return fc3dIcai.getDan2();
        }

        @Override
        public void toConvert(Fc3dIcaiPo data, ICaiForecast forecast) {
            data.setDan2(forecast.get(this.getNameZh()));
        }

        @Override
        public StatHitValue rateValue(Fc3dMasterRatePo rate) {
            return rate.getD2Me();
        }

        @Override
        public void calcRate(List<Fc3dIcaiPo> data, Fc3dMasterRatePo rate) {
            rate.calcDan2(data);
        }

        @Override
        public void setWeight(Fc3dMasterRankPo rank, double weight) {
            rank.setDan2(new RankValue(weight));
        }

        @Override
        public Double calcWeight(int hit, Fc3dMasterRankPo last) {
            Double oldVal = Optional.ofNullable(last).map(v -> v.getDan2().getWeight()).orElse(0.0);
            return WeightCalculator.compute(oldVal, weightValue(hit));
        }

        @Override
        public void calcRank(Fc3dMasterRatePo rate, Fc3dMasterRankPo rank) {
            rank.calcD2Rank(rate);
        }

        @Override
        public RankValue rankValue(Fc3dMasterRankPo rank) {
            return rank.getDan2();
        }

        @Override
        public void assemble(NumberThreeCensusVo census, Fc3dLottoCensusPo po) {
            Optional.ofNullable(po).ifPresent(v -> census.setD2(v.getCensus()));
        }
    },
    DAN3("d3", "三胆") {

        final Map<Integer, Integer> weightTable = Maps.newHashMap();
        {
            weightTable.put(0, 0);
            weightTable.put(1, 1);
            weightTable.put(2, 2);
            weightTable.put(3, 3);
        }

        @Override
        public void calcHit(ForecastValue value, List<String> lottery) {
            DAN1.calcHit(value, lottery);
        }

        @Override
        public Map<Integer, Integer> getWeightTable() {
            return weightTable;
        }

        @Override
        public double rateThrottle() {
            return 0.93;
        }

        @Override
        public ForecastValue forecastValue(Fc3dIcaiPo fc3dIcai) {
            return fc3dIcai.getDan3();
        }

        @Override
        public void toConvert(Fc3dIcaiPo data, ICaiForecast forecast) {
            data.setDan3(forecast.get(this.getNameZh()));
        }

        @Override
        public StatHitValue rateValue(Fc3dMasterRatePo rate) {
            return rate.getD3Hi();
        }

        @Override
        public void calcRate(List<Fc3dIcaiPo> data, Fc3dMasterRatePo rate) {
            rate.calcDan3(data);
        }

        @Override
        public void setWeight(Fc3dMasterRankPo rank, double weight) {
            rank.setDan3(new RankValue(weight));
        }

        @Override
        public Double calcWeight(int hit, Fc3dMasterRankPo last) {
            Double oldVal = Optional.ofNullable(last).map(v -> v.getDan3().getWeight()).orElse(0.0);
            return WeightCalculator.compute(oldVal, weightValue(hit));
        }

        @Override
        public void calcRank(Fc3dMasterRatePo rate, Fc3dMasterRankPo rank) {
            rank.calcD3Rank(rate);
        }

        @Override
        public RankValue rankValue(Fc3dMasterRankPo rank) {
            return rank.getDan3();
        }

        @Override
        public void assemble(NumberThreeCensusVo census, Fc3dLottoCensusPo po) {
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
            DAN1.calcHit(value, lottery);
        }

        @Override
        public Map<Integer, Integer> getWeightTable() {
            return DAN3.getWeightTable();
        }

        @Override
        public double rateThrottle() {
            return 0.80;
        }

        @Override
        public ForecastValue forecastValue(Fc3dIcaiPo fc3dIcai) {
            return fc3dIcai.getCom5();
        }

        @Override
        public void toConvert(Fc3dIcaiPo data, ICaiForecast forecast) {
            data.setCom5(forecast.get(this.getNameZh()));
        }

        @Override
        public StatHitValue rateValue(Fc3dMasterRatePo rate) {
            return rate.getC5Me();
        }

        @Override
        public void calcRate(List<Fc3dIcaiPo> data, Fc3dMasterRatePo rate) {
            rate.calcCom5(data);
        }

        @Override
        public void setWeight(Fc3dMasterRankPo rank, double weight) {
            rank.setCom5(new RankValue(weight));
        }

        @Override
        public Double calcWeight(int hit, Fc3dMasterRankPo last) {
            Double oldVal = Optional.ofNullable(last).map(v -> v.getCom5().getWeight()).orElse(0.0);
            return WeightCalculator.compute(oldVal, weightValue(hit));
        }

        @Override
        public void calcRank(Fc3dMasterRatePo rate, Fc3dMasterRankPo rank) {
            rank.calcC5Rank(rate);
        }

        @Override
        public RankValue rankValue(Fc3dMasterRankPo rank) {
            return rank.getCom5();
        }

        @Override
        public void assemble(NumberThreeCensusVo census, Fc3dLottoCensusPo po) {
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
            DAN1.calcHit(value, lottery);
        }

        @Override
        public Map<Integer, Integer> getWeightTable() {
            return COM5.getWeightTable();
        }

        @Override
        public double rateThrottle() {
            return 0.93;
        }

        @Override
        public ForecastValue forecastValue(Fc3dIcaiPo fc3dIcai) {
            return fc3dIcai.getCom6();
        }

        @Override
        public void toConvert(Fc3dIcaiPo data, ICaiForecast forecast) {
            data.setCom6(forecast.get(this.getNameZh()));
        }

        @Override
        public StatHitValue rateValue(Fc3dMasterRatePo rate) {
            return rate.getC6Hi();
        }

        @Override
        public void calcRate(List<Fc3dIcaiPo> data, Fc3dMasterRatePo rate) {
            rate.calcCom6(data);
        }

        @Override
        public void setWeight(Fc3dMasterRankPo rank, double weight) {
            rank.setCom6(new RankValue(weight));
        }

        @Override
        public Double calcWeight(int hit, Fc3dMasterRankPo last) {
            Double oldVal = Optional.ofNullable(last).map(v -> v.getCom6().getWeight()).orElse(0.0);
            return WeightCalculator.compute(oldVal, weightValue(hit));
        }

        @Override
        public void calcRank(Fc3dMasterRatePo rate, Fc3dMasterRankPo rank) {
            rank.calcC6Rank(rate);
        }

        @Override
        public RankValue rankValue(Fc3dMasterRankPo rank) {
            return rank.getCom6();
        }

        @Override
        public void assemble(NumberThreeCensusVo census, Fc3dLottoCensusPo po) {
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
            DAN1.calcHit(value, lottery);
        }

        @Override
        public Map<Integer, Integer> getWeightTable() {
            return COM6.getWeightTable();
        }

        @Override
        public double rateThrottle() {
            return 0.93;
        }

        @Override
        public ForecastValue forecastValue(Fc3dIcaiPo fc3dIcai) {
            return fc3dIcai.getCom7();
        }

        @Override
        public void toConvert(Fc3dIcaiPo data, ICaiForecast forecast) {
            data.setCom7(forecast.get(this.getNameZh()));
        }

        @Override
        public StatHitValue rateValue(Fc3dMasterRatePo rate) {
            return rate.getC7Hi();
        }

        @Override
        public void calcRate(List<Fc3dIcaiPo> data, Fc3dMasterRatePo rate) {
            rate.calcCom7(data);
        }

        @Override
        public void setWeight(Fc3dMasterRankPo rank, double weight) {
            rank.setCom7(new RankValue(weight));
        }

        @Override
        public Double calcWeight(int hit, Fc3dMasterRankPo last) {
            Double oldVal = Optional.ofNullable(last).map(v -> v.getCom7().getWeight()).orElse(0.0);
            return WeightCalculator.compute(oldVal, weightValue(hit));
        }

        @Override
        public void calcRank(Fc3dMasterRatePo rate, Fc3dMasterRankPo rank) {
            rank.calcC7Rank(rate);
        }

        @Override
        public RankValue rankValue(Fc3dMasterRankPo rank) {
            return rank.getCom7();
        }

        @Override
        public void assemble(NumberThreeCensusVo census, Fc3dLottoCensusPo po) {
            Optional.ofNullable(po).ifPresent(v -> census.setC7(v.getCensus()));
        }
    },
    KILL1("k1", "杀一") {

        final Map<Integer, Integer> weightTable = Maps.newHashMap();
        {
            weightTable.put(0, 0);
            weightTable.put(1, 3);
        }

        @Override
        public void calcHit(ForecastValue value, List<String> lottery) {
            int      hit   = 0;
            String[] balls = value.getData().split("\\s+");
            for (int i = 0; i < balls.length; i++) {
                String ball = balls[i].trim();
                if (!lottery.contains(ball)) {
                    hit++;
                    balls[i] = "[" + ball + "]";
                }
            }
            value.setDataHit(balls.length == hit ? 1 : 0);
            value.setHitData(String.join(" ", balls));
        }

        @Override
        public Map<Integer, Integer> getWeightTable() {
            return weightTable;
        }

        @Override
        public double rateThrottle() {
            return 0.93;
        }

        @Override
        public ForecastValue forecastValue(Fc3dIcaiPo fc3dIcai) {
            return fc3dIcai.getKill1();
        }

        @Override
        public void toConvert(Fc3dIcaiPo data, ICaiForecast forecast) {
            data.setKill1(forecast.get(this.getNameZh()));
        }

        @Override
        public StatHitValue rateValue(Fc3dMasterRatePo rate) {
            return rate.getK1Hi();
        }

        @Override
        public void calcRate(List<Fc3dIcaiPo> data, Fc3dMasterRatePo rate) {
            rate.calcKill1(data);
        }

        @Override
        public void setWeight(Fc3dMasterRankPo rank, double weight) {
            rank.setKill1(new RankValue(weight));
        }

        @Override
        public Double calcWeight(int hit, Fc3dMasterRankPo last) {
            Double oldVal = Optional.ofNullable(last).map(v -> v.getKill1().getWeight()).orElse(0.0);
            return WeightCalculator.compute(oldVal, weightValue(hit));
        }

        @Override
        public void calcRank(Fc3dMasterRatePo rate, Fc3dMasterRankPo rank) {
            rank.calcK1Rank(rate);
        }

        @Override
        public RankValue rankValue(Fc3dMasterRankPo rank) {
            return rank.getKill1();
        }

        @Override
        public void assemble(NumberThreeCensusVo census, Fc3dLottoCensusPo po) {
            Optional.ofNullable(po).ifPresent(v -> census.setK1(v.getCensus()));
        }
    },
    KILL2("k2", "杀二") {
        @Override
        public void calcHit(ForecastValue value, List<String> lottery) {
            KILL1.calcHit(value, lottery);
        }

        @Override
        public Map<Integer, Integer> getWeightTable() {
            return KILL1.getWeightTable();
        }

        @Override
        public double rateThrottle() {
            return 0.80;
        }

        @Override
        public ForecastValue forecastValue(Fc3dIcaiPo fc3dIcai) {
            return fc3dIcai.getKill2();
        }

        @Override
        public void toConvert(Fc3dIcaiPo data, ICaiForecast forecast) {
            data.setKill2(forecast.get(this.getNameZh()));
        }

        @Override
        public StatHitValue rateValue(Fc3dMasterRatePo rate) {
            return rate.getK2Me();
        }

        @Override
        public void calcRate(List<Fc3dIcaiPo> data, Fc3dMasterRatePo rate) {
            rate.calcKill2(data);
        }

        @Override
        public void setWeight(Fc3dMasterRankPo rank, double weight) {
            rank.setKill2(new RankValue(weight));
        }

        @Override
        public Double calcWeight(int hit, Fc3dMasterRankPo last) {
            Double oldVal = Optional.ofNullable(last).map(v -> v.getKill2().getWeight()).orElse(0.0);
            return WeightCalculator.compute(oldVal, weightValue(hit));
        }

        @Override
        public void calcRank(Fc3dMasterRatePo rate, Fc3dMasterRankPo rank) {
            rank.calcK2Rank(rate);
        }

        @Override
        public RankValue rankValue(Fc3dMasterRankPo rank) {
            return rank.getKill2();
        }

        @Override
        public void assemble(NumberThreeCensusVo census, Fc3dLottoCensusPo po) {
            Optional.ofNullable(po).ifPresent(v -> census.setK2(v.getCensus()));
        }
    },
    COMB3("cb3", "定位3*3*3") {

        final Map<Integer, Integer> weightTable = Maps.newHashMap();
        {
            weightTable.put(0, 0);
            weightTable.put(1, 3);
        }

        @Override
        public void calcHit(ForecastValue value, List<String> lottery) {
            int      hit      = 0;
            String[] segments = value.getData().split("\\*");
            for (int i = 0; i < segments.length; i++) {
                String segment = segments[i];
                String lot     = lottery.get(i);
                if (segment.contains(lot)) {
                    hit++;
                    segments[i] = segment.replaceAll(lot, "[" + lot + "]");
                }
            }
            value.setDataHit(hit == 3 ? 1 : 0);
            value.setHitData(String.join("*", segments));
        }

        @Override
        public Map<Integer, Integer> getWeightTable() {
            return weightTable;
        }

        @Override
        public ForecastValue forecastValue(Fc3dIcaiPo fc3dIcai) {
            return fc3dIcai.getComb3();
        }

        @Override
        public void toConvert(Fc3dIcaiPo data, ICaiForecast forecast) {
            data.setComb3(forecast.get(this.getNameZh()));
        }

        @Override
        public StatHitValue rateValue(Fc3dMasterRatePo rate) {
            return rate.getCb3Me();
        }

        @Override
        public void calcRate(List<Fc3dIcaiPo> data, Fc3dMasterRatePo rate) {
            rate.calcComb3(data);
        }

        @Override
        public Double calcWeight(int hit, Fc3dMasterRankPo last) {
            Double oldVal = Optional.ofNullable(last).map(v -> v.getComb3().getWeight()).orElse(0.0);
            return WeightCalculator.compute(oldVal, weightValue(hit));
        }

        @Override
        public void setWeight(Fc3dMasterRankPo rank, double weight) {
            rank.setComb3(new RankValue(weight));
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
        public void calcRank(Fc3dMasterRatePo rate, Fc3dMasterRankPo rank) {
            rank.calcCb3Rank(rate);
        }

        @Override
        public RankValue rankValue(Fc3dMasterRankPo rank) {
            return rank.getComb3();
        }
    },
    COMB4("cb4", "定位4*4*4") {
        @Override
        public void calcHit(ForecastValue value, List<String> lottery) {
            COMB3.calcHit(value, lottery);
        }

        @Override
        public Map<Integer, Integer> getWeightTable() {
            return COMB3.getWeightTable();
        }

        @Override
        public Double calcWeight(int hit, Fc3dMasterRankPo last) {
            Double oldVal = Optional.ofNullable(last).map(v -> v.getComb4().getWeight()).orElse(0.0);
            return WeightCalculator.compute(oldVal, weightValue(hit));
        }

        @Override
        public void setWeight(Fc3dMasterRankPo rank, double weight) {
            rank.setComb4(new RankValue(weight));
        }

        @Override
        public ChartValue chartValue() {
            return COMB3.chartValue();
        }

        @Override
        public ForecastValue forecastValue(Fc3dIcaiPo fc3dIcai) {
            return fc3dIcai.getComb4();
        }

        @Override
        public void toConvert(Fc3dIcaiPo data, ICaiForecast forecast) {
            data.setComb4(forecast.get(this.getNameZh()));
        }

        @Override
        public StatHitValue rateValue(Fc3dMasterRatePo rate) {
            return rate.getCb4Me();
        }

        @Override
        public void calcRate(List<Fc3dIcaiPo> data, Fc3dMasterRatePo rate) {
            rate.calcComb4(data);
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
        public void calcRank(Fc3dMasterRatePo rate, Fc3dMasterRankPo rank) {
            rank.calcCb4Rank(rate);
        }

        @Override
        public RankValue rankValue(Fc3dMasterRankPo rank) {
            return rank.getComb4();
        }
    },
    COMB5("cb5", "定位5*5*5") {
        @Override
        public void calcHit(ForecastValue value, List<String> lottery) {
            COMB3.calcHit(value, lottery);
        }

        @Override
        public Map<Integer, Integer> getWeightTable() {
            return COMB3.getWeightTable();
        }

        @Override
        public Double calcWeight(int hit, Fc3dMasterRankPo last) {
            Double oldVal = Optional.ofNullable(last).map(v -> v.getComb5().getWeight()).orElse(0.0);
            return WeightCalculator.compute(oldVal, weightValue(hit));
        }

        @Override
        public void setWeight(Fc3dMasterRankPo rank, double weight) {
            rank.setComb5(new RankValue(weight));
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
        public ForecastValue forecastValue(Fc3dIcaiPo fc3dIcai) {
            return fc3dIcai.getComb5();
        }

        @Override
        public void toConvert(Fc3dIcaiPo data, ICaiForecast forecast) {
            data.setComb5(forecast.get(this.getNameZh()));
        }

        @Override
        public StatHitValue rateValue(Fc3dMasterRatePo rate) {
            return rate.getCb5Me();
        }

        @Override
        public void calcRate(List<Fc3dIcaiPo> data, Fc3dMasterRatePo rate) {
            rate.calcComb5(data);
        }

        @Override
        public void calcRank(Fc3dMasterRatePo rate, Fc3dMasterRankPo rank) {
            rank.calcCb5Rank(rate);
        }

        @Override
        public RankValue rankValue(Fc3dMasterRankPo rank) {
            return rank.getComb5();
        }
    };

    private final String channel;
    private final String nameZh;

    Fc3dChannel(String channel, String nameZh) {
        this.channel = channel;
        this.nameZh  = nameZh;
    }

    public String getChannel() {
        return channel;
    }

    public String getNameZh() {
        return nameZh;
    }

    public static Fc3dChannel findOf(String channel) {
        return Arrays.stream(values())
                     .filter(v -> v.getChannel().equals(channel))
                     .findFirst()
                     .orElseThrow(() -> new RuntimeException("数据字段错误."));
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

    public int weightValue(int value) {
        return this.getWeightTable().get(value);
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

    abstract Map<Integer, Integer> getWeightTable();

    public abstract Double calcWeight(int hit, Fc3dMasterRankPo last);

    public abstract void setWeight(Fc3dMasterRankPo rank, double weight);

    public abstract ForecastValue forecastValue(Fc3dIcaiPo fc3dIcai);

    public abstract void calcRank(Fc3dMasterRatePo rate, Fc3dMasterRankPo rank);

    public abstract RankValue rankValue(Fc3dMasterRankPo rank);

    public abstract StatHitValue rateValue(Fc3dMasterRatePo rate);

    public abstract void toConvert(Fc3dIcaiPo data, ICaiForecast forecast);

    public abstract void calcRate(List<Fc3dIcaiPo> data, Fc3dMasterRatePo rate);

    public void assemble(NumberThreeCensusVo census, Fc3dLottoCensusPo po) {
    }

    public int qThrottle() {
        return 1;
    }
}
