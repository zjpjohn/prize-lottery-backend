package com.prize.lottery.application.vo;

import com.cloud.arch.utils.CollectionUtils;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@NoArgsConstructor
public class N3Comb5StatsVo {

    private List<StatsInfo> stats;

    public N3Comb5StatsVo(List<StatsInfo> stats) {
        this.stats = stats;
    }

    public Integer getSize() {
        if (CollectionUtils.isEmpty(stats)) {
            return 0;
        }
        return stats.size();
    }

    public void filter(String dan, List<String> kills, List<Integer> kuaList, List<Integer> sums) {
        if (StringUtils.isNotBlank(dan)) {
            this.stats = this.stats.stream().filter(e -> e.filterDan(dan)).collect(Collectors.toList());
        }
        if (CollectionUtils.isNotEmpty(kills)) {
            this.stats = this.stats.stream().filter(e -> e.filterKill(kills)).collect(Collectors.toList());
        }
        if (CollectionUtils.isNotEmpty(kuaList)) {
            this.stats = this.stats.stream().filter(e -> e.filterKua(kuaList)).collect(Collectors.toList());
        }
        if (CollectionUtils.isNotEmpty(sums)) {
            this.stats = this.stats.stream().filter(e -> e.filterSum(sums)).collect(Collectors.toList());
        }
    }

    public static Stream<String> flatLottery(String data, Boolean containZu3) {
        String[]     splits = data.split("\\*");
        String[]     bai    = splits[0].split("\\s+");
        String[]     shi    = splits[1].split("\\s+");
        String[]     ge     = splits[2].split("\\s+");
        List<String> result = Lists.newArrayList();
        int          length = bai.length;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                for (int k = 0; k < length; k++) {
                    if (!containZu3 && (bai[i].equals(shi[j]) || bai[i].equals(ge[k]) || shi[j].equals(ge[k]))) {
                        continue;
                    }
                    String cell = bai[i] + " " + shi[j] + " " + ge[k];
                    result.add(cell);
                }
            }
        }
        return result.stream();
    }

    @Data
    public static class StatsInfo {

        //直选号
        private String direct;
        //组选号
        private String combine;
        //直选出现次数
        private Long   stats;

        public StatsInfo(String direct, Long stats) {
            this.direct  = direct;
            this.stats   = stats;
            this.combine = directToCom(direct);
        }

        private String directToCom(String direct) {
            return Splitter.on(" ").splitToStream(direct).sorted().collect(Collectors.joining(" "));
        }

        public boolean filterKua(List<Integer> kuaList) {
            List<Integer> collected = Splitter.on(" ")
                                              .splitToStream(combine)
                                              .map(Integer::parseInt)
                                              .sorted()
                                              .collect(Collectors.toList());
            return kuaList.contains(collected.get(2) - collected.get(0));
        }

        public boolean filterKill(List<String> kill) {
            return kill.stream().noneMatch(k -> this.combine.contains(k));
        }

        public boolean filterSum(List<Integer> sum) {
            int summed = Splitter.on(" ").splitToStream(combine).mapToInt(Integer::parseInt).sum();
            return sum.contains(summed);
        }

        public boolean filterDan(String dan) {
            return this.combine.contains(dan);
        }
    }

}
