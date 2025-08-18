package com.prize.lottery.vo;

import com.cloud.arch.utils.CollectionUtils;
import com.google.common.base.Splitter;
import lombok.Data;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Data
public class Num3LottoFollowVo {

    private Long      id;
    private String    period;
    private LocalDate lotDate;
    private String    red;
    private String    com;
    private String    last1;
    private String    last2;
    private String    next1;
    private String    next2;

    private List<Integer> intBalls() {
        return Splitter.on(Pattern.compile("\\s+"))
                       .trimResults()
                       .splitToStream(this.red)
                       .map(Integer::parseInt)
                       .collect(Collectors.toList());
    }

    public boolean filter(List<String> danList, List<Integer> kuaList, List<Integer> sumList) {
        List<Integer> balls = intBalls();
        if (CollectionUtils.isNotEmpty(danList)) {
            if (danList.stream().noneMatch(e -> red.contains(e.trim()))) {
                return false;
            }
        }
        if (CollectionUtils.isNotEmpty(kuaList)) {
            Integer kua = calcKua(balls);
            if (!kuaList.contains(kua)) {
                return false;
            }
        }
        if (CollectionUtils.isNotEmpty(sumList)) {
            Integer sum = balls.stream().reduce(Integer::sum).orElse(0);
            return sumList.contains(sum);
        }
        return true;
    }

    private Integer calcKua(List<Integer> balls) {
        Collections.sort(balls);
        return Math.abs(balls.get(2) - balls.get(0));
    }
}
