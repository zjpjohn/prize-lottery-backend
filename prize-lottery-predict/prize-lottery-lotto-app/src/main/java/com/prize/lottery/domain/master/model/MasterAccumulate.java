package com.prize.lottery.domain.master.model;

import lombok.Data;

@Data
public class MasterAccumulate {

    private String  masterId;
    private Integer browse;
    private Integer subscribe;
    private Integer searches;
    private Integer focus;

    public MasterAccumulate(String masterId) {
        this.masterId = masterId;
    }

    public MasterAccumulate browse() {
        this.browse = 1;
        return this;
    }

    public MasterAccumulate subscribe() {
        this.subscribe = 1;
        return this;
    }

    public MasterAccumulate unSubscribe() {
        this.subscribe = -1;
        return this;
    }

    public MasterAccumulate searches() {
        this.searches = 1;
        return this;
    }

    public MasterAccumulate focus() {
        this.focus = 1;
        return this;
    }

    public MasterAccumulate unFocus() {
        this.focus = -1;
        return this;
    }
}
