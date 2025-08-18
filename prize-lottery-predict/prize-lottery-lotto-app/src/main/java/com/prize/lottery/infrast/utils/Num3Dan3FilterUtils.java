package com.prize.lottery.infrast.utils;

import com.cloud.arch.utils.CollectionUtils;
import com.google.common.base.Splitter;
import com.prize.lottery.application.query.dto.Dan3FilterQuery;
import com.prize.lottery.application.query.dto.N3DanFilterQuery;
import com.prize.lottery.value.ForecastValue;
import com.prize.lottery.vo.N3Dan3MetricVo;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@UtilityClass
public class Num3Dan3FilterUtils {

    private static Integer kuaValue(String data) {
        List<Integer> values = Splitter.on(" ").splitToStream(data).map(Integer::parseInt).toList();
        return values.get(2) - values.get(0);
    }

    private static Integer sumValue(String data) {
        return Splitter.on(" ").splitToStream(data).mapToInt(Integer::parseInt).sum();
    }

    /**
     * 选三型三胆过滤
     */
    public static List<ForecastValue> dan3Filter(List<ForecastValue> data, Dan3FilterQuery query) {
        return data.stream().filter(e -> {
            String        value     = e.getData();
            boolean       kuaFilter = true;
            List<Integer> kuaList   = query.getKuas();
            if (CollectionUtils.isNotEmpty(kuaList)) {
                Integer kua = kuaValue(value);
                kuaFilter = kuaList.contains(kua);
            }
            Integer sumMin    = query.getSumMin();
            Integer sumMax    = query.getSumMax();
            boolean sumFilter = true;
            if (kuaFilter && sumMin != null && sumMax != null) {
                Integer sum = sumValue(value);
                sumFilter = sum >= sumMin && sum <= sumMax;
            }
            return kuaFilter && sumFilter;
        }).collect(Collectors.toList());
    }

    /**
     * 选三型三胆预测数据过滤
     *
     * @param query  过滤条件
     * @param loader 数据源加载器
     */
    public static List<N3Dan3MetricVo> filter(N3DanFilterQuery query, Function<String, List<N3Dan3MetricVo>> loader) {
        List<N3Dan3MetricVo> metricList = loader.apply(query.getPeriod());
        if (CollectionUtils.isEmpty(metricList)) {
            return Collections.emptyList();
        }
        if (StringUtils.isNotBlank(query.getDan())) {
            metricList = metricList.stream().filter(e -> e.includes(query.getDan())).collect(Collectors.toList());
        }
        if (CollectionUtils.isNotEmpty(query.getKills())) {
            metricList = metricList.stream().filter(e -> e.excludes(query.getKills())).collect(Collectors.toList());
        }
        if (CollectionUtils.isNotEmpty(query.getKuaList())) {
            metricList = metricList.stream().filter(e -> e.containKua(query.getKuaList())).collect(Collectors.toList());
        }
        if (CollectionUtils.isNotEmpty(query.getSumList())) {
            metricList = metricList.stream().filter(e -> e.containSum(query.getSumList())).collect(Collectors.toList());
        }
        return metricList;
    }

}
