package com.prize.lottery.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class UserInfoRepo implements Serializable {

    private static final long serialVersionUID = -6460591769320377697L;

    //用户标识
    private Long    userId;
    //用户名称
    private String  nickname;
    //用户手机号
    private String  phone;
    //用户状态
    private Integer state;
    //用户是否已经成为专家
    private Integer expert;
    //是否是会员
    private boolean member;
}
