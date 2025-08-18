package com.prize.lottery.application.command.dto;

import com.prize.lottery.infrast.persist.enums.PutState;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


@Data
public class PutRecordEditCmd {

    @NotNull(message = "记录标识为空")
    private Long     id;
    @PositiveOrZero(message = "预期金额不小于0")
    private Long     expectAmt;
    @Length(max = 100, message = "说明最多100个字")
    private String   remark;
    private PutState state;

}
