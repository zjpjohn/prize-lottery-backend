package com.prize.lottery.pay;

import com.prize.lottery.error.PayRequestException;
import com.prize.lottery.props.WxPayProperties;
import com.wechat.pay.java.core.exception.ServiceException;
import com.wechat.pay.java.core.exception.WechatPayException;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.core.notification.RequestParam;
import com.wechat.pay.java.core.util.IOUtil;
import com.wechat.pay.java.service.payments.app.AppServiceExtension;
import com.wechat.pay.java.service.payments.app.model.*;
import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.payments.model.TransactionAmount;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class WxUnionPayExecutor implements UnionPayExecutor {

    public static final String SIGNATURE_HEADER      = "Wechatpay-Signature";
    public static final String NONCE_HEADER          = "Wechatpay-Nonce";
    public static final String TIMESTAMP_HEADER      = "Wechatpay-Timestamp";
    public static final String SERIAL_HEADER         = "Wechatpay-Serial";
    public static final String SIGNATURE_TYPE_HEADER = "Wechatpay-Signature-Type";
    public static final String ORDER_NOT_EXIST       = "ORDER_NOT_EXISTS";

    private final WxPayProperties     properties;
    private final AppServiceExtension service;
    private final NotificationParser  parser;

    public WxUnionPayExecutor(WxPayProperties properties, AppServiceExtension service, NotificationParser parser) {
        if (StringUtils.isBlank(properties.getNotifyUrl())) {
            throw new IllegalArgumentException("wx pay notify url must not be null.");
        }
        this.properties = properties;
        this.service    = service;
        this.parser     = parser;
    }

    @Override
    public PayChannel bizIndex() {
        return PayChannel.WX_PAY;
    }

    @Override
    public UnionOrder execute(UnionPayRequest request) {
        PrepayRequest prepayRequest = new PrepayRequest();
        prepayRequest.setOutTradeNo(request.getOrderNo());
        prepayRequest.setAppid(properties.getAppId());
        prepayRequest.setMchid(properties.getMerchantId());
        prepayRequest.setNotifyUrl(properties.getNotifyUrl());
        prepayRequest.setTimeExpire(this.format(request.getExpireTime()));
        Amount amount = new Amount();
        amount.setCurrency("CNY");
        amount.setTotal(request.getAmount().intValue());
        prepayRequest.setAmount(amount);
        PrepayWithRequestPaymentResponse response;
        try {
            response = service.prepayWithRequestPayment(prepayRequest);
        } catch (ServiceException error) {
            throw new PayRequestException(PayChannel.WX_PAY.getChannel(), error.getErrorCode(), error.getErrorMessage());
        } catch (WechatPayException error) {
            throw new PayRequestException(PayChannel.WX_PAY.getChannel(), "SYSTEM_ERROR", error.getMessage());
        }
        UnionOrder unionOrder = new UnionOrder();
        unionOrder.setChannel(PayChannel.WX_PAY);
        unionOrder.setAppId(response.getAppid());
        unionOrder.setPartnerId(response.getPartnerId());
        unionOrder.setPrepayId(response.getPrepayId());
        unionOrder.setTimestamp(response.getTimestamp());
        unionOrder.setSign(response.getSign());
        unionOrder.setPackValue(response.getPackageVal());
        unionOrder.setNonceStr(response.getNonceStr());
        return unionOrder;
    }

    @Override
    public NotifyResult notify(HttpServletRequest request) {
        try {
            RequestParam param = new RequestParam.Builder().serialNumber(request.getHeader(SERIAL_HEADER))
                                                           .nonce(request.getHeader(NONCE_HEADER))
                                                           .signature(request.getHeader(SIGNATURE_HEADER))
                                                           .timestamp(request.getHeader(TIMESTAMP_HEADER))
                                                           .signType(request.getHeader(SIGNATURE_TYPE_HEADER))
                                                           .body(IOUtil.toString(request.getInputStream()))
                                                           .build();
            Transaction  transaction = parser.parse(param, Transaction.class);
            NotifyResult result      = new NotifyResult();
            result.setChannel(PayChannel.WX_PAY);
            result.setAppId(transaction.getAppid());
            result.setOrderNo(transaction.getOutTradeNo());
            TransactionAmount amount = transaction.getAmount();
            result.setTotal(amount.getTotal().longValue());
            result.setPayTotal(amount.getPayerTotal().longValue());
            Transaction.TradeStateEnum tradeState = transaction.getTradeState();
            if (tradeState == Transaction.TradeStateEnum.SUCCESS) {
                result.setState(TradeState.SUCCESS);
                result.setPayTime(this.parse(transaction.getSuccessTime()));
            } else if (tradeState == Transaction.TradeStateEnum.CLOSED) {
                result.setState(TradeState.CLOSED);
                result.setCloseTime(this.parse(transaction.getSuccessTime()));
            }
            return result;
        } catch (Exception error) {
            log.error(error.getMessage(), error);
        }
        return null;
    }

    @Override
    public OrderResult query(String orderNo, String mchId) {
        QueryOrderByOutTradeNoRequest request = new QueryOrderByOutTradeNoRequest();
        request.setMchid(mchId);
        request.setOutTradeNo(orderNo);
        try {
            Transaction transaction = service.queryOrderByOutTradeNo(request);
            OrderResult result      = new OrderResult();
            result.setOrderNo(transaction.getOutTradeNo());
            result.setTradeNo(transaction.getTransactionId());
            result.setState(convert(transaction.getTradeState()));
            result.setAmount(transaction.getAmount().getPayerTotal().longValue());
            result.setPayerId(transaction.getPayer().getOpenid());
            String successTime = transaction.getSuccessTime();
            if (StringUtils.isNotBlank(successTime)) {
                result.setPayTime(parse(successTime));
            }
            return result;
        } catch (ServiceException error) {
            if (!ORDER_NOT_EXIST.equals(error.getErrorCode())) {
                throw new PayRequestException(PayChannel.WX_PAY.getChannel(), error.getErrorCode(), error.getErrorMessage());
            }
            log.warn("微信支付端订单[{}]不存在,错误码[{}]", orderNo, error.getErrorCode());
            return null;
        } catch (WechatPayException error) {
            throw new PayRequestException(PayChannel.WX_PAY.getChannel(), "SYSTEM_ERROR", error.getMessage());
        }
    }

    @Override
    public CloseResult close(String orderNo, String mchId) {
        CloseResult result = new CloseResult();
        result.setOrderNo(orderNo);
        CloseOrderRequest request = new CloseOrderRequest();
        request.setMchid(mchId);
        request.setOutTradeNo(orderNo);
        try {
            service.closeOrder(request);
            result.setResult(true);
        } catch (Exception error) {
            result.setResult(false);
            log.error("关闭微信支付订单异常:", error);
        }
        return result;
    }

    private TradeState convert(Transaction.TradeStateEnum state) {
        if (state == Transaction.TradeStateEnum.NOTPAY) {
            return TradeState.WAIT_PAY;
        }
        if (state == Transaction.TradeStateEnum.SUCCESS) {
            return TradeState.SUCCESS;
        }
        if (state == Transaction.TradeStateEnum.CLOSED) {
            return TradeState.CLOSED;
        }
        return null;
    }

    private String format(LocalDateTime time) {
        return time.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    private LocalDateTime parse(String time) {
        return LocalDateTime.parse(time, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}
