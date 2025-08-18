package com.prize.lottery.infrast.spider.skill.domain;

import lombok.Data;

@Data
public class ListRequestBody {

    private String  timestamp;
    private Integer size;
    private String  labels;

    public ListRequestBody(Integer size, String labels) {
        this("", size, labels);
    }

    public ListRequestBody(String timestamp, Integer size, String labels) {
        this.timestamp = timestamp;
        this.size      = size;
        this.labels    = labels;
    }

}
