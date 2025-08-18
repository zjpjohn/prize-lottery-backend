package com.prize.lottery.pay;

import com.alibaba.fastjson2.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.prize.lottery.error.PayRequestException;
import com.prize.lottery.props.AliPayProperties;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class AliUnionPayExecutor implements UnionPayExecutor {

    public static final String TRADE_CLOSED    = "TRADE_CLOSED";
    public static final String TRADE_SUCCESS   = "TRADE_SUCCESS";
    public static final String TRADE_FINISHED  = "TRADE_FINISHED";
    public static final String WAIT_BUYER_PAY  = "WAIT_BUYER_PAY";
    public static final String TRADE_NOT_EXIST = "ACQ.TRADE_NOT_EXIST";

    private final AliPayProperties properties;
    private final AlipayClient     alipayClient;

    public AliUnionPayExecutor(AliPayProperties properties, AlipayClient alipayClient) {
        if (StringUtils.isBlank(properties.getNotifyUrl())) {
            throw new IllegalArgumentException("alipay notify url must not be null.");
        }
        this.properties   = properties;
        this.alipayClient = alipayClient;
    }

    @Override
    public PayChannel bizIndex() {
        return PayChannel.ALI_PAY;
    }

    @Override
    public UnionOrder execute(UnionPayRequest request) {
        AlipayTradeAppPayModel payModel = new AlipayTradeAppPayModel();
        payModel.setBody(request.getDescription());
        payModel.setSubject(request.getSubject());
        payModel.setOutTradeNo(request.getOrderNo());
        String totalAmount =
            BigDecimal.valueOf(request.getAmount() / 100.0).setScale(2, RoundingMode.HALF_UP).toPlainString();
        payModel.setTotalAmount(totalAmount);
        payModel.setTimeExpire(this.format(request.getExpireTime()));
        payModel.setProductCode("QUICK_MSECURITY_PAY");
        AlipayTradeAppPayRequest payRequest = new AlipayTradeAppPayRequest();
        payRequest.setBizModel(payModel);
        payRequest.setNotifyUrl(properties.getNotifyUrl());
        AlipayTradeAppPayResponse response;
        try {
            response = alipayClient.sdkExecute(payRequest);
        } catch (AlipayApiException error) {
            throw new PayRequestException(PayChannel.ALI_PAY.getChannel(), error.getErrCode(), error.getErrMsg());
        }
        if (!response.isSuccess()) {
            throw new PayRequestException(PayChannel.ALI_PAY.getChannel(), response.getSubCode(), response.getSubMsg());
        }
        UnionOrder unionOrder = new UnionOrder();
        unionOrder.setChannel(PayChannel.ALI_PAY);
        unionOrder.setOrder(response.getBody());
        unionOrder.setPlatform(Platform.ANDROID.getPlatform());
        unionOrder.setEnvironment(properties.getSandbox());
        return unionOrder;
    }

    @Override
    public NotifyResult notify(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, String>   params       = Maps.newHashMap();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String value = String.join(",", entry.getValue());
            params.put(entry.getKey(), value);
        }
        if (CollectionUtils.isEmpty(params)) {
            log.warn("回调请求参数为空");
            return null;
        }
        try {
            boolean verified =
                AlipaySignature.rsaCheckV1(params, properties.getPublicKey(), properties.getCharset(), properties.getSignType());
            if (!verified) {
                log.warn("回调请求参数校验不通过");
                return null;
            }
            String appId = params.get("app_id");
            if (!properties.getAppId().equals(appId)) {
                log.warn("服务商appId不一致request[{}],required[{}]", appId, properties.getAppId());
                return null;
            }
            NotifyResult result = new NotifyResult();
            result.setChannel(PayChannel.ALI_PAY);
            result.setAppId(appId);
            result.setOrderNo(params.get("out_trade_no"));
            //订单金额
            double totalAmount = Double.parseDouble(params.get("total_amount")) * 100;
            result.setTotal(Double.valueOf(totalAmount).longValue());
            //实付金额
            double receiptAmount = Double.parseDouble(params.get("receipt_amount")) * 100;
            result.setPayTotal(Double.valueOf(receiptAmount).longValue());
            //订单状态
            String tradeStatus = params.get("trade_status");
            if (tradeStatus.equals(TRADE_SUCCESS) || tradeStatus.equals(TRADE_FINISHED)) {
                result.setState(TradeState.SUCCESS);
                LocalDateTime gmtPayment =
                    Optional.ofNullable(params.get("gmt_payment")).map(this::parse).orElseGet(LocalDateTime::now);
                result.setPayTime(gmtPayment);
            } else if (tradeStatus.equals(TRADE_CLOSED)) {
                result.setState(TradeState.CLOSED);
                LocalDateTime gmtClose =
                    Optional.ofNullable(params.get("gmt_close")).map(this::parse).orElseGet(LocalDateTime::now);
                result.setCloseTime(gmtClose);
            }
            return result;
        } catch (AlipayApiException error) {
            log.error(error.getErrMsg(), error);
        }
        return null;
    }

    @Override
    public OrderResult query(String orderNo, String mchId) {
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setOutTradeNo(orderNo);
        model.setQueryOptions(Lists.newArrayList("trade_settle_info"));
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizModel(model);
        AlipayTradeQueryResponse response;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException error) {
            log.error("查询支付宝订单异常:code[{}],message:{}", error.getErrCode(), error.getErrMsg());
            throw new PayRequestException(PayChannel.ALI_PAY.getChannel(), error.getErrCode(), error.getErrMsg());
        }
        if (!response.isSuccess()) {
            if (TRADE_NOT_EXIST.equals(response.getSubCode())) {
                log.warn("支付宝端订单[{}]不存在,错误码[{}]", orderNo, response.getSubCode());
                return null;
            }
            log.warn("支付宝端响应错误[{}]:{}", response.getSubCode(), response.getSubMsg());
            throw new PayRequestException(PayChannel.ALI_PAY.getChannel(), response.getSubCode(), response.getSubMsg());
        }
        OrderResult result = new OrderResult();
        result.setTradeNo(response.getTradeNo());
        result.setOrderNo(response.getOutTradeNo());
        result.setPayerId(response.getBuyerOpenId());
        result.setState(convert(response.getTradeStatus()));
        result.setAmount((long)(Double.parseDouble(response.getTotalAmount()) * 100));
        Date payDate = response.getSendPayDate();
        if (payDate != null) {
            result.setPayTime(LocalDateTime.ofInstant(payDate.toInstant(), ZoneId.systemDefault()));
        }
        return result;
    }

    @Override
    public CloseResult close(String orderNo, String mchId) {
        AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
        request.setBizContent(JSONObject.of("out_trade_no", orderNo).toString());
        AlipayTradeCloseResponse response;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException error) {
            throw new PayRequestException(PayChannel.ALI_PAY.getChannel(), error.getErrCode(), error.getErrMsg());
        }
        CloseResult result = new CloseResult();
        result.setOrderNo(orderNo);
        result.setResult(response.isSuccess());
        return result;
    }

    private TradeState convert(String status) {
        if (Objects.equals(status, TRADE_SUCCESS)) {
            return TradeState.SUCCESS;
        }
        if (Objects.equals(status, TRADE_CLOSED)) {
            return TradeState.CLOSED;
        }
        if (Objects.equals(status, WAIT_BUYER_PAY)) {
            return TradeState.WAIT_PAY;
        }
        return null;
    }

    private String format(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private LocalDateTime parse(String time) {
        return LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
