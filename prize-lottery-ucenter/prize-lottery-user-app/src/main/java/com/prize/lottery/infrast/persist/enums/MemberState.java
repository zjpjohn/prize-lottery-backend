package com.prize.lottery.infrast.persist.enums;

import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@TypeHandler(type = Type.ENUM)
public enum MemberState implements Value<Integer> {
    INVALID(0, "无效"),
    NORMAL(1, "正常"),
    EXPIRED(2, "过期"),
    ;

    private final Integer state;
    private final String  remark;

    @Override
    public Integer value() {
        return this.state;
    }

    @Override
    public String label() {
        return this.remark;
    }

}
