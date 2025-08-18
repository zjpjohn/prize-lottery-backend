package com.prize.lottery.application.consumer;

import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.application.consumer.event.AdsRewardEvent;
import com.prize.lottery.domain.activity.model.ActivityDrawDo;
import com.prize.lottery.domain.activity.repository.IActivityDrawRepository;
import com.prize.lottery.domain.user.repository.IUserBalanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdvertRewardHandler {

    private final IUserBalanceRepository  userBalanceRepository;
    private final IActivityDrawRepository activityDrawRepository;

    /**
     * 激励视频观看奖励事件处理
     *
     * @param event 奖励事件
     */
    @EventListener
    public void onHandle(AdsRewardEvent event) {
        Integer channel = event.getChannel();
        Long    userId  = event.getUserId();
        if (channel == null || channel == 1) {
            //观看广告金币奖励
            userBalanceRepository.ofId(userId).ifPresent(balance -> {
                balance.incrBalance(event.getReward(), event.getBounty());
                userBalanceRepository.save(balance);
            });
            return;
        }
        //观看广告获取抽奖机会
        activityDrawRepository.ofDay(userId, LocalDate.now()).map(AggregateFactory::create).ifPresent(root -> {
            root.peek(ActivityDrawDo::rewardChance).save(activityDrawRepository::save);
        });
    }

}
