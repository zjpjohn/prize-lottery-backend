package com.prize.lottery.application.vo;

import com.prize.lottery.domain.share.model.N3ComRecommendDo;
import com.prize.lottery.value.ComRecommend;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class Pl3DkRecommendVo {

    //预测期号
    private String              period;
    //组6推荐
    private ComRecommend        zu6;
    //组3推荐
    private ComRecommend        zu3;
    //推荐胆码集合
    private List<String>        dans;
    //推荐杀码集合
    private List<String>        kills;
    //全部号码杀码权重
    private Map<String, Double> killWeight;

    public static Pl3DkRecommendVo empty(String period) {
        return new Pl3DkRecommendVo(period, ComRecommend.empty("组六"), ComRecommend.empty("组三"), Collections.emptyList(), Collections.emptyList(), Collections.emptyMap());
    }

    public static Pl3DkRecommendVo fromDo(N3ComRecommendDo recommend, Map<String, Double> killWeight) {
        return new Pl3DkRecommendVo(recommend.getPeriod(), recommend.getZu6(), recommend.getZu3(), recommend.getDanWarnings(), recommend.getKillWarnings(), killWeight);
    }

}
