package com.prize.lottery.application.command.dto;

import com.prize.lottery.enums.LotteryEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class BatchSubscribeCmd {

    @NotNull(message = "用户标识为空")
    private Long                userId;
    @Valid
    @NotEmpty(message = "订阅专家为空")
    private List<SubscribeItem> masters;

    //
    public List<SubscribeMasterCmd> toCommands() {
        return masters.stream()
                      .map(e -> new SubscribeMasterCmd(e.getMasterId(), userId, e.getType()))
                      .collect(Collectors.toList());
    }

    @Data
    public static class SubscribeItem {

        @NotBlank(message = "专家标识为空")
        private String      masterId;
        @NotNull(message = "订阅渠道为空")
        private LotteryEnum type;

    }

}
