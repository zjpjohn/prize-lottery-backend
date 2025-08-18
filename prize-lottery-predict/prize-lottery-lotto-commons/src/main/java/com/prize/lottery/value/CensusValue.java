package com.prize.lottery.value;

import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@Data
@NoArgsConstructor
@TypeHandler(type = Type.JSON)
public class CensusValue {

    private List<List<Long>> values;

    public CensusValue(List<Long> value) {
        this.values = Collections.singletonList(value);
    }

    public CensusValue(List<Long> high, List<Long> middle, List<Long> low) {
        this.values = Arrays.asList(high, middle, low);
    }

    public TreeMap<Long, List<Integer>> singleToMap() {
        TreeMap<Long, List<Integer>> map = Maps.newTreeMap();
        if (this.values.size() > 1) {
            return map;
        }
        List<Long> value = this.values.get(0);
        for (int i = 0; i < value.size(); i++) {
            Long          data    = value.get(i);
            List<Integer> dataSet = map.get(data);
            if (dataSet != null) {
                dataSet.add(i);
            } else {
                map.put(data, Lists.newArrayList(i));
            }
        }
        return map;
    }

    public TreeMap<Long, List<String>> singleTo(boolean zero) {
        TreeMap<Long, List<String>> map = Maps.newTreeMap();
        if (this.values.size() > 1) {
            return map;
        }
        List<Long> value = this.values.get(0);
        for (int i = 0; i < value.size(); i++) {
            String       ball    = String.valueOf(zero ? i : i + 1);
            Long         data    = value.get(i);
            List<String> dataSet = map.get(data);
            if (dataSet != null) {
                dataSet.add(ball);
            } else {
                map.put(data, Lists.newArrayList(ball));
            }
        }
        return map;
    }

    /**
     * 单类型统计转换并排序
     *
     * @param zero 数据是否从long开始
     */
    public List<CensusItem> singleAndSort(boolean zero) {
        if (this.values.size() > 1) {
            return Collections.emptyList();
        }
        List<CensusItem> items = Lists.newArrayList();
        List<Long>       value = this.values.get(0);
        for (int i = 0; i < value.size(); i++) {
            CensusItem censusItem = new CensusItem(String.valueOf(zero ? i : i + 1), value.get(i));
            items.add(censusItem);
        }
        items.sort(Comparator.reverseOrder());
        return items;
    }

}
