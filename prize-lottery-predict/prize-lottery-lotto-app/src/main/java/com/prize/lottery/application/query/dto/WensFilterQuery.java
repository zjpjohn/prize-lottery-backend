package com.prize.lottery.application.query.dto;

import com.google.common.collect.Range;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.commons.SumRange;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
public class WensFilterQuery {

    @NotBlank(message = "期号不允许为空")
    private String      period;
    //彩票类型
    @NotNull(message = "彩票类型不允许为空")
    private LotteryEnum type;
    //胆码过滤
    @PositiveOrZero(message = "胆码不允许小于0")
    private Integer     dan;
    //跨度过滤
    private List<Integer> kuaList;
    //和值过滤
    private List<Integer> killList;
    //和值范围过滤
    @Valid
    private SumRange      sum;

    public Range<Integer> sumRange() {
        return Optional.ofNullable(sum).map(SumRange::range).orElse(null);
    }

}
