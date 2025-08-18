package com.prize.lottery.domain.order.repository;

import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.order.model.aggregate.OrderInfoDo;

import java.util.List;

public interface IOrderInfoRepository {

    void save(Aggregate<Long, OrderInfoDo> aggregate);

    Aggregate<Long, OrderInfoDo> ofNo(String orderNo);

    List<OrderInfoDo> expireOrders();

}
