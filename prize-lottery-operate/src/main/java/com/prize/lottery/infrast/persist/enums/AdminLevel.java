package com.prize.lottery.infrast.persist.enums;

import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import com.cloud.arch.web.error.ApiBizException;

import java.util.Arrays;

@TypeHandler(type = Type.ENUM)
public enum AdminLevel implements Value<Integer> {

    SUPER_ADMIN(1, "超级管理员", "super"),
    PLAIN_ADMIN(2, "一般管理员", "plain");

    private final String  name;
    private final Integer level;
    private final String  role;

    AdminLevel(Integer level, String name, String role) {
        this.level = level;
        this.name  = name;
        this.role  = role;
    }

    public Integer getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    /**
     * 获取枚举变量唯一值
     */
    @Override
    public Integer value() {
        return level;
    }

    /**
     * 枚举值描述
     */
    @Override
    public String label() {
        return name;
    }


    public static AdminLevel findOf(Integer level) {
        return Arrays.stream(values())
                     .filter(v -> v.level.equals(level))
                     .findFirst()
                     .orElseThrow(() -> new ApiBizException(400, "管理员级别错误."));
    }
}
