package com.prize.lottery.infrast.commons;

import com.cloud.arch.utils.CollectionUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class WensFilterResult {

    @Data
    @NoArgsConstructor
    public static class ResultItem {

        private String  value;
        private Integer hit;

        public ResultItem(String value) {
            this.value = value;
        }
    }

    private String           period;
    //开奖号码
    private String           lottery;
    //下一期开奖号
    private String           next;
    //稳氏断组
    private String           segment;
    //4胆码表
    private String           danTable;
    //计算结果
    private List<ResultItem> values;

    public void calcHit(String ball) {
        if (CollectionUtils.isNotEmpty(this.values) && StringUtils.isNotBlank(ball)) {
            this.values.forEach(e -> e.setHit(ball.equals(e.value) ? 1 : 0));
        }
    }

    public Integer getSize() {
        if (CollectionUtils.isEmpty(values)) {
            return 0;
        }
        return values.size();
    }

    public void buildSegment(int[][] data) {
        this.segment = joinArray(data[0])
                + "-"
                + joinArray(data[1])
                + "-"
                + joinArray(data[2])
                + "-"
                + joinArray(data[3]);
    }

    private String joinArray(int[] data) {
        return Arrays.stream(data).mapToObj(String::valueOf).collect(Collectors.joining(""));
    }

    public void buildDanTable(Set<Integer> data) {
        this.danTable = data.stream().sorted().map(String::valueOf).collect(Collectors.joining(""));
    }
}
