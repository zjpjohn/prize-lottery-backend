package com.prize.lottery.domain.value;

import lombok.Getter;

@Getter
public class Identity {

    private final Long id;

    public Identity(Long id) {
        this.id = id;
    }

}
