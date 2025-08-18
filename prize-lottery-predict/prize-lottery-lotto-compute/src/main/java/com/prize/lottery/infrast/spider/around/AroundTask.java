package com.prize.lottery.infrast.spider.around;

import com.prize.lottery.delay.DelayTask;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AroundTask implements DelayTask {

    private Long   timestamp;
    private String period;

    public AroundTask(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public Long timestamp() {
        return this.timestamp;
    }

}
