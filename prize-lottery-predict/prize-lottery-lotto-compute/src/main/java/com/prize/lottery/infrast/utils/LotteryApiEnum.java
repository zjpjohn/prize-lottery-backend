package com.prize.lottery.infrast.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.cloud.arch.http.HttpRequest;
import com.prize.lottery.domain.lottery.model.LotteryInfoDo;
import com.prize.lottery.domain.lottery.value.LevelValue;
import com.prize.lottery.enums.LotteryEnum;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Getter
public enum LotteryApiEnum {
    FC3D_API(LotteryEnum.FC3D) {
        @Override
        public List<LotteryInfoDo> fetch(int size) {
            try {
                String    apiUri   = String.format(ICaiCloudConstants.FC3D_LOTTERY_API, size);
                String    response = HttpRequest.instance().get(apiUri);
                JSONArray result   = checkFcResult(response);
                return result.stream().map(e -> (JSONObject) e).map(this::parseFc).collect(Collectors.toList());
            } catch (Exception e) {
                throw new RuntimeException("抓取福彩3D开奖数据异常.", e);
            }
        }
    },
    SSQ_API(LotteryEnum.SSQ) {
        @Override
        protected boolean isTotalAmount() {
            return false;
        }

        @Override
        public List<LotteryInfoDo> fetch(int size) {
            try {
                String    apiUri   = String.format(ICaiCloudConstants.SSQ_LOTTERY_API, size);
                String    response = HttpRequest.instance().get(apiUri);
                JSONArray result   = checkFcResult(response);
                return result.stream().map(e -> (JSONObject) e).map(this::parseFc).collect(Collectors.toList());
            } catch (Exception e) {
                throw new RuntimeException("抓取双色球开奖数据异常.", e);
            }
        }

    },
    QLC_API(LotteryEnum.QLC) {
        @Override
        protected boolean isTotalAmount() {
            return false;
        }

        @Override
        public List<LotteryInfoDo> fetch(int size) {
            try {
                String    apiUri   = String.format(ICaiCloudConstants.QLC_LOTTERY_API, size);
                String    response = HttpRequest.instance().get(apiUri);
                JSONArray result   = checkFcResult(response);
                return result.stream().map(e -> (JSONObject) e).map(this::parseFc).collect(Collectors.toList());
            } catch (Exception e) {
                throw new RuntimeException("抓取七乐彩开奖数据异常.", e);
            }
        }
    },
    DLT_API(LotteryEnum.DLT) {
        @Override
        public List<LotteryInfoDo> fetch(int size) {
            try {
                String    apiUri   = String.format(ICaiCloudConstants.DLT_LOTTERY_API, size);
                String    response = HttpRequest.instance().get(apiUri);
                JSONArray result   = checkTcResult(response);
                return result.stream().map(e -> (JSONObject) e).map(this::parseTc).collect(Collectors.toList());
            } catch (Exception e) {
                throw new RuntimeException("抓取大乐透开奖数据异常.", e);
            }
        }
    },
    PL3_API(LotteryEnum.PL3) {
        @Override
        public List<LotteryInfoDo> fetch(int size) {
            try {
                String    apiUri   = String.format(ICaiCloudConstants.PL3_LOTTERY_API, size);
                String    response = HttpRequest.instance().get(apiUri);
                JSONArray result   = checkTcResult(response);
                return result.stream().map(e -> (JSONObject) e).map(this::parseTc).collect(Collectors.toList());
            } catch (Exception e) {
                throw new RuntimeException("抓取排列三开奖数据异常.", e);
            }
        }

        @Override
        public List<LotteryInfoDo> fetch(String start, String end) {
            try {
                start = start.substring(2);
                end   = end.substring(2);
                String    apiUri   = String.format(ICaiCloudConstants.PL3_LOTTERY_RANGE_API, start, end);
                String    response = HttpRequest.instance().get(apiUri);
                JSONArray result   = checkTcResult(response);
                return result.stream().map(e -> (JSONObject) e).map(this::parseTc).collect(Collectors.toList());
            } catch (Exception e) {
                throw new RuntimeException("抓取排列三开奖数据异常.", e);
            }

        }
    },
    KL8_API(LotteryEnum.KL8) {
        @Override
        protected boolean isTotalAmount() {
            return false;
        }

        @Override
        public List<LotteryInfoDo> fetch(int size) {
            try {
                String    apiUri   = String.format(ICaiCloudConstants.KL8_LOTTERY_API, size);
                String    response = HttpRequest.instance().get(apiUri);
                JSONArray result   = checkFcResult(response);
                return result.stream().map(e -> (JSONObject) e).map(this::parseKl8).collect(Collectors.toList());
            } catch (Exception e) {
                throw new RuntimeException("抓取快乐8开奖数据异常.", e);
            }
        }
    };

