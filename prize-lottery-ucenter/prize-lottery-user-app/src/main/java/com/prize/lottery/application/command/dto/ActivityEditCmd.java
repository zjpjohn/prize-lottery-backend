package com.prize.lottery.application.command.dto;

import com.prize.lottery.infrast.persist.enums.ActivityState;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Data
public class ActivityEditCmd {

    @NotNull(message = "活动标识为空")
    private Long          id;
    private String        name;
    @Range(min = 1, max = 7, message = "有效期无效")
    private Integer       duration;
    @Range(min = 1, max = 10, message = "抽奖最小机会次数错误")
    private Integer       minimum;
    @Range(min = 1, message = "失败次数后错误")
    private Integer       throttle;
    private List<String>  remark;
    private ActivityState state;
}
