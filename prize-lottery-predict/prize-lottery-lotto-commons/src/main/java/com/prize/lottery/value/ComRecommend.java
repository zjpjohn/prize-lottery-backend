package com.prize.lottery.value;

import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.prize.lottery.enums.WarningEnums;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TypeHandler(type = Type.JSON)
public class ComRecommend {

    private String             channel;
    private List<RecValueItem> items;

    public static ComRecommend empty(String channel) {
        return new ComRecommend(channel, Lists.newArrayList());
    }

    public static ComRecommend zu3(List<String> items) {
        return of("组三", items);
    }

    public static ComRecommend zu6(List<String> items) {
        return of("组六", items);
    }

    public static ComRecommend of(String channel, List<String> items) {
        List<RecValueItem> valueItems = items.stream().map(RecValueItem::new).collect(Collectors.toList());
        return new ComRecommend(channel, valueItems);
    }

    /**
     * 计算和值集合
     */
    public Map<WarningEnums, Set<String>> calcWarning() {
        Set<Integer> sumValues = Sets.newHashSet();
        Set<Integer> kuas      = Sets.newHashSet();
        Set<String> values = items.stream().map(RecValueItem::getValue).map(e -> {
            List<Integer> list = Splitter.on(" ")
                                         .trimResults()
                                         .omitEmptyStrings()
                                         .splitToStream(e)
                                         .map(Integer::parseInt)
                                         .sorted()
                                         .collect(Collectors.toList());
            kuas.add(list.get(list.size() - 1) - list.get(0));
            return list;
        }).flatMap(e -> {
            int          sum  = e.stream().mapToInt(i -> i).sum();
            Set<Integer> sums = Sets.newHashSet();
            if (e.size() == 3) {
                sums.add(sum);
            } else if (e.size() == 2) {
                sums.add(sum + e.get(0));
                sums.add(sum + e.get(1));
            }
            sumValues.addAll(sums);
            return sums.stream();
        }).map(String::valueOf).collect(Collectors.toSet());
        Map<WarningEnums, Set<String>> result = Maps.newHashMap();
        //和值预警
        result.put(WarningEnums.SUM_WARNING, values);
        //和尾预警
        Set<String> tails = sumValues.stream().map(e -> e % 10).map(String::valueOf).collect(Collectors.toSet());
        result.put(WarningEnums.TAIL_WARNING, tails);
        //跨度预警
        Set<String> kuaValues = kuas.stream().map(String::valueOf).collect(Collectors.toSet());
        result.put(WarningEnums.KUA_WARNING, kuaValues);

        return result;
    }

}
