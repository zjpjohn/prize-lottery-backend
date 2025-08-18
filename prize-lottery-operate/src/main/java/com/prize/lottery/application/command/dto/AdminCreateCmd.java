package com.prize.lottery.application.command.dto;

import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.infrast.persist.enums.AdminLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class AdminCreateCmd {

    /**
     * 账户名称
     */
    @NotBlank(message = "账户名称为空")
    private String  name;
    /**
     * 包含大小写字母数据
     */
    @NotBlank(message = "账户密码为空")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$", message = "密码格式错误")
    private String  password;
    /**
     * 确认密码
     */
    @NotBlank(message = "确认密码为空")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$", message = "密码格式错误")
    private String  confirm;
    /**
     * 手机号
     */
    @NotBlank(message = "手机号为空")
    @Pattern(regexp = "^(1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8})$", message = "手机格式错误")
    private String  phone;
    /**
     * 账户级别
     */
    @NotNull(message = "级别类型为空")
    @Enumerable(enums = AdminLevel.class, message = "级别类型错误.")
    private Integer level;

}