    private final LotteryEnum lottery;

    LotteryApiEnum(LotteryEnum lottery) {
        this.lottery = lottery;
    }

    public abstract List<LotteryInfoDo> fetch(int size);

    public List<LotteryInfoDo> fetch(String start, String end) {
        return Collections.emptyList();
    }

    protected boolean isTotalAmount() {
        return true;
    }

    protected JSONArray checkFcResult(String response) {
        JSONObject root = JSON.parseObject(response);
        if (root.getIntValue("state") == 1) {
            throw new IllegalArgumentException(root.getString("message"));
        }
        return Optional.ofNullable(root.getJSONArray("result")).orElseGet(JSONArray::new);
    }

    protected JSONArray checkTcResult(String response) {
        JSONObject root = JSON.parseObject(response);
        if (!root.getBoolean("success")) {
            throw new IllegalArgumentException(root.getString("errorMessage"));
        }
        JSONObject value = root.getJSONObject("value");
        if (value == null) {
            throw new IllegalArgumentException("开奖数据格式错误.");
        }
        return Optional.ofNullable(value.getJSONArray("list")).orElseGet(JSONArray::new);
    }

    protected LotteryInfoDo parseKl8(JSONObject node) {
        LotteryInfoDo lottery = new LotteryInfoDo();
        lottery.setLottery(this.getLottery());
        lottery.setRed(node.getString("red"));
        lottery.setPeriod(node.getString("code"));
        lottery.setSales(node.getString("sales"));
        lottery.setLotDate(node.getString("date"));
        lottery.setPool(node.getString("poolmoney"));
        JSONArray jsonArray = Optional.ofNullable(node.getJSONArray("prizegrades")).orElseGet(JSONArray::new);
        List<LevelValue> values = jsonArray.stream().map(e -> (JSONObject) e).filter(v -> {
            String typenum = v.getString("typenum");
            return !("0".equals(typenum) || "_".equals(typenum) || StringUtils.isBlank(typenum));
        }).map(v -> {
            String  type      = v.getString("type").replace("x", "").replace("z", "");
            int     level     = Integer.parseInt(type);
            Integer quantity  = Integer.valueOf(v.getString("typenum"));
            Double  typeMoney = Double.valueOf(v.getString("typemoney"));
            if (isTotalAmount()) {
                return new LevelValue(level, quantity, typeMoney);
            }
            return new LevelValue(level, 0, quantity, typeMoney, quantity * typeMoney);
        }).collect(Collectors.toList());
        lottery.setLevels(values);
        return lottery;
    }

