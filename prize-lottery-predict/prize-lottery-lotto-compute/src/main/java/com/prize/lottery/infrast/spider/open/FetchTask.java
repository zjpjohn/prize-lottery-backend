package com.prize.lottery.infrast.spider.open;

import com.prize.lottery.delay.DelayTask;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FetchTask implements DelayTask {

    //专家标识
    private String masterId;
    //第三方来源标识
    private String sourceId;
    //预测数据期号
    private String period;
    //执行时间
    private Long   timestamp;

    @Override
    public Long timestamp() {
        return this.timestamp;
    }

}
