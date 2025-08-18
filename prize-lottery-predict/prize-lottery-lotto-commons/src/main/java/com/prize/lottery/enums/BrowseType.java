package com.prize.lottery.enums;

import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import lombok.Getter;

@Getter
@TypeHandler(type = Type.ENUM)
public enum BrowseType implements Value<Integer> {
    FORECAST(1, "专家推荐预测", ""),
    COMPARE(2, "专家批量比较PK", "2"),
    CHART_FULL(3, "整体预测趋势分析", "3"),
    CHART_VIP(4, "预测趋势综合分析", "4"),
    CHART_HOT(5, "热门热点号码分析", "5"),
    CHART_RATE(6, "精选牛人汇总", "6"),
    LOTTO_INDEX(7, "智能选彩指数", "7"),
    LOTTO_WARN(8, "选彩号码预警", "8"),
    CHART_ITEM(9, "分类预测趋势统计", "9"),
    TODAY_PIVOT(10, "系统今日要点", "10"),
    NUM3_WARN(11, "选号码预警", "11"),
    NUM3_INDEX(12, "选三分类指数", "12"),
    NUM3_LAYER(13, "选三分层预警", "13"),
    ;

    private final Integer type;
    private final String  remark;
    private final String  sourceId;

    BrowseType(Integer type, String remark, String sourceId) {
        this.type     = type;
        this.remark   = remark;
        this.sourceId = sourceId;
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
        return remark;
    }

}
