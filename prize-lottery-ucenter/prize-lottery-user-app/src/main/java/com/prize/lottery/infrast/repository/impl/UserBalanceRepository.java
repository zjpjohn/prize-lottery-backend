package com.prize.lottery.infrast.repository.impl;

import com.prize.lottery.domain.user.model.UserBalance;
import com.prize.lottery.domain.user.repository.IUserBalanceRepository;
import com.prize.lottery.infrast.persist.mapper.UserInfoMapper;
import com.prize.lottery.infrast.persist.po.UserBalanceLogPo;
import com.prize.lottery.infrast.persist.po.UserBalancePo;
import com.prize.lottery.infrast.repository.converter.UserBalanceConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserBalanceRepository implements IUserBalanceRepository {

    private final UserInfoMapper       mapper;
    private final UserBalanceConverter converter;

    @Override
    public void save(UserBalance balance) {
        if (balance.isNew()) {
            UserBalancePo balancePo = new UserBalancePo();
            balancePo.setUserId(balance.getUserId());
            mapper.addUserBalance(balancePo);
            return;
        }
        if (balance.getOperation() == null) {
            UserBalancePo userBalance = converter.toPo(balance);
            mapper.editUserBalance(userBalance);
            return;
        }
        //账户信息变更
        balance.toBalance().ifPresent(mapper::editUserBalance);
        //账户操作日志
        balance.toBalanceLog().ifPresent(mapper::addUserBalanceLog);
    }

    @Override
    public Optional<UserBalance> ofId(Long userId) {
        return Optional.ofNullable(mapper.getUserBalance(userId)).map(converter::toDo);
    }

    @Override
    public void saveBatch(List<UserBalance> balances) {
        //保存账户操作变更
        List<UserBalancePo> userBalances = balances.stream()
                                                   .map(UserBalance::toBalance)
                                                   .filter(Optional::isPresent)
                                                   .map(Optional::get)
                                                   .collect(Collectors.toList());
        //保存账户操作日志
        List<UserBalanceLogPo> balanceLogs = balances.stream()
                                                     .map(UserBalance::toBalanceLog)
                                                     .filter(Optional::isPresent)
                                                     .map(Optional::get)
                                                     .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(userBalances)) {
            mapper.editBalanceList(userBalances);
        }
        if (!CollectionUtils.isEmpty(balanceLogs)) {
            mapper.addBalanceLogList(balanceLogs);
        }
    }

    @Override
    public List<UserBalance> ofUsers(List<Long> userIds) {
        return mapper.getBalanceByUsers(userIds).stream().map(converter::toDo).collect(Collectors.toList());
    }

}
