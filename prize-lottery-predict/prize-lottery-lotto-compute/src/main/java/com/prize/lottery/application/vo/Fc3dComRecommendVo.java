package com.prize.lottery.application.vo;

import com.prize.lottery.domain.share.model.N3ComRecommendDo;
import com.prize.lottery.value.ComRecommend;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fc3dComRecommendVo {

    //预测期号
    private String       period;
    //组6推荐
    private ComRecommend zu6;
    //组3推荐
    private ComRecommend zu3;
    //推荐胆码集合
    private List<String> dans;
    //推荐杀码集合
    private List<String> kills;

    public static Fc3dComRecommendVo empty(String period) {
        return new Fc3dComRecommendVo(period, ComRecommend.empty("组六"), ComRecommend.empty("组三"), Collections.emptyList(), Collections.emptyList());
    }

    public static Fc3dComRecommendVo fromDo(N3ComRecommendDo recommend) {
        return new Fc3dComRecommendVo(recommend.getPeriod(), recommend.getZu6(), recommend.getZu3(), recommend.getDanWarnings(), recommend.getKillWarnings());
    }
}
