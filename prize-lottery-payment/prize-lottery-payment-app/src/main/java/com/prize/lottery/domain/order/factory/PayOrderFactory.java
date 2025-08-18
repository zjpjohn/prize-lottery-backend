package com.prize.lottery.domain.order.factory;

import com.cloud.arch.utils.IdWorker;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.dto.ChargeOrderCreateCmd;
import com.prize.lottery.application.command.dto.MemberOrderCreateCmd;
import com.prize.lottery.domain.order.model.aggregate.ChargeConfDo;
import com.prize.lottery.domain.order.model.aggregate.OrderInfoDo;
import com.prize.lottery.domain.order.model.aggregate.PackInfoDo;
import com.prize.lottery.domain.order.model.valobj.OrderContent;
import com.prize.lottery.domain.order.repository.IChargeConfRepository;
import com.prize.lottery.domain.order.repository.IPackInfoRepository;
import com.prize.lottery.domain.transfer.model.aggregate.PayChannelDo;
import com.prize.lottery.domain.transfer.repository.IPayChannelRepository;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.OrderState;
import com.prize.lottery.infrast.persist.enums.OrderType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class PayOrderFactory {

    private final IPackInfoRepository   packRepository;
    private final IPayChannelRepository channelRepository;
    private final IChargeConfRepository confRepository;

    public OrderInfoDo chargeOrder(ChargeOrderCreateCmd command) {
        PayChannelDo payChannel = channelRepository.ofChannel(command.getChannel().getChannel());
        Assert.state(payChannel == null || !payChannel.canPay(), ResponseHandler.PAY_CHANNEL_UNAVAILABEL);

        ChargeConfDo config = confRepository.ofId(command.getConfId()).getRoot();
        Assert.state(config.isUsing(), ResponseHandler.CHARGE_CONF_UNAVAILABLE);

        //金币与人民币兑换比例1元=1000金币，订单价格以分为单位
        Long        price     = config.getAmount() / 10;
        OrderInfoDo orderInfo = new OrderInfoDo();
        orderInfo.setBizNo(String.valueOf(IdWorker.nextId()));
        orderInfo.setUserId(command.getUserId());
        orderInfo.setState(OrderState.WAIT_PAY);
        orderInfo.setChannel(command.getChannel());
        orderInfo.setType(OrderType.CHARGE);
        orderInfo.setQuantity(1);
        orderInfo.setStdPrice(price);
        orderInfo.setRealPrice(price);
        orderInfo.setAmount(price);
        orderInfo.setSettled(0);
        orderInfo.setRemark("账户金币充值");
        orderInfo.setAttach(config.getName());
        //订单内容
        Map<String, Object> content = this.chargeOrderContent(config);
        orderInfo.setContent(content);
        //订单支付有效时间30分钟
        LocalDateTime expireTime = LocalDateTime.now().plusMinutes(30);
        orderInfo.setExpireTime(expireTime);
        return orderInfo;
    }

    /**
     * 创建套餐订单
     */
    public OrderInfoDo memberOrder(MemberOrderCreateCmd command) {
        PayChannelDo payChannel = channelRepository.ofChannel(command.getChannel().getChannel());
        Assert.state(payChannel != null && payChannel.canPay(), ResponseHandler.PAY_CHANNEL_UNAVAILABEL);

        PackInfoDo packInfo = packRepository.ofPackNo(command.getPackNo())
                                            .orElseThrow(Assert.supply(ResponseHandler.PACK_NONE));
        Assert.state(packInfo.isIssued(), ResponseHandler.PACK_UN_AVAILABLE);

        OrderInfoDo orderInfo = new OrderInfoDo();
        orderInfo.setBizNo(String.valueOf(IdWorker.nextId()));
        orderInfo.setUserId(command.getUserId());
        orderInfo.setState(OrderState.WAIT_PAY);
        orderInfo.setChannel(command.getChannel());
        orderInfo.setType(OrderType.MEMBER);
        orderInfo.setQuantity(1);
        orderInfo.setStdPrice(packInfo.getPrice());
        orderInfo.setRealPrice(packInfo.getDiscount());
        orderInfo.setAmount(packInfo.getDiscount());
        orderInfo.setSettled(0);
        orderInfo.setRemark("会员去广告套餐");
        //订单附件信息
        String attach = this.memberAttach(packInfo);
        orderInfo.setAttach(attach);
        //订单内容
        Map<String, Object> content = this.memberOrderContent(packInfo);
        orderInfo.setContent(content);
        //订单支付有效时间30分钟
        LocalDateTime expireTime = LocalDateTime.now().plusMinutes(30);
        orderInfo.setExpireTime(expireTime);
        return orderInfo;

    }

    private Map<String, Object> chargeOrderContent(ChargeConfDo config) {
        OrderContent orderContent = OrderContent.charge(config.getAmount(), config.getGift());
        return orderContent.getContent();
    }

    private Map<String, Object> memberOrderContent(PackInfoDo packInfo) {
        OrderContent orderContent = OrderContent.member(packInfo.getSeqNo(), packInfo.getName(), packInfo.getTimeUnit()
                                                                                                         .getUnit());
        return orderContent.getContent();
    }

    private String memberAttach(PackInfoDo packInfo) {
        return packInfo.getName() + "套餐优惠" + (packInfo.getPrice() - packInfo.getDiscount()) / 100 + "元";
    }

}
