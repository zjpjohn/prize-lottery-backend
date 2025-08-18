package com.prize.lottery.application.query.dto;

import com.prize.lottery.dto.N3BestDanDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;
import java.util.function.Function;

@Data
public class N3BestQuery {

    @NotBlank(message = "预测期号不允许为空")
    private String       period;
    @NotBlank(message = "胆码不允许为空")
    private String       best;
    @NotBlank(message = "胆码不允许为空")
    private String       secondary;
    @NotEmpty(message = "胆码集合不允许为空")
    private List<String> mores;

    public N3BestDanDto convert(Function<String, String> lastCalc) {
        N3BestDanDto dto = new N3BestDanDto();
        dto.setPeriod(period);
        dto.setBest(best);
        dto.setSecondary(secondary);
        dto.setMoreList(mores);
        dto.setLast(lastCalc.apply(period));
        return dto;
    }
}

