package com.prize.lottery.application.cmd;

import com.google.common.base.Splitter;
import com.prize.lottery.enums.LotteryEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class TrialCreateCmd {

    @NotNull(message = "奖号类型为空")
    private LotteryEnum     type;
    @Valid
    @NotEmpty(message = "奖号集合为空")
    private List<TrialItem> items;

    @Data
    public static class TrialItem {
        @NotBlank(message = "期号不允许为空")
        private String period;
        @NotBlank(message = "奖号不允许为空")
        private String ball;
    }

    public List<Pair<String, String>> convert() {
        return items.stream().map(i -> Pair.of(i.getPeriod(), lottery(i.getBall()))).collect(Collectors.toList());
    }

    private String lottery(String ball) {
        return Splitter.on("").trimResults().splitToStream(ball).collect(Collectors.joining(" "));
    }

}
