package com.prize.lottery.application.command.executor;

import com.prize.lottery.application.assembler.UserInfoAssembler;
import com.prize.lottery.application.command.vo.UserBalanceVo;
import com.prize.lottery.domain.user.model.UserBalance;
import com.prize.lottery.domain.user.repository.IUserBalanceRepository;
import com.prize.lottery.domain.withdraw.repository.IWithdrawRuleRepository;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.TransferScene;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserBalanceDetailExecutor {

    private final UserInfoAssembler       userInfoAssembler;
    private final IUserBalanceRepository  userBalanceRepository;
    private final IWithdrawRuleRepository withdrawRuleRepository;

    public UserBalanceVo execute(Long userId) {
        UserBalance   balance   = userBalanceRepository.ofId(userId).orElseThrow(ResponseHandler.ACCOUNT_NONE_EXISTED);
        UserBalanceVo balanceVo = userInfoAssembler.toVo(balance);
        balanceVo.setScene(TransferScene.USER_REWARD_TRANS);
        //账户是否允许提现
        Boolean canWithdraw = withdrawRuleRepository.ofScene(TransferScene.USER_REWARD_TRANS.value())
                                                    .map(balance::canWithdraw)
                                                    .orElse(false);
        balanceVo.setCanWithdraw(canWithdraw ? 1 : 0);
        return balanceVo;
    }
}
