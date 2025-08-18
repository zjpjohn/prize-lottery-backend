package com.prize.lottery.application.command.executor.withdraw;


import com.cloud.arch.executor.Executor;
import com.prize.lottery.application.command.dto.WithdrawDto;
import com.prize.lottery.application.consumer.event.TransCallbackEvent;
import com.prize.lottery.domain.withdraw.specs.WithdrawRuleSpec;
import com.prize.lottery.infrast.persist.enums.TransferScene;

public interface WithdrawExecutor extends Executor<TransferScene> {

    /**
     * 支付业务
     */
    void execute(WithdrawDto withdraw, WithdrawRuleSpec ruleSpec);

    /**
     * 提现回调处理
     *
     * @param event 回调事件
     */
    void callbackHandle(TransCallbackEvent event);

}
