package com.prize.lottery.application.command.dto;

import com.prize.lottery.infrast.persist.enums.ChannelState;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


@Data
public class PutChannelEditCmd {

    @NotNull(message = "渠道标识为空")
    private Long         id;
    private ChannelState state;
    private Integer      targetCnt;
    private String       thirdId;
    @Length(max = 100, message = "说明最多100个字")
    private String       remark;

}
