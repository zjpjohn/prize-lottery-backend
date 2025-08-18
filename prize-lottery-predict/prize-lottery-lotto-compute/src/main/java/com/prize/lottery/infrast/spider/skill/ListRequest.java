package com.prize.lottery.infrast.spider.skill;

import com.prize.lottery.infrast.spider.skill.domain.ListRequestBody;
import com.prize.lottery.infrast.spider.skill.domain.RequestHeader;
import lombok.Data;

@Data
public class ListRequest {

    private RequestHeader   header;
    private ListRequestBody body;

    public ListRequest(String timestamp, Integer size, String labels) {
        this.header = new RequestHeader(13053);
        this.body   = new ListRequestBody(timestamp, size, labels);
    }

    public ListRequest(Integer size, String labels) {
        this.header = new RequestHeader(13053);
        this.body   = new ListRequestBody(size, labels);
    }
}
