package com.prize.lottery.application.command.dto;

import com.prize.lottery.infrast.persist.enums.CommonState;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;


@Data
public class TagGroupModifyCmd {

    @NotNull(message = "唯一标识为空")
    private Long        id;
    private String      name;
    private String      tagPrefix;
    private String      remark;
    private CommonState state;
    @Positive(message = "上限值大于0")
    private Integer     upperBound;

}
