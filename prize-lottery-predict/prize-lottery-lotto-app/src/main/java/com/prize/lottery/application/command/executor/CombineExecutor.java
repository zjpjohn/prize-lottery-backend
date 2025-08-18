package com.prize.lottery.application.command.executor;

import com.cloud.arch.utils.CollectionUtils;
import com.google.common.base.Splitter;
import com.prize.lottery.application.command.dto.ComCombineCalcCmd;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface CombineExecutor {

    List<Pair<String, Long>> execute(ComCombineCalcCmd cmd);

    default boolean filterKua(List<Integer> kuas, String data) {
        if (CollectionUtils.isEmpty(kuas)) {
            return true;
        }
        List<Integer> values = Splitter.on(" ").trimResults().splitToStream(data).map(Integer::parseInt).toList();
        int           kua    = values.get(2) - values.get(0);
        return kuas.contains(kua);
    }

    default boolean filterSum(List<Integer> sums, String data) {
        if (CollectionUtils.isEmpty(sums)) {
            return true;
        }
        int sum = Splitter.on(" ").trimResults().splitToStream(data).mapToInt(Integer::parseInt).sum();
        return sums.contains(sum);
    }

    default boolean filterDan(String forecast, List<String> danTable) {
        if (CollectionUtils.isEmpty(danTable)) {
            return true;
        }
        return danTable.stream().anyMatch(forecast::contains);
    }

    default boolean filterTwoMa(String forecast, List<String> twoMaList) {
        if (CollectionUtils.isEmpty(twoMaList)) {
            return true;
        }
        String[] values = forecast.trim().split("\\s+");
        return twoMaList.stream()
                        .anyMatch(twoMa -> (twoMa.contains(values[0]) && twoMa.contains(values[1]))
                                || (twoMa.contains(values[0]) && twoMa.contains(values[2]))
                                || (twoMa.contains(values[1]) && twoMa.contains(values[2])));
    }

}
