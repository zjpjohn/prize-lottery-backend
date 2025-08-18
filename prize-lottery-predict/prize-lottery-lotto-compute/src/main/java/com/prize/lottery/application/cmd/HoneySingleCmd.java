package com.prize.lottery.application.cmd;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class HoneySingleCmd {

    @NotBlank(message = "数据期号为空")
    private String       period;
    @NotNull(message = "开奖日期为空")
    private LocalDate    lottoDate;
    @NotEmpty(message = "数据集合为空")
    @Size(min = 16, max = 16, message = "数据集长度仅允许为16")
    private List<String> values;

}
