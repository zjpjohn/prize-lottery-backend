package com.prize.lottery.enums;


import com.cloud.arch.enums.Value;

public enum ChartType implements Value<Integer> {
    VIP_CHART(1, "1", "vip专家统计") {
        @Override
        public BrowseType browseType() {
            return BrowseType.CHART_VIP;
        }
    },
    HOT_CHART(2, "2", "热门专家统计") {
        @Override
        public BrowseType browseType() {
            return BrowseType.CHART_HOT;
        }
    },
    RATE_CHART(3, "3", "高命中率统计") {
        @Override
        public BrowseType browseType() {
            return BrowseType.CHART_RATE;
        }
    },
    ALL_CHART(4, "4", "全量专家统计") {
        @Override
        public BrowseType browseType() {
            return BrowseType.CHART_FULL;
        }
    },
    ITEM_CHART(5, "5", "分类专家统计") {
        @Override
        public BrowseType browseType() {
            return BrowseType.CHART_ITEM;
        }
    },
    ;

    private final Integer type;
    private final String  sourceId;
    private final String  name;

    ChartType(Integer type, String sourceId, String name) {
        this.type     = type;
        this.sourceId = sourceId;
        this.name     = name;
    }

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getSourceId() {
        return sourceId;
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
        return name;
    }

    /**
     * 浏览类型
     */
    public abstract BrowseType browseType();

}
