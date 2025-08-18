package com.prize.lottery.enums;


import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import com.prize.lottery.po.kl8.Kl8IcaiInfoPo;
import com.prize.lottery.po.kl8.Kl8MasterRankPo;
import com.prize.lottery.po.kl8.Kl8MasterRatePo;
import com.prize.lottery.value.ForecastValue;
import com.prize.lottery.value.RankValue;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
@TypeHandler(type = Type.ENUM)
public enum Kl8Channel implements Value<String> {
    D1("d1", "d1", "选一") {
        @Override
        public void valueSet(Kl8IcaiInfoPo icaiInfo, ForecastValue value) {
            icaiInfo.setXd1(value);
        }

        @Override
        public ForecastValue forecastValue(Kl8IcaiInfoPo icaiInfo) {
            return icaiInfo.getXd1();
        }

        @Override
        public void calcHit(ForecastValue value, List<String> reds) {
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
        public void calcRate(List<Kl8IcaiInfoPo> data, Kl8MasterRatePo rate) {
            rate.calcD1(data);
        }

        @Override
        public void calcRank(Kl8MasterRatePo rate, Kl8MasterRankPo rank) {
            rank.calcD1Rank(rate);
        }

        @Override
        public RankValue rankValue(Kl8MasterRankPo rank) {
            return rank.getD1();
        }
    },
    D2("d2", "d2", "选二") {
        @Override
        public void valueSet(Kl8IcaiInfoPo icaiInfo, ForecastValue value) {
            icaiInfo.setXd2(value);
        }

        @Override
        public ForecastValue forecastValue(Kl8IcaiInfoPo icaiInfo) {
            return icaiInfo.getXd2();
        }

        @Override
        public void calcHit(ForecastValue value, List<String> reds) {
            D1.calcHit(value, reds);
        }

        @Override
        public void calcRate(List<Kl8IcaiInfoPo> data, Kl8MasterRatePo rate) {
            rate.calcD2(data);
        }

        @Override
        public void calcRank(Kl8MasterRatePo rate, Kl8MasterRankPo rank) {
            rank.calcD2Rank(rate);
        }

        @Override
        public RankValue rankValue(Kl8MasterRankPo rank) {
            return rank.getD2();
        }
    },
    D3("d3", "d3", "选三") {
        @Override
        public void valueSet(Kl8IcaiInfoPo icaiInfo, ForecastValue value) {
            icaiInfo.setXd3(value);
        }

        @Override
        public ForecastValue forecastValue(Kl8IcaiInfoPo icaiInfo) {
            return icaiInfo.getXd3();
        }

        @Override
        public void calcHit(ForecastValue value, List<String> reds) {
            D1.calcHit(value, reds);
        }

        @Override
        public void calcRate(List<Kl8IcaiInfoPo> data, Kl8MasterRatePo rate) {
            rate.calcD3(data);
        }

        @Override
        public void calcRank(Kl8MasterRatePo rate, Kl8MasterRankPo rank) {
            rank.calcD3Rank(rate);
        }

        @Override
        public RankValue rankValue(Kl8MasterRankPo rank) {
            return rank.getD3();
        }

    },
    D4("d4", "d4", "选四") {
        @Override
        public void valueSet(Kl8IcaiInfoPo icaiInfo, ForecastValue value) {
            icaiInfo.setXd4(value);
        }

        @Override
        public ForecastValue forecastValue(Kl8IcaiInfoPo icaiInfo) {
            return icaiInfo.getXd4();
        }

        @Override
        public void calcHit(ForecastValue value, List<String> reds) {
            D1.calcHit(value, reds);
        }

        @Override
        public void calcRate(List<Kl8IcaiInfoPo> data, Kl8MasterRatePo rate) {
            rate.calcD4(data);
        }

        @Override
        public void calcRank(Kl8MasterRatePo rate, Kl8MasterRankPo rank) {
            rank.calcD4Rank(rate);
        }

        @Override
        public RankValue rankValue(Kl8MasterRankPo rank) {
            return rank.getD4();
        }
    },
    D5("d5", "d5", "选五") {
        @Override
        public void valueSet(Kl8IcaiInfoPo icaiInfo, ForecastValue value) {
            icaiInfo.setXd5(value);
        }

        @Override
        public ForecastValue forecastValue(Kl8IcaiInfoPo icaiInfo) {
            return icaiInfo.getXd5();
        }

        @Override
        public void calcHit(ForecastValue value, List<String> reds) {
            D1.calcHit(value, reds);
        }

        @Override
        public void calcRate(List<Kl8IcaiInfoPo> data, Kl8MasterRatePo rate) {
            rate.calcD5(data);
        }

        @Override
        public void calcRank(Kl8MasterRatePo rate, Kl8MasterRankPo rank) {
            rank.calcD5Rank(rate);
        }

        @Override
        public RankValue rankValue(Kl8MasterRankPo rank) {
            return rank.getD5();
        }

    },
    D6("d6", "d6", "选六") {
        @Override
        public void valueSet(Kl8IcaiInfoPo icaiInfo, ForecastValue value) {
            icaiInfo.setXd6(value);
        }

        @Override
        public ForecastValue forecastValue(Kl8IcaiInfoPo icaiInfo) {
            return icaiInfo.getXd6();
        }

        @Override
        public void calcHit(ForecastValue value, List<String> reds) {
            D1.calcHit(value, reds);
        }

        @Override
        public void calcRate(List<Kl8IcaiInfoPo> data, Kl8MasterRatePo rate) {
            rate.calcD6(data);
        }

        @Override
        public void calcRank(Kl8MasterRatePo rate, Kl8MasterRankPo rank) {
            rank.calcD6Rank(rate);
        }

        @Override
        public RankValue rankValue(Kl8MasterRankPo rank) {
            return rank.getD6();
        }
    },
    D7("d7", "d7", "选七") {
        @Override
        public void valueSet(Kl8IcaiInfoPo icaiInfo, ForecastValue value) {
            icaiInfo.setXd7(value);
        }

        @Override
        public ForecastValue forecastValue(Kl8IcaiInfoPo icaiInfo) {
            return icaiInfo.getXd7();
        }

        @Override
        public void calcHit(ForecastValue value, List<String> reds) {
            D1.calcHit(value, reds);
        }

        @Override
        public void calcRate(List<Kl8IcaiInfoPo> data, Kl8MasterRatePo rate) {
            rate.calcD7(data);
        }

        @Override
        public void calcRank(Kl8MasterRatePo rate, Kl8MasterRankPo rank) {
            rank.calcD7Rank(rate);
        }

        @Override
        public RankValue rankValue(Kl8MasterRankPo rank) {
            return rank.getD7();
        }

    },
    D8("d8", "d8", "选八") {
        @Override
        public void valueSet(Kl8IcaiInfoPo icaiInfo, ForecastValue value) {
            icaiInfo.setXd8(value);
        }

        @Override
        public ForecastValue forecastValue(Kl8IcaiInfoPo icaiInfo) {
            return icaiInfo.getXd8();
        }

        @Override
        public void calcHit(ForecastValue value, List<String> reds) {
            D1.calcHit(value, reds);
        }

        @Override
        public void calcRate(List<Kl8IcaiInfoPo> data, Kl8MasterRatePo rate) {
            rate.calcD8(data);
        }

        @Override
        public void calcRank(Kl8MasterRatePo rate, Kl8MasterRankPo rank) {
            rank.calcD8Rank(rate);
        }

        @Override
        public RankValue rankValue(Kl8MasterRankPo rank) {
            return rank.getD8();
        }

    },
    D9("d9", "d9", "选九") {
        @Override
        public void valueSet(Kl8IcaiInfoPo icaiInfo, ForecastValue value) {
            icaiInfo.setXd9(value);
        }

        @Override
        public ForecastValue forecastValue(Kl8IcaiInfoPo icaiInfo) {
            return icaiInfo.getXd9();
        }

        @Override
        public void calcHit(ForecastValue value, List<String> reds) {
            D1.calcHit(value, reds);
        }

        @Override
        public void calcRate(List<Kl8IcaiInfoPo> data, Kl8MasterRatePo rate) {
            rate.calcD9(data);
        }

        @Override
        public void calcRank(Kl8MasterRatePo rate, Kl8MasterRankPo rank) {
            rank.calcD9Rank(rate);
        }

        @Override
        public RankValue rankValue(Kl8MasterRankPo rank) {
            return rank.getD9();
        }
    },
    D10("d10", "d10", "选十") {
        @Override
        public void valueSet(Kl8IcaiInfoPo icaiInfo, ForecastValue value) {
            icaiInfo.setXd10(value);
        }

        @Override
        public ForecastValue forecastValue(Kl8IcaiInfoPo icaiInfo) {
            return icaiInfo.getXd10();
        }

        @Override
        public void calcHit(ForecastValue value, List<String> reds) {
            D1.calcHit(value, reds);
        }

        @Override
        public void calcRate(List<Kl8IcaiInfoPo> data, Kl8MasterRatePo rate) {
            rate.calcD10(data);
        }

        @Override
        public void calcRank(Kl8MasterRatePo rate, Kl8MasterRankPo rank) {
            rank.calcD10Rank(rate);
        }

        @Override
        public RankValue rankValue(Kl8MasterRankPo rank) {
            return rank.getD10();
        }
    },
    D11("d11", "d11", "11码") {
        @Override
        public void valueSet(Kl8IcaiInfoPo icaiInfo, ForecastValue value) {
            icaiInfo.setXd11(value);
        }

        @Override
        public ForecastValue forecastValue(Kl8IcaiInfoPo icaiInfo) {
            return icaiInfo.getXd11();
        }

        @Override
        public void calcHit(ForecastValue value, List<String> reds) {
            D1.calcHit(value, reds);
        }

        @Override
        public void calcRate(List<Kl8IcaiInfoPo> data, Kl8MasterRatePo rate) {
            rate.calcD11(data);
        }

        @Override
        public void calcRank(Kl8MasterRatePo rate, Kl8MasterRankPo rank) {
            rank.calcD11Rank(rate);
        }

        @Override
        public RankValue rankValue(Kl8MasterRankPo rank) {
            return rank.getD11();
        }
    },
    D12("d12", "d12", "12码") {
        @Override
        public void valueSet(Kl8IcaiInfoPo icaiInfo, ForecastValue value) {
            icaiInfo.setXd12(value);
        }

        @Override
        public ForecastValue forecastValue(Kl8IcaiInfoPo icaiInfo) {
            return icaiInfo.getXd12();
        }

        @Override
        public void calcHit(ForecastValue value, List<String> reds) {
            D1.calcHit(value, reds);
        }

        @Override
        public void calcRate(List<Kl8IcaiInfoPo> data, Kl8MasterRatePo rate) {
            rate.calcD12(data);
        }

        @Override
        public void calcRank(Kl8MasterRatePo rate, Kl8MasterRankPo rank) {
            rank.calcD12Rank(rate);
        }

        @Override
        public RankValue rankValue(Kl8MasterRankPo rank) {
            return rank.getD12();
        }
    },
    D13("d13", "d13", "13码") {
        @Override
        public void valueSet(Kl8IcaiInfoPo icaiInfo, ForecastValue value) {
            icaiInfo.setXd13(value);
        }

        @Override
        public ForecastValue forecastValue(Kl8IcaiInfoPo icaiInfo) {
            return icaiInfo.getXd13();
        }

        @Override
        public void calcHit(ForecastValue value, List<String> reds) {
            D1.calcHit(value, reds);
        }

        @Override
        public void calcRate(List<Kl8IcaiInfoPo> data, Kl8MasterRatePo rate) {
            rate.calcD13(data);
        }

        @Override
        public void calcRank(Kl8MasterRatePo rate, Kl8MasterRankPo rank) {
            rank.calcD13Rank(rate);
        }

        @Override
        public RankValue rankValue(Kl8MasterRankPo rank) {
            return rank.getD13();
        }
    },
    D14("d14", "d14", "14码") {
        @Override
        public void valueSet(Kl8IcaiInfoPo icaiInfo, ForecastValue value) {
            icaiInfo.setXd14(value);
        }

        @Override
        public ForecastValue forecastValue(Kl8IcaiInfoPo icaiInfo) {
            return icaiInfo.getXd14();
        }

        @Override
        public void calcHit(ForecastValue value, List<String> reds) {
            D1.calcHit(value, reds);
        }

        @Override
        public void calcRate(List<Kl8IcaiInfoPo> data, Kl8MasterRatePo rate) {
            rate.calcD14(data);
        }

        @Override
        public void calcRank(Kl8MasterRatePo rate, Kl8MasterRankPo rank) {
            rank.calcD14Rank(rate);
        }

        @Override
        public RankValue rankValue(Kl8MasterRankPo rank) {
            return rank.getD14();
        }
    },
    D15("d15", "d15", "15码") {
        @Override
        public void valueSet(Kl8IcaiInfoPo icaiInfo, ForecastValue value) {
            icaiInfo.setXd15(value);
        }

        @Override
        public ForecastValue forecastValue(Kl8IcaiInfoPo icaiInfo) {
            return icaiInfo.getXd15();
        }

        @Override
        public void calcHit(ForecastValue value, List<String> reds) {
            D1.calcHit(value, reds);
        }

        @Override
        public void calcRate(List<Kl8IcaiInfoPo> data, Kl8MasterRatePo rate) {
            rate.calcD15(data);
        }

        @Override
        public void calcRank(Kl8MasterRatePo rate, Kl8MasterRankPo rank) {
            rank.calcD15Rank(rate);
        }

        @Override
        public RankValue rankValue(Kl8MasterRankPo rank) {
            return rank.getD15();
        }
    },
    D20("d20", "d20", "20码") {
        @Override
        public void valueSet(Kl8IcaiInfoPo icaiInfo, ForecastValue value) {
            icaiInfo.setXd20(value);
        }

        @Override
        public ForecastValue forecastValue(Kl8IcaiInfoPo icaiInfo) {
            return icaiInfo.getXd20();
        }

        @Override
        public void calcHit(ForecastValue value, List<String> reds) {
            D1.calcHit(value, reds);
        }

        @Override
        public void calcRate(List<Kl8IcaiInfoPo> data, Kl8MasterRatePo rate) {
            rate.calcD20(data);
        }

        @Override
        public void calcRank(Kl8MasterRatePo rate, Kl8MasterRankPo rank) {
            rank.calcD20Rank(rate);
        }

        @Override
        public RankValue rankValue(Kl8MasterRankPo rank) {
            return rank.getD20();
        }
    },
    K1("k1", "s1", "杀1") {
        @Override
        public void valueSet(Kl8IcaiInfoPo icaiInfo, ForecastValue value) {
            icaiInfo.setSk1(value);
        }

        @Override
        public ForecastValue forecastValue(Kl8IcaiInfoPo icaiInfo) {
            return icaiInfo.getSk1();
        }

        @Override
        public void calcHit(ForecastValue value, List<String> reds) {
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
        public void calcRate(List<Kl8IcaiInfoPo> data, Kl8MasterRatePo rate) {
            rate.calcK1(data);
        }

        @Override
        public void calcRank(Kl8MasterRatePo rate, Kl8MasterRankPo rank) {
            rank.calcK1Rank(rate);
        }

        @Override
        public RankValue rankValue(Kl8MasterRankPo rank) {
            return rank.getK1();
        }
    },
    K2("k2", "s2", "杀2") {
        @Override
        public void valueSet(Kl8IcaiInfoPo icaiInfo, ForecastValue value) {
            icaiInfo.setSk2(value);
        }

        @Override
        public ForecastValue forecastValue(Kl8IcaiInfoPo icaiInfo) {
            return icaiInfo.getSk2();
        }

        @Override
        public void calcHit(ForecastValue value, List<String> reds) {
            K1.calcHit(value, reds);
        }

        @Override
        public void calcRate(List<Kl8IcaiInfoPo> data, Kl8MasterRatePo rate) {
            rate.calcK2(data);
        }

        @Override
        public void calcRank(Kl8MasterRatePo rate, Kl8MasterRankPo rank) {
            rank.calcK2Rank(rate);
        }

        @Override
        public RankValue rankValue(Kl8MasterRankPo rank) {
            return rank.getK2();
        }
    },
    K3("k3", "s3", "杀3") {
        @Override
        public void valueSet(Kl8IcaiInfoPo icaiInfo, ForecastValue value) {
            icaiInfo.setSk3(value);
        }

        @Override
        public ForecastValue forecastValue(Kl8IcaiInfoPo icaiInfo) {
            return icaiInfo.getSk3();
        }

        @Override
        public void calcHit(ForecastValue value, List<String> reds) {
            K1.calcHit(value, reds);
        }

        @Override
        public void calcRate(List<Kl8IcaiInfoPo> data, Kl8MasterRatePo rate) {
            rate.calcK3(data);
        }

        @Override
        public void calcRank(Kl8MasterRatePo rate, Kl8MasterRankPo rank) {
            rank.calcK3Rank(rate);
        }

        @Override
        public RankValue rankValue(Kl8MasterRankPo rank) {
            return rank.getK3();
        }
    },
    K4("k4", "s4", "杀4") {
        @Override
        public void valueSet(Kl8IcaiInfoPo icaiInfo, ForecastValue value) {
            icaiInfo.setSk4(value);
        }

        @Override
        public ForecastValue forecastValue(Kl8IcaiInfoPo icaiInfo) {
            return icaiInfo.getSk4();
        }

        @Override
        public void calcHit(ForecastValue value, List<String> reds) {
            K1.calcHit(value, reds);
        }

        @Override
        public void calcRate(List<Kl8IcaiInfoPo> data, Kl8MasterRatePo rate) {
            rate.calcK4(data);
        }

        @Override
        public void calcRank(Kl8MasterRatePo rate, Kl8MasterRankPo rank) {
            rank.calcK4Rank(rate);
        }

        @Override
        public RankValue rankValue(Kl8MasterRankPo rank) {
            return rank.getK4();
        }
    },
    K5("k5", "s5", "杀5") {
        @Override
        public void valueSet(Kl8IcaiInfoPo icaiInfo, ForecastValue value) {
            icaiInfo.setSk5(value);
        }

        @Override
        public ForecastValue forecastValue(Kl8IcaiInfoPo icaiInfo) {
            return icaiInfo.getSk5();
        }

        @Override
        public void calcHit(ForecastValue value, List<String> reds) {
            K1.calcHit(value, reds);
        }

        @Override
        public void calcRate(List<Kl8IcaiInfoPo> data, Kl8MasterRatePo rate) {
            rate.calcK5(data);
        }

        @Override
        public void calcRank(Kl8MasterRatePo rate, Kl8MasterRankPo rank) {
            rank.calcK5Rank(rate);
        }

        @Override
        public RankValue rankValue(Kl8MasterRankPo rank) {
            return rank.getK5();
        }
    };

    private final String channel;
    private final String alias;
    private final String nameZh;

    Kl8Channel(String channel, String alias, String nameZh) {
        this.channel = channel;
        this.alias   = alias;
        this.nameZh  = nameZh;
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


    private boolean isCensus() {
        return true;
    }

    public abstract ForecastValue forecastValue(Kl8IcaiInfoPo icaiInfo);

    public abstract void calcHit(ForecastValue value, List<String> reds);

    public abstract void calcRate(List<Kl8IcaiInfoPo> data, Kl8MasterRatePo rate);

    public abstract void calcRank(Kl8MasterRatePo rate, Kl8MasterRankPo rank);

    public abstract void valueSet(Kl8IcaiInfoPo icaiInfo, ForecastValue value);

    public abstract RankValue rankValue(Kl8MasterRankPo rank);

    public static Kl8Channel ofChannel(String channel) {
        return Arrays.stream(values())
                     .filter(v -> v.channel.equals(channel))
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException("快乐8渠道错误."));
    }

    public static Optional<Kl8Channel> ofAlias(String alias) {
        return Arrays.stream(values()).filter(v -> v.alias.equals(alias)).findFirst();
    }

}
