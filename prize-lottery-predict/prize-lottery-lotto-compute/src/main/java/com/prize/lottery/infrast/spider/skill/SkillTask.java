package com.prize.lottery.infrast.spider.skill;

import com.alibaba.fastjson2.JSONObject;
import com.cloud.arch.utils.JsonUtils;
import com.google.common.hash.Hashing;
import com.prize.lottery.delay.DelayTask;
import com.prize.lottery.enums.LotteryEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
public class SkillTask implements DelayTask {

    private DetailRequest request;
    private String        author;
    private String        title;
    private String        header;
    private LotteryEnum   type;
    private Integer       browse;
    private Long          timestamp;
    private LocalDateTime createTime;

    public SkillTask(JSONObject node, LotteryEnum type, Long timestamp) {
        this.type      = type;
        this.timestamp = timestamp;
        this.title     = node.getString("title").trim();
        this.author    = node.getString("authorName").trim();
        this.header    = node.getString("cover").trim();
        this.browse    = this.parseBrowse(node);
        this.request   = new DetailRequest(node.getString("id"));
        long createTimestamp = Long.parseLong(node.getString("timeStamp"));
        this.createTime = Instant.ofEpochMilli(createTimestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();

    }

    public String getRequestSha() {
        return Hashing.sha256().hashString(request.getBody().getNewsId(), StandardCharsets.UTF_8).toString();
    }

    private Integer parseBrowse(JSONObject node) {
        String readNum = node.getString("readNum");
        if (StringUtils.isBlank(readNum)) {
            return 0;
        }
        return Integer.parseInt(readNum);
    }

    /**
     * 请求json信息
     */
    public String jsonBody() {
        return JsonUtils.toJson(request);
    }

    @Override
    public Long timestamp() {
        return this.timestamp;
    }

}
