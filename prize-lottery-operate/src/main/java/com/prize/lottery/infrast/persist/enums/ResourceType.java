package com.prize.lottery.infrast.persist.enums;

import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import com.cloud.arch.web.converter.ConvertParseException;

import java.util.Arrays;

@TypeHandler(type = Type.ENUM)
public enum ResourceType implements Value<Integer> {
    ICON(1, "应用图标"),
    IMAGE(2, "背景图片"),
    OTHER(3, "其他资源");

    private final Integer type;
    private final String  remark;

    ResourceType(Integer type, String remark) {
        this.type   = type;
        this.remark = remark;
    }

    public Integer getType() {
        return type;
    }

    public String getRemark() {
        return remark;
    }

    /**
     * 获取枚举变量唯一值
     */
    @Override
    public Integer value() {
        return type;
    }

    /**
     * 枚举值描述
     */
    @Override
    public String label() {
        return this.remark;
    }

    /**
     * 在 @RequestBody请求体中使用枚举
     * 需实现静态方法映射并标注@JsonCreator
     *
     * @param type 类型值
     */
    public static ResourceType of(int type) {
        return Arrays.stream(values())
                     .filter(e -> e.value() == type)
                     .findFirst()
                     .orElseThrow(() -> new ConvertParseException("类型错误,可用值范围[1,2,3]."));
    }
}
