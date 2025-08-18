package com.prize.lottery.transfer;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayFundTransCommonQueryModel;
import com.alipay.api.domain.AlipayFundTransUniTransferModel;
import com.alipay.api.domain.Participant;
import com.alipay.api.request.AlipayFundTransCommonQueryRequest;
import com.alipay.api.request.AlipayFundTransUniTransferRequest;
import com.alipay.api.response.AlipayFundTransCommonQueryResponse;
import com.alipay.api.response.AlipayFundTransUniTransferResponse;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.error.PayRequestException;
import com.prize.lottery.pay.PayChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
public class AliPayFundTransferExecutor implements FundTransferExecutor {

    private final PayChannel   channel;
    private final AlipayClient alipayClient;

    public AliPayFundTransferExecutor(AlipayClient alipayClient) {
        this.alipayClient = alipayClient;
        this.channel      = PayChannel.ALI_PAY;
    }

    @Override
    public PayChannel bizIndex() {
        return this.channel;
    }

    /**
     * 转账操作
     *
     * @param request 转账请求
     */
    @Override
    public TransferResponse transfer(TransferRequest request) {
        List<TransferInfo> items = request.getItems();
        Assert.state(!CollectionUtils.isEmpty(items), "转账信息为空");

        TransferInfo transfer = items.get(0);
        double amount = BigDecimal.valueOf(transfer.getAmount())
                                  .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_DOWN)
                                  .setScale(2, RoundingMode.HALF_DOWN)
                                  .doubleValue();

        AlipayFundTransUniTransferModel model = new AlipayFundTransUniTransferModel();
        model.setRemark(transfer.getRemark());
        model.setOutBizNo(transfer.getTransNo());
        model.setTransAmount(String.valueOf(amount));
        model.setProductCode("TRANS_ACCOUNT_NO_PWD");
        model.setOrderTitle(transfer.getRemark());
        model.setBizScene("DIRECT_TRANSFER");
        model.setBusinessParams("{\"payer_show_name_use_alias\":\"true\"}");

        Participant payee = new Participant();
        payee.setIdentity(transfer.getTransId());
        payee.setIdentityType("ALIPAY_USER_ID");
        model.setPayeeInfo(payee);

        AlipayFundTransUniTransferRequest transferRequest = new AlipayFundTransUniTransferRequest();
        transferRequest.setBizModel(model);
        AlipayFundTransUniTransferResponse response;
        try {
            response = alipayClient.certificateExecute(transferRequest);
        } catch (AlipayApiException error) {
            throw new PayRequestException(channel.getChannel(), error.getErrCode(), error.getErrMsg());
        }
        if (!response.isSuccess()) {
            throw new PayRequestException(channel.getChannel(), response.getSubCode(), response.getSubMsg());
        }
        TransferResponse transferResponse = new TransferResponse();
        transferResponse.setTransNo(response.getOutBizNo());
        transferResponse.setOrderId(response.getOrderId());
        transferResponse.setState(response.getStatus());
        LocalDateTime transTime = this.parse(response.getTransDate());
        transferResponse.setTransTime(transTime);
        return transferResponse;
    }

    /**
     * 查询转账业务单据
     *
     * @param request 查询请求
     */
    @Override
    public TransDetailResponse query(TransDetailRequest request) {
        AlipayFundTransCommonQueryModel model = new AlipayFundTransCommonQueryModel();
        model.setOutBizNo(request.getTransNo());
        model.setBizScene("DIRECT_TRANSFER");
        model.setProductCode("TRANS_ACCOUNT_NO_PWD");
        AlipayFundTransCommonQueryRequest queryRequest = new AlipayFundTransCommonQueryRequest();
        queryRequest.setBizModel(model);

        AlipayFundTransCommonQueryResponse response;
        try {
            response = alipayClient.execute(queryRequest);
        } catch (AlipayApiException error) {
            throw new PayRequestException(channel.getChannel(), error.getErrCode(), error.getErrMsg());
        }
        TransDetailResponse result = new TransDetailResponse();
        result.setTransNo(response.getOutBizNo());
        result.setFailReason(response.getFailReason());
        result.setLatestTime(this.parse(response.getPayDate()));
        result.setState(TransferState.of(response.getStatus()));
        return result;
    }

    private LocalDateTime parse(String timeStr) {
        return LocalDateTime.parse(timeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
