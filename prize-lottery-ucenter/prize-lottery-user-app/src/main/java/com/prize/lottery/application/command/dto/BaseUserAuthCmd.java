package com.prize.lottery.application.command.dto;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.RegisterChannel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.util.StringUtils;


@Data
public class BaseUserAuthCmd {
    /**
     * 应用设备标识
     */
    @NotBlank(message = "设备标识为空")
    private String deviceId;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号为空")
    @Pattern(regexp = "^(1([38][0-9]|4[579]|5[0-3,5-9]|66|7[0135678]|9[89])\\d{8})$", message = "手机格式错误")
    private String          phone;
    /**
     * 邀请渠道 1-彩票站 2-用户邀请 3-直接下载 4-渠道邀请
     */
    @NotNull(message = "注册渠道为空")
    private RegisterChannel channel;
    /**
     * 注册邀请码
     */
    private String          invite;

    /**
     * 注册邀请码校验
     */
    public void checkInviteCode() {
        if (channel == RegisterChannel.USER_INVITE) {
            Assert.state(StringUtils.hasText(invite) && invite.length() < 8, ResponseHandler.INVITE_CODE_ILLEGAL);
            return;
        }
        if (channel == RegisterChannel.CHANNEL_INVITE) {
            Assert.state(StringUtils.hasText(invite) && invite.length() >= 8, ResponseHandler.INVITE_CODE_ILLEGAL);
        }
    }

    /**
     * 是否为用户分享
     */
    public boolean isUserShare() {
        return channel.activatedInv() && channel == RegisterChannel.USER_INVITE;
    }

    /**
     * 是否为渠道分享
     */
    public boolean isChannelShare() {
        return channel.activatedInv() && channel == RegisterChannel.CHANNEL_INVITE;
    }
}
