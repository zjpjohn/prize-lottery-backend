package com.prize.lottery.application.command.executor.withdraw;

import com.cloud.arch.executor.EnumerableExecutorFactory;
import com.prize.lottery.application.command.dto.WithdrawDto;
import com.prize.lottery.application.consumer.event.TransCallbackEvent;
import com.prize.lottery.domain.withdraw.specs.WithdrawRuleSpec;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.TransferScene;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WithdrawExecutorFactory {

    private final EnumerableExecutorFactory<TransferScene, WithdrawExecutor> executorFactory;

    /**
     * 资金账户提现操作
     */
    public void execute(WithdrawDto withdraw, WithdrawRuleSpec ruleSpec) {
        this.executorFactory.ofNullable(withdraw.getScene())
                            .orElseThrow(ResponseHandler.WITHDRAW_FORBIDDEN)
                            .execute(withdraw, ruleSpec);
    }

    /**
     * 提现回调处理
     */
    public void callbackHandle(TransCallbackEvent event) {
        TransferScene.ofNullable(event.getScene())
                     .flatMap(this.executorFactory::ofNullable)
                     .ifPresent(executor -> executor.callbackHandle(event));
    }

}
