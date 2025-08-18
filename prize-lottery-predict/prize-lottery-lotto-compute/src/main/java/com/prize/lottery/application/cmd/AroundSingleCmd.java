package com.prize.lottery.application.cmd;

import com.prize.lottery.enums.AroundType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.value.AroundValue;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AroundSingleCmd {

    @NotNull(message = "类型不允许为空")
    private AroundType                   type;
    @NotNull(message = "彩票类型不允许为空")
    private LotteryEnum                  lotto;
    @NotBlank(message = "期号不允许为空")
    private String                       period;
    @NotNull(message = "开奖日期不允许为空")
    private LocalDate                    lottoDate;
    @Valid
    @NotEmpty(message = "号码集合不允许为空")
    @Size(min = 11, max = 11, message = "数据集长度仅允许为11")
    private List<AroundValue.AroundCell> cells;

}
