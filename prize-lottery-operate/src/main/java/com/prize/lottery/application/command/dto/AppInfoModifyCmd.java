package com.prize.lottery.application.command.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class AppInfoModifyCmd {

    /**
     * 应用唯一标识
     */
    @NotNull(message = "应用标识为空")
    private Long   id;
    /**
     * 应用logo
     */
    private String logo;
    /**
     * 应用分享链接
     */
    private String shareUri;
    /**
     * 应用版权信息
     */
    private String copyright;
    /**
     * open install分享key
     */
    private String openInstall;
    /**
     * 公司名称
     */
    private String corp;
    /**
     * 联系电话
     */
    @Pattern(
            regexp = "^(0\\d{2}-\\d{8}(-\\d{1,4})?)|(0\\d{3}-\\d{7,8}(-\\d{1,4})?)|(400-\\d{3}-\\d{4})$",
            message = "联系电话错误")
    private String telephone;
    /**
     * 联系地址
     */
    private String address;
    /**
     * 应用备注信息
     */
    private String record;
    /**
     * 备注信息
     */
    private String remark;

}
