package com.prize.lottery.application.command.dto;

import com.prize.lottery.infrast.persist.enums.TransferScene;
import com.prize.lottery.infrast.persist.value.LevelValue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
public class WithLevelCreateCmd {

    @NotNull(message = "提现场景为空")
    private TransferScene    scene;
    @NotEmpty(message = "提现等级为空")
    private List<LevelValue> levels;
    @Length(max = 100, message = "描述说明最多100个文字")
    private String           remark;
}
