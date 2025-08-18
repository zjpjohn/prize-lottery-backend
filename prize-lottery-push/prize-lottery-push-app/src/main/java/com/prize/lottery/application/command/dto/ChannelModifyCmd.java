package com.prize.lottery.application.command.dto;

import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.infrast.persist.enums.ChannelScope;
import com.prize.lottery.infrast.persist.enums.CommonState;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class ChannelModifyCmd {

    @NotNull(message = "渠道唯一标识为空")
    private Long         id;
    private String       name;
    private String       cover;
    private String       remark;
    @Enumerable(ranges = {"0", "1"}, message = "渠道类型错误")
    private Integer      type;
    @Enumerable(ranges = {"0", "1"}, message = "提醒标识错误")
    private Integer      remind;
    private ChannelScope scope;
    private CommonState  state;

}
