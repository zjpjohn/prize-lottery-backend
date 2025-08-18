package com.prize.lottery.infrast.spider.open;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Maps;
import com.prize.lottery.enums.*;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.infrast.utils.ICaiCloudConstants;
import com.prize.lottery.infrast.utils.OpenApiSignature;
import com.prize.lottery.value.ForecastValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum OpenForecastApi {
    FC3D(LotteryEnum.FC3D) {
        @Override
        public String fetchApi(String appKey, String secret, Map<String, String> params) {
            return ICaiCloudConstants.FC3D_OPEN_API + OpenApiSignature.signature(appKey, secret, params);
        }

        @Override
        public Map<String, ForecastValue> parse(JSONObject data) {
            Map<String, ForecastValue> result = Maps.newHashMap();
            result.put(Fc3dChannel.DAN1.getNameZh(), new ForecastValue(data.getString("dan1")));
            result.put(Fc3dChannel.DAN2.getNameZh(), new ForecastValue(data.getString("dan2")));
            result.put(Fc3dChannel.DAN3.getNameZh(), new ForecastValue(data.getString("dan3")));
            result.put(Fc3dChannel.COM5.getNameZh(), new ForecastValue(data.getString("com5")));
            result.put(Fc3dChannel.COM6.getNameZh(), new ForecastValue(data.getString("com6")));
            result.put(Fc3dChannel.COM7.getNameZh(), new ForecastValue(data.getString("com7")));
            result.put(Fc3dChannel.KILL1.getNameZh(), new ForecastValue(data.getString("kill1")));
            result.put(Fc3dChannel.KILL2.getNameZh(), new ForecastValue(data.getString("kill2")));
            result.put(Fc3dChannel.COMB3.getNameZh(), new ForecastValue(data.getString("comb3")));
            result.put(Fc3dChannel.COMB4.getNameZh(), new ForecastValue(data.getString("comb4")));
            result.put(Fc3dChannel.COMB5.getNameZh(), new ForecastValue(data.getString("comb5")));
            return result;
        }
    },
    PL3(LotteryEnum.PL3) {
        @Override
        public String fetchApi(String appKey, String secret, Map<String, String> params) {
            return ICaiCloudConstants.PL3_OPEN_API + OpenApiSignature.signature(appKey, secret, params);
        }

        @Override
        public Map<String, ForecastValue> parse(JSONObject data) {
            Map<String, ForecastValue> result = Maps.newHashMap();
            result.put(Pl3Channel.DAN1.getNameZh(), new ForecastValue(data.getString("dan1")));
            result.put(Pl3Channel.DAN2.getNameZh(), new ForecastValue(data.getString("dan2")));
            result.put(Pl3Channel.DAN3.getNameZh(), new ForecastValue(data.getString("dan3")));
            result.put(Pl3Channel.COM5.getNameZh(), new ForecastValue(data.getString("com5")));
            result.put(Pl3Channel.COM6.getNameZh(), new ForecastValue(data.getString("com6")));
            result.put(Pl3Channel.COM7.getNameZh(), new ForecastValue(data.getString("com7")));
            result.put(Pl3Channel.KILL1.getNameZh(), new ForecastValue(data.getString("kill1")));
            result.put(Pl3Channel.KILL2.getNameZh(), new ForecastValue(data.getString("kill2")));
            result.put(Pl3Channel.COMB3.getNameZh(), new ForecastValue(data.getString("comb3")));
            result.put(Pl3Channel.COMB4.getNameZh(), new ForecastValue(data.getString("comb4")));
            result.put(Pl3Channel.COMB5.getNameZh(), new ForecastValue(data.getString("comb5")));
            return result;
        }
    },
    SSQ(LotteryEnum.SSQ) {
        @Override
        public String fetchApi(String appKey, String secret, Map<String, String> params) {
            return ICaiCloudConstants.SSQ_OPEN_API + OpenApiSignature.signature(appKey, secret, params);
        }

        @Override
        public Map<String, ForecastValue> parse(JSONObject data) {
            Map<String, ForecastValue> result = Maps.newHashMap();
            result.put(SsqChannel.RED1.getNameZh(), new ForecastValue(data.getString("red1")));
            result.put(SsqChannel.RED2.getNameZh(), new ForecastValue(data.getString("red2")));
            result.put(SsqChannel.RED3.getNameZh(), new ForecastValue(data.getString("red3")));
            result.put(SsqChannel.RED12.getNameZh(), new ForecastValue(data.getString("red12")));
            result.put(SsqChannel.RED20.getNameZh(), new ForecastValue(data.getString("red20")));
            result.put(SsqChannel.RED25.getNameZh(), new ForecastValue(data.getString("red25")));
            result.put(SsqChannel.RK3.getNameZh(), new ForecastValue(data.getString("redKill3")));
            result.put(SsqChannel.RK6.getNameZh(), new ForecastValue(data.getString("redKill6")));
            result.put(SsqChannel.BLUE3.getNameZh(), new ForecastValue(data.getString("blue3")));
            result.put(SsqChannel.BLUE5.getNameZh(), new ForecastValue(data.getString("blue5")));
            result.put(SsqChannel.BK.getNameZh(), new ForecastValue(data.getString("blueKill")));
            return result;
        }
    },
    DLT(LotteryEnum.DLT) {
        @Override
        public String fetchApi(String appKey, String secret, Map<String, String> params) {
            return ICaiCloudConstants.DLT_OPEN_API + OpenApiSignature.signature(appKey, secret, params);
        }

        @Override
        public Map<String, ForecastValue> parse(JSONObject data) {
            Map<String, ForecastValue> result = Maps.newHashMap();
            result.put(DltChannel.RED1.getNameZh(), new ForecastValue(data.getString("red1")));
            result.put(DltChannel.RED2.getNameZh(), new ForecastValue(data.getString("red2")));
            result.put(DltChannel.RED3.getNameZh(), new ForecastValue(data.getString("red3")));
            result.put(DltChannel.RED10.getNameZh(), new ForecastValue(data.getString("red10")));
            result.put(DltChannel.RED20.getNameZh(), new ForecastValue(data.getString("red20")));
            result.put(DltChannel.RK3.getNameZh(), new ForecastValue(data.getString("redKill3")));
            result.put(DltChannel.RK6.getNameZh(), new ForecastValue(data.getString("redKill6")));
            result.put(DltChannel.BLUE1.getNameZh(), new ForecastValue(data.getString("blue1")));
            result.put(DltChannel.BLUE2.getNameZh(), new ForecastValue(data.getString("blue2")));
            result.put(DltChannel.BLUE6.getNameZh(), new ForecastValue(data.getString("blue6")));
            result.put(DltChannel.BK.getNameZh(), new ForecastValue(data.getString("blueKill3")));
            return result;
        }
    },
    QLC(LotteryEnum.QLC) {
        @Override
        public String fetchApi(String appKey, String secret, Map<String, String> params) {
            return ICaiCloudConstants.QLC_OPEN_API + OpenApiSignature.signature(appKey, secret, params);
        }

        @Override
        public Map<String, ForecastValue> parse(JSONObject data) {
            Map<String, ForecastValue> result = Maps.newHashMap();
            result.put(QlcChannel.RED1.getNameZh(), new ForecastValue(data.getString("red1")));
            result.put(QlcChannel.RED2.getNameZh(), new ForecastValue(data.getString("red2")));
            result.put(QlcChannel.RED3.getNameZh(), new ForecastValue(data.getString("red3")));
            result.put(QlcChannel.RED12.getNameZh(), new ForecastValue(data.getString("red12")));
            result.put(QlcChannel.RED18.getNameZh(), new ForecastValue(data.getString("red18")));
            result.put(QlcChannel.RED22.getNameZh(), new ForecastValue(data.getString("red22")));
            result.put(QlcChannel.RK3.getNameZh(), new ForecastValue(data.getString("kill3")));
            result.put(QlcChannel.RK6.getNameZh(), new ForecastValue(data.getString("kill6")));
            return result;
        }
    };

    private final LotteryEnum type;

    public abstract String fetchApi(String appKey, String secret, Map<String, String> params);

    public String masterApi(String appKey, String secret) {
        Map<String, String> params = Maps.newHashMap();
        params.put("type", type.getType());
        return ICaiCloudConstants.MASTER_OPEN_API + OpenApiSignature.signature(appKey, secret, params);
    }

    public String layerApi(String appKey, String secret, String period) {
        Map<String, String> params = Maps.newHashMap();
        params.put("period", period);
        params.put("type", type.getType());
        return ICaiCloudConstants.LAYER_OPEN_API + OpenApiSignature.signature(appKey, secret, params);
    }

    public abstract Map<String, ForecastValue> parse(JSONObject data);

    public static OpenForecastApi of(LotteryEnum type) {
        return Arrays.stream(values())
                     .filter(e -> e.getType() == type)
                     .findFirst()
                     .orElseThrow(ResponseHandler.LOTTO_TYPE_ERROR);
    }
}
