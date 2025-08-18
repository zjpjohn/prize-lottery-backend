package com.prize.lottery.application.command.vo;

import com.prize.lottery.infrast.persist.enums.RegisterChannel;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserLoginResult {

    private String        token;
    private Long          expire;
    private LocalDateTime loginTime;
    private UserInfo      user;

    @Data
    public static class UserInfo {

        private String          uid;
        private String          nickname;
        private String          phone;
        private String          avatar;
        private String          code;
        private String          inviteUri;
        private Integer         expert  = 0;
        private RegisterChannel channel;
        private Boolean         wxBind  = false;
        private Boolean         aliBind = false;

    }
}
