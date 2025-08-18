package com.prize.lottery.infrast.persist.enums;

import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@TypeHandler(type = Type.ENUM)
public enum AuditStep implements Value<Integer> {
    START(1, "审核开始"),
    NEXT(2, "提请上级审核"),
    END(3, "审核结束"),
    ;

    private final Integer step;
    private final String  remark;


    @Override
    public Integer value() {
        return this.step;
    }

    @Override
    public String label() {
        return this.remark;
    }

}
