package com.prize.lottery.application.command.dto;

import com.prize.lottery.infrast.persist.enums.FeedbackState;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


@Data
public class FeedbackHandleCmd {
    /**
     * 反馈唯一标识
     */
    @NotNull(message = "反馈标识为空")
    private Long          id;
    /**
     * 反馈状态
     */
    @NotNull(message = "反馈状态为空")
    private FeedbackState state;
    /**
     * 反馈备注信息
     */
    @Length(max = 100, message = "反馈处理备注最多100个字")
    private String        remark;

}
