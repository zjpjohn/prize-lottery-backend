package com.prize.lottery.transfer;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.error.PayRequestException;
import com.prize.lottery.pay.PayChannel;
import com.prize.lottery.props.WxPayProperties;
import com.wechat.pay.java.core.exception.ServiceException;
import com.wechat.pay.java.core.exception.WechatPayException;
import com.wechat.pay.java.service.transferbatch.TransferBatchService;
import com.wechat.pay.java.service.transferbatch.model.*;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class WxPayFundTransferExecutor implements FundTransferExecutor {

    private final PayChannel           channel;
    private final WxPayProperties      config;
    private final TransferBatchService transferService;

    public WxPayFundTransferExecutor(WxPayProperties config, TransferBatchService transferService) {
        this.config          = config;
        this.transferService = transferService;
        this.channel         = PayChannel.WX_PAY;
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
        Assert.state(!CollectionUtils.isEmpty(items), "批量转账内容为空");

        InitiateBatchTransferRequest transferRequest = new InitiateBatchTransferRequest();
        transferRequest.setAppid(config.getAppId());
        transferRequest.setOutBatchNo(request.getBatchNo());
        transferRequest.setBatchName(request.getBatchName());
        transferRequest.setBatchRemark(request.getRemark());
        transferRequest.setTotalAmount(request.getTotal());
        transferRequest.setTotalNum(items.size());

        List<TransferDetailInput> inputList = items.stream().map(e -> {
            TransferDetailInput input = new TransferDetailInput();
            input.setOpenid(e.getTransId());
            input.setOutDetailNo(e.getTransNo());
            input.setTransferRemark(e.getRemark());
            input.setTransferAmount(e.getAmount());
            return input;
        }).collect(Collectors.toList());
        transferRequest.setTransferDetailList(inputList);
        InitiateBatchTransferResponse response;
        try {
            response = transferService.initiateBatchTransfer(transferRequest);
        } catch (ServiceException error) {
            throw new PayRequestException(channel.getChannel(), error.getErrorCode(), error.getErrorMessage());
        } catch (WechatPayException error) {
            throw new PayRequestException(channel.getChannel(), "SYSTEM_ERROR", error.getMessage());
        }
        TransferResponse transferResponse = new TransferResponse();
        transferResponse.setTransNo(response.getOutBatchNo());
        transferResponse.setOrderId(response.getBatchId());
        transferResponse.setState(response.getBatchStatus());
        LocalDateTime transTime = this.parse(response.getCreateTime());
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
        GetTransferDetailByOutNoRequest detailRequest = new GetTransferDetailByOutNoRequest();
        detailRequest.setOutBatchNo(request.getBatchNo());
        detailRequest.setOutDetailNo(request.getTransNo());

        TransferDetailEntity response;
        try {
            response = transferService.getTransferDetailByOutNo(detailRequest);
        } catch (ServiceException error) {
            throw new PayRequestException(channel.getChannel(), error.getErrorCode(), error.getErrorMessage());
        } catch (WechatPayException error) {
            throw new PayRequestException(channel.getChannel(), "SYSTEM_ERROR", error.getMessage());
        }

        TransDetailResponse result = new TransDetailResponse();
        result.setTransNo(response.getOutDetailNo());
        result.setLatestTime(this.parse(response.getUpdateTime()));
        result.setState(TransferState.of(response.getDetailStatus()));
        if (response.getFailReason() != null) {
            result.setFailReason(response.getFailReason().toString());
        }
        return result;
    }

    /**
     * 查询批量转账的批次信息
     * 注：批量转账-只针对微信转账
     *
     * @param request 查询批次请求
     */
    @Override
    public TransBatchResponse query(TransBatchRequest request) {
        GetTransferBatchByOutNoRequest batchRequest = new GetTransferBatchByOutNoRequest();
        batchRequest.setOutBatchNo(request.getBatchNo());
        batchRequest.setDetailStatus("ALL");
        batchRequest.setNeedQueryDetail(true);
        batchRequest.setLimit(request.getLimit());
        batchRequest.setOffset(request.getOffset());

        TransferBatchEntity response;
        try {
            response = this.transferService.getTransferBatchByOutNo(batchRequest);
        } catch (ServiceException error) {
            throw new PayRequestException(channel.getChannel(), error.getErrorCode(), error.getErrorMessage());
        } catch (WechatPayException error) {
            throw new PayRequestException(channel.getChannel(), "SYSTEM_ERROR", error.getMessage());
        }

        TransBatchResponse result = new TransBatchResponse();
        TransferBatchGet   batch  = response.getTransferBatch();
        result.setBatchNo(batch.getOutBatchNo());
        result.setState(TransBatchState.of(batch.getBatchStatus()));
        result.setLatestTime(this.parse(batch.getUpdateTime()));
        //转账关闭时，返回关闭原因
        if (TransBatchState.CLOSED.toString().equals(batch.getBatchStatus())) {
            result.setCloseReason(batch.getCloseReason().toString());
        }
        //转账完成时，返回成功、失败以及转账条目状态
        if (TransBatchState.FINISHED.toString().equals(batch.getBatchStatus())) {
            result.setSuccessAmount(batch.getSuccessAmount());
            result.setSuccessNum(batch.getSuccessNum());
            result.setFailAmount(batch.getFailAmount());
            result.setFailNum(batch.getFailNum());
            List<TransBatchResponse.TransferEntry> entries = response.getTransferDetailList().stream().map(e -> {
                TransBatchResponse.TransferEntry entry = new TransBatchResponse.TransferEntry();
                entry.setTransNo(e.getOutDetailNo());
                entry.setState(TransferState.of(e.getDetailStatus()));
                return entry;
            }).collect(Collectors.toList());
            result.setItems(entries);
        }
        return result;
    }

    private LocalDateTime parse(String timeStr) {
        return LocalDateTime.parse(timeStr, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

}
