package com.prize.lottery.application.command.dto;

import com.prize.lottery.infrast.persist.enums.ChannelType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


@Data
public class PutChannelCreateCmd {

    @NotBlank(message = "应用标识为空")
    private String      appNo;
    @NotNull(message = "渠道类型为空")
    private ChannelType type;
    @NotNull(message = "目标群体为空")
    @PositiveOrZero(message = "目标群体不小于0")
    private Integer     targetCnt;
    @NotBlank(message = "渠道说明为空")
    @Length(max = 100, message = "说明最多100个字")
    private String      remark;
    private String      thirdId;

}
