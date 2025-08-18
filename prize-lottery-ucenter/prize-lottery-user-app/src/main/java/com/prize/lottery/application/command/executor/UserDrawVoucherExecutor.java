package com.prize.lottery.application.command.executor;

import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.dto.DrawVoucherCmd;
import com.prize.lottery.application.command.vo.DrawVoucherVo;
import com.prize.lottery.domain.user.model.UserBalance;
import com.prize.lottery.domain.user.repository.IUserBalanceRepository;
import com.prize.lottery.domain.voucher.model.aggregate.BatchUserVoucherDo;
import com.prize.lottery.domain.voucher.model.aggregate.VoucherInfoDo;
import com.prize.lottery.domain.voucher.model.entity.UserVoucher;
import com.prize.lottery.domain.voucher.repository.IUserVoucherRepository;
import com.prize.lottery.domain.voucher.repository.IVoucherInfoRepository;
import com.prize.lottery.domain.voucher.valobj.DrawVoucherObj;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDrawVoucherExecutor {

    private final IVoucherInfoRepository voucherRepository;
    private final IUserVoucherRepository userVoucherRepository;
    private final IUserBalanceRepository balanceRepository;

    /**
     * 用户领券计算
     *
     * @param command 领券信息
     */
    public DrawVoucherVo execute(DrawVoucherCmd command) {
        //代金券信息
        VoucherInfoDo voucher = voucherRepository.ofNo(command.getSeqNo());
        //上一次领券时间
        LocalDateTime lastDraw = userVoucherRepository.lastDraw(command.getUserId(), command.getSeqNo());
        //代金券领券校验
        boolean canDraw = voucher.canDraw(lastDraw);
        Assert.state(canDraw, ResponseHandler.VOUCHER_CAN_NOT_DRAW);

        //领取代金券
        BatchUserVoucherDo batchUserVoucher = new BatchUserVoucherDo();
        DrawVoucherObj     drawVoucher      = new DrawVoucherObj(command.getSeqNo(), voucher.getVoucher(), voucher.getExpire());
        batchUserVoucher.drawVoucher(command.getUserId(), drawVoucher);
        AggregateFactory.create(batchUserVoucher).save(userVoucherRepository::save);

        //余额账户信息
        UserBalance userBalance = balanceRepository.ofId(command.getUserId())
                                                   .orElseThrow(Assert.supply(ResponseHandler.ACCOUNT_NONE_EXISTED));
        //账户余额计算
        userBalance.drawVoucher(voucher.getVoucher());
        balanceRepository.save(userBalance);

        //领取代金券结果
        DrawVoucherVo drawResult = new DrawVoucherVo();
        drawResult.setSeqNo(command.getSeqNo());
        drawResult.setDisposable(voucher.getDisposable());
        UserVoucher userVoucher = batchUserVoucher.getVouchers().get(0);
        drawResult.setVoucher(userVoucher.getVoucher());
        drawResult.setExpireAt(userVoucher.getExpireAt());
        //可重复领取，下一次领取时间
        if (voucher.getDisposable() == 0) {
            LocalDateTime nextTime = userVoucher.nextDraw(voucher.getInterval());
            drawResult.setNextTime(nextTime);
        }
        return drawResult;
    }
}
