package com.prize.lottery.domain.app.model;

import com.cloud.arch.aggregate.AggregateRoot;
import lombok.Data;

@Data
public class AppDepictionDo implements AggregateRoot<Long> {

    private static final long serialVersionUID = 2081084618609459441L;

    private Long   id;
    private String appNo;
    private String appVer;
    private String depiction;


}
