package com.prize.lottery.application.command.dto;

import com.prize.lottery.infrast.persist.enums.CommonState;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class AssistantModifyCmd {

    @NotNull(message = "助手标识为空")
    private Long        id;
    private String      suitVer;
    private String      title;
    private String      content;
    private CommonState state;
    private String      remark;

}
