package com.prize.lottery.application.command.dto;

import com.prize.lottery.infrast.persist.enums.WithLevelSate;
import com.prize.lottery.infrast.persist.value.LevelValue;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
public class WithLevelEditCmd {

    @NotNull(message = "提现等级标识为空")
    private Long             id;
    @Length(max = 100, message = "描述说明最多100个文字")
    private String           remark;
    @Valid
    private List<LevelValue> levels;
    private WithLevelSate    state;

}
