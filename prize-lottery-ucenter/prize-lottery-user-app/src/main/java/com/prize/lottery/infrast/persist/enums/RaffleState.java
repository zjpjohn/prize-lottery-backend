package com.prize.lottery.infrast.persist.enums;

import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@TypeHandler(type = Type.ENUM)
public enum RaffleState implements Value<Integer> {
    LOSS(0, "未中奖"),
    UN_DRAW(1, "待抽奖"),
    WIN(2, "已中奖");

    private final Integer state;
    private final String  name;

    @Override
    public Integer value() {
        return this.state;
    }

    @Override
    public String label() {
        return this.name;
    }
}
