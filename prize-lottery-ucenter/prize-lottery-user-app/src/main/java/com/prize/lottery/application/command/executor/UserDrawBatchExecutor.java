package com.prize.lottery.application.command.executor;

import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Maps;
import com.prize.lottery.application.command.dto.DrawBatchVoucherCmd;
import com.prize.lottery.application.command.vo.DrawVoucherVo;
import com.prize.lottery.domain.user.model.UserBalance;
import com.prize.lottery.domain.user.repository.IUserBalanceRepository;
import com.prize.lottery.domain.voucher.model.aggregate.BatchUserVoucherDo;
import com.prize.lottery.domain.voucher.model.aggregate.VoucherInfoDo;
import com.prize.lottery.domain.voucher.repository.IUserVoucherRepository;
import com.prize.lottery.domain.voucher.repository.IVoucherInfoRepository;
import com.prize.lottery.domain.voucher.valobj.DrawVoucherObj;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDrawBatchExecutor {

    private final IVoucherInfoRepository voucherRepository;
    private final IUserVoucherRepository drawRepository;
    private final IUserBalanceRepository balanceRepository;

    /**
     * 批量领取代金券
     */
    public List<DrawVoucherVo> execute(DrawBatchVoucherCmd command) {
        //代金券信息
        List<VoucherInfoDo> vouchers = voucherRepository.ofNoList(command.getSeqNos());
        Assert.state(!CollectionUtils.isEmpty(vouchers), ResponseHandler.VOUCHER_NOT_EXIST);

        //代金券过滤校验
        Map<String, LocalDateTime> lastDrawMap = drawRepository.lastDraws(command.getUserId(), command.getSeqNos());
        List<VoucherInfoDo> voucherList = vouchers.stream().filter(voucher -> {
            LocalDateTime lastDraw = lastDrawMap.get(voucher.getSeqNo());
            return voucher.canDraw(lastDraw);
        }).collect(Collectors.toList());
        Assert.state(!CollectionUtils.isEmpty(voucherList), ResponseHandler.VOUCHER_CAN_NOT_DRAW);
        List<DrawVoucherObj> drawVouchers = voucherList.stream().map(DrawVoucherObj::new).collect(Collectors.toList());
        //批量领取代金券
        BatchUserVoucherDo batchUserVoucher = new BatchUserVoucherDo();
        batchUserVoucher.batchDraw(command.getUserId(), drawVouchers);
        AggregateFactory.create(batchUserVoucher).save(drawRepository::save);

        //账户余额信息
        UserBalance userBalance = balanceRepository.ofId(command.getUserId())
                                                   .orElseThrow(Assert.supply(ResponseHandler.ACCOUNT_NONE_EXISTED));
        int voucher = voucherList.stream().mapToInt(VoucherInfoDo::getVoucher).sum();
        //代金券余额计算
        userBalance.drawVoucher(voucher);
        balanceRepository.save(userBalance);
        //返回批量领取结果
        Map<String, VoucherInfoDo> voucherMap = Maps.uniqueIndex(voucherList, VoucherInfoDo::getSeqNo);
        return batchUserVoucher.getVouchers().stream().map(userVoucher -> {
            DrawVoucherVo drawResult = new DrawVoucherVo();
            drawResult.setSeqNo(userVoucher.getBizNo());
            drawResult.setVoucher(userVoucher.getVoucher());
            drawResult.setExpireAt(userVoucher.getExpireAt());
            VoucherInfoDo voucherInfo = voucherMap.get(userVoucher.getBizNo());
            if (voucherInfo != null) {
                drawResult.setDisposable(voucherInfo.getDisposable());
                if (voucherInfo.getDisposable() == 0) {
                    LocalDateTime nextTime = userVoucher.nextDraw(voucherInfo.getInterval());
                    drawResult.setNextTime(nextTime);
                }
            }
            return drawResult;
        }).collect(Collectors.toList());
    }

}
