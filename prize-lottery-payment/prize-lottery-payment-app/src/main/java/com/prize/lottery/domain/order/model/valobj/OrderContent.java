package com.prize.lottery.domain.order.model.valobj;

import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.Map;

public class OrderContent {

    private final Map<String, Object> content;

    public OrderContent(Map<String, Object> content) {
        this.content = content;
    }

    public Object getValue(String key) {
        return this.content.get(key);
    }

    public Map<String, Object> getContent() {
        return Collections.unmodifiableMap(content);
    }

    /**
     * 充值订单内容
     *
     * @param amount 充值金币数量
     * @param gift   系统赠送数量
     */
    public static OrderContent charge(Long amount, Long gift) {
        Map<String, Object> content = Maps.newHashMap();
        content.put("name", "金币");
        content.put("amount", amount);
        content.put("gift", gift);
        return new OrderContent(content);
    }

    /**
     * 会员套餐订单内容
     *
     * @param packNo 会员套餐编号
     * @param name   套餐名称
     * @param unit   套餐时间单位
     */
    public static OrderContent member(String packNo, String name, Integer unit) {
        Map<String, Object> content = Maps.newHashMap();
        content.put("name", name);
        content.put("packNo", packNo);
        content.put("timeUnit", unit);
        return new OrderContent(content);
    }

}
