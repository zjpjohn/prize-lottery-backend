package com.prize.lottery.vo;

import com.cloud.arch.utils.CollectionUtils;
import com.google.common.base.Splitter;
import lombok.Data;

import java.util.List;
import java.util.regex.Pattern;

@Data
public class N3Dan3MetricVo {

    private String  period;
    private String  data;
    private String  hitData;
    private Integer dataHit;
    private Integer amount;

    public boolean includes(String value) {
        return this.data.contains(value);
    }

    public boolean excludes(List<String> values) {
        if (CollectionUtils.isEmpty(values)) {
            return true;
        }
        return values.stream().noneMatch(e -> data.contains(e));
    }

    public boolean containKua(List<Integer> kuaList) {
        return kuaList.contains(this.kua());
    }

    public boolean containSum(List<Integer> sumList) {
        return sumList.contains(this.sum());
    }

    public Integer kua() {
        String[] splits = data.split("\\s+");
        return Integer.parseInt(splits[2]) - Integer.parseInt(splits[0]);
    }

    public Integer sum() {
        return Splitter.on(Pattern.compile("\\s+"))
                       .trimResults()
                       .splitToList(data)
                       .stream()
                       .mapToInt(Integer::parseInt)
                       .sum();
    }

}
