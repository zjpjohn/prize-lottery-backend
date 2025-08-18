package com.prize.lottery.value;

import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import com.cloud.arch.utils.CollectionUtils;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@TypeHandler(type = Type.JSON)
public class WarningComplex {

    private Integer            hit;
    private List<RecValueItem> items;

    public static WarningComplex empty() {
        return new WarningComplex(-1, Lists.newArrayList());
    }

    /**
     * 构造预警分信息
     */
    public static WarningComplex of(List<String> values) {
        if (CollectionUtils.isEmpty(values)) {
            return new WarningComplex(-1, Lists.newArrayList());
        }
        List<RecValueItem> itemList = values.stream().map(RecValueItem::new).collect(Collectors.toList());
        return new WarningComplex(-1, itemList);
    }

    /**
     * 对结果数据进行过滤
     */
    public WarningComplex filter(List<String> filter) {
        if (CollectionUtils.isEmpty(filter)) {
            return this;
        }
        List<RecValueItem> itemList = this.items.stream()
                                                .filter(e -> filter.contains(e.getValue()))
                                                .collect(Collectors.toList());
        Integer newHit = hit;
        if (newHit == 1) {
            newHit = itemList.stream().anyMatch(v -> v.getHit() >= 1) ? 1 : 0;
        }
        return new WarningComplex(newHit, itemList);
    }

    /**
     * 计算预警结果
     */
    public WarningComplex calcHit(Map<String, Integer> judgeLottery) {
        if (CollectionUtils.isEmpty(items)) {
            return new WarningComplex(0, Lists.newArrayList());
        }
        List<RecValueItem> itemList = this.items.stream()
                                                .map(v -> v.valueHit(judgeLottery))
                                                .collect(Collectors.toList());
        long hitResult = itemList.stream().filter(v -> v.getHit() >= 1).count();
        return new WarningComplex(hitResult >= 1 ? 1 : 0, itemList);
    }

    public boolean hasHit() {
        return Optional.ofNullable(hit).map(v -> v == 1).orElse(false);
    }

}
