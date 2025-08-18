package com.prize.lottery.infrast.persist.enums;

import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
@TypeHandler(type = Type.ENUM)
public enum TransferScene implements Value<String> {
    USER_REWARD_TRANS("USER_REWARD_TRANS", "用户收益提现"),
    USER_AGENT_TRANS("USER_AGENT_TRANS", "流量主收益提现"),
    USER_EXPERT_TRANS("USER_EXPERT_TRANS", "专家收益提现");

    private final String scene;
    private final String remark;

    @Override
    public String value() {
        return this.scene;
    }

    @Override
    public String label() {
        return this.remark;
    }

    public static TransferScene of(String scene) {
        return Arrays.stream(values())
                     .filter(e -> e.scene.equals(scene))
                     .findFirst()
                     .orElseThrow(ResponseHandler.ENUM_VALUE_UNKNOWN);
    }

    public static Optional<TransferScene> ofNullable(String scene) {
        return Arrays.stream(values()).filter(e -> e.scene.equals(scene)).findFirst();
    }

}
