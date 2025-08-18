package com.prize.lottery.application.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Data
public class ComCombineCalcCmd {

    @NotBlank(message = "预测期号为空")
    private String        period;
    @NotNull(message = "最小出现次数为空")
    @Range(min = 2, max = 5, message = "最小出现次数2~5")
    private Integer       minSize;
    @NotNull(message = "最大出现次数为空")
    @Range(min = 7, max = 13, message = "最大出现次数7~13")
    private Integer       maxSize;
    //定胆
    private List<String>  danList;
    //杀码
    private List<String>  killList;
    //稳氏四胆
    private List<String>  wensDan;
    //每日四胆
    private List<String>  weekDan;
    //试机四胆
    private List<String>  shiDan;
    //十位对码胆
    private List<String>  tenDan;
    //自配两码组合
    private List<String>  twoMa;
    //和值过滤
    private List<Integer> sumList;
    //跨度过滤
    private List<Integer> kuaList;

}
