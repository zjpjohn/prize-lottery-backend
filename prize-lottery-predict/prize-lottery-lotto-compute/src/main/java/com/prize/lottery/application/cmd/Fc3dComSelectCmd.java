package com.prize.lottery.application.cmd;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class Fc3dComSelectCmd {

    //预测期号
    @NotBlank(message = "预测期号为空")
    private String        period;
    //确定胆码
    @NotEmpty(message = "杀码集合为空")
    private List<String>  dans;
    //确定杀码
    @NotEmpty(message = "杀码集合为空")
    private List<String>  kills;
    //跨度过滤
    @NotEmpty(message = "号码跨度为空")
    private List<Integer> kuas;
    //是否保存计算结果
    @NotNull(message = "保存结果表为空")
    private Boolean       save;
}
