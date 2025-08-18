package com.prize.lottery.application.cmd;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class DkRecommendCmd {

    //计算期号
    @NotBlank(message = "计算期号不允许为空")
    private String        period;
    //手动加入杀码号码
    private List<String>  kills;
    //排除自动计算出的杀码数据
    private List<String>  excludes;
    //号码跨度过滤
    private List<Integer> kuas;
}
