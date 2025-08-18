package com.prize.lottery.infrast.spider.open;

import com.prize.lottery.value.ICaiForecast;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collections;
import java.util.List;

public interface FetchEventHandler {

    /**
     * 处理获取的预测数据
     *
     * @param data 预测数据
     */
    void handle(ICaiForecast data);

    /**
     * 获取指定期号的专家信息
     *
     * @param period 指定期号
     */
    List<Pair<String, String>> masters(String period);

    /**
     * 增量专家集合
     */
    default List<Pair<String, String>> incrMasters(String period) {
        return Collections.emptyList();
    }

}
