package com.prize.lottery.infrast.spider.skill;

import com.prize.lottery.infrast.spider.skill.domain.DetailRequestBody;
import com.prize.lottery.infrast.spider.skill.domain.RequestHeader;
import lombok.Data;

@Data
public class DetailRequest {

    private RequestHeader     header;
    private DetailRequestBody body;

    public DetailRequest(String newsId) {
        this.header = new RequestHeader(1003);
        this.body   = new DetailRequestBody(newsId);
    }

}
