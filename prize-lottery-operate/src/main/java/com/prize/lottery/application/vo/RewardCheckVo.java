package com.prize.lottery.application.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RewardCheckVo {

    private final Integer code;
    private final String  message;

    public static RewardCheckVo success() {
        return new RewardCheckVo(0, "回调验证成功");
    }

    public static RewardCheckVo failure(String message) {
        return new RewardCheckVo(100, message);
    }

}
