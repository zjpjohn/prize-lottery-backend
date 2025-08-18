package com.prize.lottery.application.cmd;

import com.prize.lottery.dto.N3BestDanDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class N3TodayPivotCmd {

    @NotBlank(message = "预测期号不允许为空")
    private String       period;
    @NotBlank(message = "第一胆码不允许为空")
    private String       best;
    @NotBlank(message = "第二胆码不允许为空")
    private String       second;
    @NotEmpty(message = "优质选号不允许为空")
    private List<String> quality;

    public N3BestDanDto convert(Function<String, String> lastFunc) {
        N3BestDanDto bestDan = new N3BestDanDto();
        bestDan.setPeriod(period);
        bestDan.setBest(best);
        bestDan.setSecondary(second);
        bestDan.setMoreList(quality);
        bestDan.setLast(lastFunc.apply(period));
        return bestDan;
    }

    public String quality() {
        return this.quality.stream().sorted().collect(Collectors.joining(" "));
    }

}
