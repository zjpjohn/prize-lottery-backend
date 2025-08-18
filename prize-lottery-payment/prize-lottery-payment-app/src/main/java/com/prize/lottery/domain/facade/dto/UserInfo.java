package com.prize.lottery.domain.facade.dto;

import lombok.Data;

@Data
public class UserInfo {

    private Long    userId;
    private String  phone;
    private String  nickname;
    private Integer state;

}
