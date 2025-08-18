package com.prize.lottery.infrast.persist.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.prize.lottery.infrast.persist.enums.RegisterChannel;
import com.prize.lottery.infrast.persist.enums.UserState;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserInfoPo {

    private Long            id;
    //用户昵称
    private String          nickname;
    //用户手机号
    private String          phone;
    //账户登录密码
    @JsonIgnore
    private String          password;
    //绑定微信标识
    private String          wxId;
    //支付宝账户id
    private String          aliId;
    //邮箱地址
    private String          email;
    //用户头像
    private String          avatar;
    //是否开通专家
    private Integer         expert;
    //注册来源渠道
    private RegisterChannel channel;
    //用户状态:0-无效,1-正常,2-锁定
    private UserState       state;
    //数据版本
    private Integer         version;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm", timezone = "GMT+8")
    private LocalDateTime   gmtCreate;
    private LocalDateTime   gmtModify;

}
