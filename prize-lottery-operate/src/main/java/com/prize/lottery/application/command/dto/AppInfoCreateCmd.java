package com.prize.lottery.application.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class AppInfoCreateCmd {

    /**
     * 应用名称
     */
    @NotBlank(message = "应用名称为空")
    private String name;
    /**
     * 应用logo
     */
    @NotBlank(message = "应用logo为空")
    private String logo;

    /**
     * 应用版权信息
     */
    @NotBlank(message = "版权信息为空")
    private String copyright;
    /**
     * 公司信息
     */
    @NotBlank(message = "公司信息为空")
    private String corp;
    /**
     * 联系电话
     */
    @NotBlank(message = "联系电话为空")
    @Pattern(
            regexp = "^(0\\d{2}-\\d{8}(-\\d{1,4})?)|(0\\d{3}-\\d{7,8}(-\\d{1,4})?)|(400-\\d{3}-\\d{4})$",
            message = "联系电话错误")
    private String telephone;
    /**
     * 联系地址
     */
    @NotBlank(message = "联系地址为空")
    private String address;
    /**
     * 备案信息
     */
    @NotBlank(message = "备案信息为空")
    private String record;
    /**
     * 应用分享链接
     */
    private String shareUri;
    /**
     * open install分享key
     */
    private String openInstall;
    /**
     * 备注信息
     */
    private String remark;

}
