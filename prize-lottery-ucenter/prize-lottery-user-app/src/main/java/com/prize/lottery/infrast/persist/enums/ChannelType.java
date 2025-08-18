package com.prize.lottery.infrast.persist.enums;

import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@TypeHandler(type = Type.ENUM)
public enum ChannelType implements Value<Integer> {
    WECHAT(1, "微信群"),
    QQ(2, "QQ群"),
    BAI_JIA_HAO(3, "百家号"),
    DOU_YIN(4, "抖音"),
    KUAI_SHOU(5, "快手"),
    RED_BOOK(6, "小红书"),
    STATION(7, "彩票站"),
    OTHER(8, "其他");

    private final Integer type;
    private final String  name;

    @Override
    public Integer value() {
        return this.type;
    }

    @Override
    public String label() {
        return this.name;
    }
}
