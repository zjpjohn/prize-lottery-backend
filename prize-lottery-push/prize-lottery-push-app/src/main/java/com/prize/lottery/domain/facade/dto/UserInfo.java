package com.prize.lottery.domain.facade.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserInfo {

    private Long          userId;
    private String        phone;
    private List<Integer> scopes;

}
