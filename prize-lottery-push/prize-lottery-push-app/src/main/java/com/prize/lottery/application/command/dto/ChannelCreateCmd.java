package com.prize.lottery.application.command.dto;

import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.infrast.persist.enums.ChannelScope;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


@Data
public class ChannelCreateCmd {

    @NotBlank(message = "渠道名称为空")
    private String       name;
    @NotBlank(message = "渠道码为空")
    private String       channel;
    @NotBlank(message = "渠道图标为空")
    private String       cover;
    @NotBlank(message = "渠道说明为空")
    @Length(max = 50, message = "描述说明最多50个字")
    private String       remark;
    @NotNull(message = "渠道类型为空")
    @Enumerable(ranges = {"0", "1"}, message = "渠道类型错误")
    private Integer      type;
    @NotNull(message = "渠道可见范围错误")
    private ChannelScope scope;
    @NotNull(message = "渠道提醒标识为空")
    @Enumerable(ranges = {"0", "1"}, message = "提醒标识为空")
    private Integer      remind;

}
