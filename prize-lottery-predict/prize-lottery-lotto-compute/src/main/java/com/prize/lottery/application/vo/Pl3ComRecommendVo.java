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
public class Pl3ComRecommendVo {

    //预测期号
    private String       period;
    //组6推荐
    private ComRecommend zu6;
    //组3推荐
    private ComRecommend zu3;
    //定胆集合
    private List<String> dans;
    //杀码集合
    private List<String> kills;

    public static Pl3ComRecommendVo empty(String period) {
        return new Pl3ComRecommendVo(period, ComRecommend.empty("组六"), ComRecommend.empty("组三"), Collections.emptyList(), Collections.emptyList());
    }

    public static Pl3ComRecommendVo fromDo(N3ComRecommendDo rec) {
        return new Pl3ComRecommendVo(rec.getPeriod(), rec.getZu6(), rec.getZu3(), rec.getDanWarnings(), rec.getKillWarnings());
    }
}
