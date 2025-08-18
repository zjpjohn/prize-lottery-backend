package com.prize.lottery.application.command.executor;

import com.cloud.arch.aggregate.Aggregate;
import com.google.common.collect.Lists;
import com.prize.lottery.domain.user.model.UserBalance;
import com.prize.lottery.domain.user.repository.IUserBalanceRepository;
import com.prize.lottery.domain.voucher.model.aggregate.BatchUserVoucherDo;
import com.prize.lottery.domain.voucher.repository.IUserVoucherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserVoucherExpireExecutor {

    private final IUserVoucherRepository userVoucherRepository;
    private final IUserBalanceRepository userBalanceRepository;

    /**
     * 账户代金券过期计算
     *
     * @param limit 计算代金券数量
     */
    public Integer execute(Integer limit) {
        Aggregate<Long, BatchUserVoucherDo> aggregate   = userVoucherRepository.ofExpired(limit);
        BatchUserVoucherDo                  userVoucher = aggregate.getRoot();
        if (userVoucher.isEmpty()) {
            return 0;
        }
        //已领取的代金券过期计算
        Map<Long, Integer> expireVouchers = userVoucher.expireVouchers();
        userVoucherRepository.save(aggregate);
        //代金券账户余额操作
        List<Long>        userIds      = Lists.newArrayList(expireVouchers.keySet());
        List<UserBalance> userBalances = userBalanceRepository.ofUsers(userIds);
        //代金券过期，回退账户过期的代金券
        userBalances.forEach(balance -> {
            Integer voucher = expireVouchers.get(balance.getUserId());
            balance.expireVoucher(voucher);
        });
        userBalanceRepository.saveBatch(userBalances);
        return userVoucher.size();
    }

}
