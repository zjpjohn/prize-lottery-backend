package com.prize.lottery.infrast.external.push.request;

import lombok.Data;

import java.util.List;

@Data
public class TagBasedBindDto {

    private Long         appKey;
    private List<String> devices;
    private String       tagName;

}
