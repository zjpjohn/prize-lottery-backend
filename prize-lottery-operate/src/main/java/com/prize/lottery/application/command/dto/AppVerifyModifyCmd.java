package com.prize.lottery.application.command.dto;

import com.prize.lottery.infrast.persist.enums.CommonState;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class AppVerifyModifyCmd {

    @NotNull(message = "标识为空")
    private Long        id;
    private String      appPack;
    private String      signature;
    private String      authKey;
    private CommonState state;
    private String      success;
    private String      cancel;
    private String      downgrades;
    private String      remark;

}
