package com.prize.lottery.infrast.persist.enums;

import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@TypeHandler(type = Type.ENUM)
public enum ChannelScope implements Value<Integer> {
    ALL(0, "全部"),
    USER(1, "用户"),
    EXPERT(2, "专家"),
    AGENT(3, "代理商");

    private final Integer scope;
    private final String  remark;

    @Override
    public Integer value() {
        return this.scope;
    }

    @Override
    public String label() {
        return this.remark;
    }
}
