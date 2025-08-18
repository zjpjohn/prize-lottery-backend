package com.prize.lottery.application.query.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class Num3DanFilterQuery {

    @NotBlank(message = "期号不允许为空")
    private String period;
    @NotBlank(message = "第一胆码不允许为空")
    private String d1;
    @NotBlank(message = "第二胆码不允许为空")
    private String d2;

}
