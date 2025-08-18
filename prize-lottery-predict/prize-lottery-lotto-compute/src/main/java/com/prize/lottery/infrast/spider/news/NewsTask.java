package com.prize.lottery.infrast.spider.news;

import com.prize.lottery.delay.DelayTask;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewsTask implements DelayTask {
    //文章标题
    private String        title;
    //文章详细链接
    private String        url;
    //文章图片
    private String        img;
    //执行时间
    private Long          timestamp;
    //新闻创建时间
    private LocalDateTime createTime;

    @Override
    public Long timestamp() {
        return this.timestamp;
    }

}
