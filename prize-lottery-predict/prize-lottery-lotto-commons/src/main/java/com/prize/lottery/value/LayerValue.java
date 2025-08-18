package com.prize.lottery.value;

import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@TypeHandler(type = Type.JSON)
public class LayerValue {

    //分层过滤名称
    private String         name;
    //命中类型:0-未命中,1-组六命中,2-组三命中
    private Integer        hit;
    //分层过滤条件
    private List<Integer>  condition;
    //组三过滤结果
    private WarningComplex zu3;
    //组六过滤结果
    private WarningComplex zu6;

    public static LayerValue empty() {
        return new LayerValue("", Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
    }

    public LayerValue(String name, List<Integer> condition, List<String> zu6, List<String> zu3) {
        this.name      = name;
        this.condition = condition;
        this.zu6       = WarningComplex.of(zu6);
        this.zu3       = WarningComplex.of(zu3);
        this.hit       = -1;
    }

    public void calcHit(Map<String, Integer> judgeLottery) {
        this.zu3 = this.zu3.calcHit(judgeLottery);
        this.zu6 = this.zu6.calcHit(judgeLottery);
        this.hit = 0;
        if (this.zu6.hasHit() && judgeLottery.size() == 3) {
            this.hit = 1;
            return;
        }
        if (this.zu3.hasHit() && judgeLottery.size() == 2) {
            this.hit = 2;
        }
    }
}
