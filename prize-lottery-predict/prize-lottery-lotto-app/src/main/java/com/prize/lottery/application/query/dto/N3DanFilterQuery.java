package com.prize.lottery.application.query.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class N3DanFilterQuery {

    @NotBlank(message = "期号不允许为空")
    private String        period;
    //胆码过滤
    private String        dan;
    //杀码过滤
    private List<String>  kills;
    //跨度过滤
    private List<Integer> kuaList;
    //和值过滤
    private List<Integer> sumList;
    //是否包含组三
    private Boolean       containZu3;
}
