package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.domain.order.model.aggregate.OrderInfoDo;
import com.prize.lottery.domain.order.repository.IOrderInfoRepository;
import com.prize.lottery.infrast.persist.mapper.OrderInfoMapper;
import com.prize.lottery.infrast.persist.po.OrderInfoPo;
import com.prize.lottery.infrast.repository.converter.OrderInfoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderInfoRepository implements IOrderInfoRepository {

    private final OrderInfoMapper    mapper;
    private final OrderInfoConverter converter;

    @Override
    public void save(Aggregate<Long, OrderInfoDo> aggregate) {
        OrderInfoDo root = aggregate.getRoot();
        if (root.isNew()) {
            OrderInfoPo orderInfo = converter.toPo(root);
            mapper.addOrderInfo(orderInfo);
            return;
        }
        //更新订单信息
        aggregate.ifChanged().map(converter::toPo).ifPresent(mapper::editOrderInfo);
    }

    @Override
    public Aggregate<Long, OrderInfoDo> ofNo(String orderNo) {
        return Optional.ofNullable(mapper.getOrderInfoByNo(orderNo))
                       .map(converter::toDo)
                       .map(AggregateFactory::create)
                       .orElse(null);
    }

    @Override
    public List<OrderInfoDo> expireOrders() {
        List<OrderInfoPo> orders = mapper.getExpiredWaitOrders();
        return converter.toList(orders);
    }
}
