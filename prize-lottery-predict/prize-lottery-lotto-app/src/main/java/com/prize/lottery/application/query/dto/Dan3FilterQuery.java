package com.prize.lottery.application.query.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.List;

@Data
public class Dan3FilterQuery {

    @NotBlank(message = "预测期号为空")
    private String        period;
    @NotEmpty(message = "过滤胆码为空")
    private List<String>  dans;
    private List<String>  kills;
    private List<Integer> kuas;
    @PositiveOrZero(message = "最小和值为正数")
    private Integer       sumMin;
    @PositiveOrZero(message = "最大和值为正数")
    private Integer       sumMax;

}
