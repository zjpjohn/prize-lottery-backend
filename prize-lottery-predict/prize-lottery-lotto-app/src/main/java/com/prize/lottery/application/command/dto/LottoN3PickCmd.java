package com.prize.lottery.application.command.dto;

import com.cloud.arch.web.validation.annotation.Enumerable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class LottoN3PickCmd {

    @NotNull(message = "彩票类型为空")
    @Enumerable(ranges = {"fc3d", "pl3"}, message = "彩种类型错误")
    private String             lottery;
    @NotBlank(message = "选号期号为空")
    private String             period;
    @NotEmpty(message = "选号号码为空")
    private List<List<String>> balls;

}
