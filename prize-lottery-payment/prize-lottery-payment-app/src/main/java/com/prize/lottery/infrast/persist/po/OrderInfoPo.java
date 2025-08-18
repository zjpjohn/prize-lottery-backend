package com.prize.lottery.infrast.persist.po;

import com.prize.lottery.infrast.persist.enums.OrderState;
import com.prize.lottery.infrast.persist.enums.OrderType;
import com.prize.lottery.pay.PayChannel;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class OrderInfoPo {

    private Long                id;
    //订单编号
    private String              bizNo;
    //用户标识
    private Long                userId;
    //套餐标识
    private OrderType           type;
    //标准价格
    private Long                stdPrice;
    //实际价格
    private Long                realPrice;
    //套餐数量
    private Integer             quantity;
    //实际支付金额
    private Long                amount;
    //支付渠道
    private PayChannel          channel;
    //订单备注信息
    private String              remark;
    //订单附加信息
    private String              attach;
    //订单内容
    private Map<String, Object> content;
    //订单状态
    private OrderState          state;
    //是否已对账
    private Integer             settled;
    //订单支付到期时间
    private LocalDateTime       expireTime;
    //订单支付时间
    private LocalDateTime       payTime;
    //订单关闭时间
    private LocalDateTime       closeTime;
    //创建时间
    private LocalDateTime       gmtCreate;
    //最近更新时间
    private LocalDateTime       gmtModify;

}
