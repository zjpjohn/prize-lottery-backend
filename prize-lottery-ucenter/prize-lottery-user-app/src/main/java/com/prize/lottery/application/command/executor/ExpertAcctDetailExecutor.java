package com.prize.lottery.application.command.executor;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.assembler.ExpertAcctAssembler;
import com.prize.lottery.application.command.vo.ExpertAccountVo;
import com.prize.lottery.domain.expert.model.ExpertBalance;
import com.prize.lottery.domain.expert.repository.IExpertBalanceRepository;
import com.prize.lottery.domain.user.model.UserInfo;
import com.prize.lottery.domain.user.repository.IUserInfoRepository;
import com.prize.lottery.domain.withdraw.repository.IWithdrawRuleRepository;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.TransferScene;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExpertAcctDetailExecutor {

    private final IUserInfoRepository      userInfoRepository;
    private final ExpertAcctAssembler      expertAcctAssembler;
    private final IWithdrawRuleRepository  withdrawRuleRepository;
    private final IExpertBalanceRepository expertBalanceRepository;

    @Transactional
    public ExpertAccountVo execute(Long userId) {
        //专家账户信息
        ExpertBalance             balance   = expertBalanceRepository.ofId(userId)
                                                                     .orElseThrow(ResponseHandler.USER_MASTER_NONE);
        Aggregate<Long, UserInfo> aggregate = userInfoRepository.ofId(userId);
        Assert.notNull(aggregate, ResponseHandler.USER_INFO_NONE);

        UserInfo        userInfo = aggregate.getRoot();
        ExpertAccountVo acctVo   = expertAcctAssembler.toVo(balance, userInfo.getNickname(), userInfo.getPhone());
        //当前账户是否允许提现
        Boolean canWithdraw = withdrawRuleRepository.ofScene(TransferScene.USER_EXPERT_TRANS.getScene())
                                                    .map(balance::canWithdraw)
                                                    .orElse(false);
        acctVo.setCanWithdraw(canWithdraw ? 1 : 0);
        acctVo.setScene(TransferScene.USER_EXPERT_TRANS);
        return acctVo;
    }

}
