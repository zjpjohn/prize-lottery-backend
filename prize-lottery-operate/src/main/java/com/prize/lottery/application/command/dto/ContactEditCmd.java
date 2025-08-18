package com.prize.lottery.application.command.dto;

import com.prize.lottery.infrast.persist.enums.CommonState;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ContactEditCmd {

    @NotNull(message = "联系人标识为空")
    private Long        id;
    @Length(max = 20, message = "名称最多20个字")
    private String      name;
    private String      qrImg;
    @Length(max = 100, message = "说明信息最多100个字")
    private String      remark;
    private CommonState state;

}
