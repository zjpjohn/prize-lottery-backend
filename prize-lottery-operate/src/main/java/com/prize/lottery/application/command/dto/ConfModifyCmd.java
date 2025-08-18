package com.prize.lottery.application.command.dto;

import com.prize.lottery.infrast.persist.enums.ConfState;
import com.prize.lottery.infrast.persist.enums.ConfType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class ConfModifyCmd {

    @NotNull(message = "配置标识为空")
    private Long      id;
    /**
     * 配置key
     */
    private String    confKey;
    /**
     * 配置值
     */
    private String    confVal;
    /**
     * 配置类型
     */
    private ConfType  valType;
    /**
     * 配置描述备注信息
     */
    private String    remark;
    /**
     * 配置状态
     */
    private ConfState state;
}
