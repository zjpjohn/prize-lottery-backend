package com.prize.lottery.application.command.dto;

import com.cloud.arch.web.utils.Assert;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.infrast.error.ResponseErrorHandler;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;


@Data
public class AnnounceCreateCmd {

    @NotBlank(message = "公告标题为空")
    private String  title;
    @NotBlank(message = "公告内容为空")
    private String  text;
    @NotBlank(message = "消息通道为空")
    private String  channel;
    @NotNull(message = "消息类型为空")
    @Enumerable(ranges = {"1", "2"}, message = "消息类型错误")
    private Integer type;
    //链接消息是配置APP端路由地址
    private String  link;

    public void validate() {
        if (this.type == 2) {
            Assert.state(StringUtils.isNotBlank(link), ResponseErrorHandler.MESSAGE_LINK_NULL);
        }
    }
}
