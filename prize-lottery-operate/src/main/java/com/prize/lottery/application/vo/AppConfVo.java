package com.prize.lottery.application.vo;

import lombok.Data;

@Data
public class AppConfVo {

    /**
     * 配置名称key
     */
    private String  key;
    /**
     * 配置值value
     */
    private String  value;
    /**
     * 配置值类型
     */
    private Integer type;

}
