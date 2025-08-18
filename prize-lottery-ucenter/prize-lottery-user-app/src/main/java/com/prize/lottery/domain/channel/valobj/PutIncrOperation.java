package com.prize.lottery.domain.channel.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PutIncrOperation {

    //分享邀请计数
    private final Integer value;

    public static PutIncrOperation count() {
        return new PutIncrOperation(1);
    }

}
