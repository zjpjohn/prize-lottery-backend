package com.prize.lottery.infrast.spider.skill.domain;

import lombok.Data;

@Data
public class DetailRequestBody {

    private String newsId;
    private String spInfoType = "";

    public DetailRequestBody(String newsId) {
        this.newsId = newsId;
    }
}