    protected LotteryInfoDo parseFc(JSONObject node) {
        LotteryInfoDo lottery = new LotteryInfoDo();
        lottery.setLottery(this.getLottery());
        lottery.setRed(node.getString("red"));
        lottery.setBlue(node.getString("blue"));
        lottery.setPeriod(node.getString("code"));
        lottery.setSales(node.getString("sales"));
        lottery.setLotDate(node.getString("date"));
        lottery.setPool(node.getString("poolmoney"));
        JSONArray grades = Optional.ofNullable(node.getJSONArray("prizegrades")).orElseGet(JSONArray::new);
        List<LevelValue> values = grades.stream().map(e -> (JSONObject) e).filter(v -> {
            String typenum = v.getString("typenum");
            return !("0".equals(typenum) || "_".equals(typenum) || StringUtils.isBlank(typenum));
        }).map(v -> {
            int     level     = v.getIntValue("type");
            Integer quantity  = Integer.valueOf(v.getString("typenum"));
            Double  typeMoney = Double.valueOf(v.getString("typemoney"));
            if (isTotalAmount()) {
                return new LevelValue(level, quantity, typeMoney);
            }
            return new LevelValue(level, 0, quantity, typeMoney, quantity * typeMoney);
        }).collect(Collectors.toList());
        lottery.setLevels(values);
        return lottery;
    }

    protected LotteryInfoDo parseTc(JSONObject node) {
        LotteryInfoDo lottery = new LotteryInfoDo();
        lottery.setLottery(this.getLottery());
        lottery.setPeriod("20" + node.getString("lotteryDrawNum"));
        lottery.setLotDate(node.getString("lotteryDrawTime"));
        String   result = node.getString("lotteryDrawResult");
        String[] split  = result.split("\\s+");
        if (split.length > 5) {
            lottery.setRed(String.join(" ", split[0], split[1], split[2], split[3], split[4]));
            lottery.setBlue(String.join(" ", split[5], split[6]));
        } else {
            lottery.setRed(String.join(" ", split));
        }
        lottery.setSales(node.getString("totalSaleAmount"));
        lottery.setPool(node.getString("poolBalanceAfterdraw"));
        JSONArray levelList = Optional.ofNullable(node.getJSONArray("prizeLevelList")).orElseGet(JSONArray::new);
        List<LevelValue> values = levelList.stream().map(e -> (JSONObject) e).filter(v -> {
            String stakeCount = v.getString("stakeCount");
            return StringUtils.isNotBlank(stakeCount) && !"0".equals(stakeCount);
        }).map(v -> {
            //中奖等级
            int level;
            if (this.getLottery() == LotteryEnum.PL3) {
                level = Pl3Level.findOf(v.getString("prizeLevel")).group;
            } else if (this.getLottery() == LotteryEnum.PL5) {
                level = v.getIntValue("awardType");
            } else {
                level = Integer.parseInt(v.getString("group"));
            }
            //单注奖金
            double bonus = Double.parseDouble(v.getString("stakeAmount").replace(",", ""));
            //中奖数量
            Integer quantity         = Integer.valueOf(v.getString("stakeCount").replace(",", ""));
            String  totalPrizeamount = v.getString("totalPrizeamount").replace(",", "");
            int     index            = totalPrizeamount.length();
            if (totalPrizeamount.contains(".")) {
                index = totalPrizeamount.indexOf(".");
            }
            //中奖总金额
            double prizeamount = Double.parseDouble(totalPrizeamount.substring(0, index));
            return new LevelValue(level, 0, quantity, bonus, prizeamount);

        }).collect(Collectors.toList());
        lottery.setLevels(values);
        return lottery;
    }

    public static LotteryApiEnum valueOf(LotteryEnum lottery) {
        return Arrays.stream(values())
                     .filter(v -> v.lottery == lottery)
                     .findFirst()
                     .orElseThrow(() -> new RuntimeException("彩票类型不存在"));
    }

    @Getter
    public enum Pl3Level {
        zhi_xuan("直选", 10),
        zu3_xuan("组选3", 20),
        zu6_xuan("组选6", 30),
        ;
        private final String  name;
        private final Integer group;

        Pl3Level(String name, Integer group) {
            this.name  = name;
            this.group = group;
        }

        public static Pl3Level findOf(String name) {
            return Arrays.stream(values()).filter(v -> name.equals(v.getName())).findFirst().orElse(null);
        }
    }
}
