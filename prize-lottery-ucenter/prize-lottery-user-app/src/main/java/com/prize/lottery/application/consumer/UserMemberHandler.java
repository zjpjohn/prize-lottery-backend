package com.prize.lottery.application.consumer;

import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.application.consumer.event.UserMemberEvent;
import com.prize.lottery.domain.user.model.UserMember;
import com.prize.lottery.domain.user.repository.IUserMemberRepository;
import com.prize.lottery.infrast.persist.enums.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserMemberHandler {

    private final IUserMemberRepository memberRepository;

    @EventListener
    public void onHandle(UserMemberEvent event) {
        TimeUnit timeUnit = TimeUnit.of(event.getTimeUnit()).orElse(null);
        if (timeUnit == null) {
            log.warn("用户会员有效期时间单位错误[{}]", event.getTimeUnit());
            return;
        }
        memberRepository.ofUser(event.getUserId())
                        .map(agg -> agg.peek(member -> member.renewMember(event, timeUnit)))
                        .orElseGet(() -> AggregateFactory.create(new UserMember(event, timeUnit)))
                        .save(memberRepository::save);
    }

}
