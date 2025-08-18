package com.prize.lottery;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class AuthSourceProperty {

    private String       identity;
    private String       domain;
    private String       description;
    private List<String> retains = Lists.newArrayList();

}
