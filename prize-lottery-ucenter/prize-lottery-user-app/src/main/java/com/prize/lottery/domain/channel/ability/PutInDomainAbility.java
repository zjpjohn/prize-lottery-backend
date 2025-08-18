package com.prize.lottery.domain.channel.ability;

import com.prize.lottery.domain.channel.event.ChannelInviteEvent;
import com.prize.lottery.domain.channel.model.PutRecordDo;
import com.prize.lottery.domain.channel.repository.IPutRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PutInDomainAbility {

    private final IPutRecordRepository repository;

    @Async
    @EventListener
    public void putHandle(ChannelInviteEvent event) {
        repository.ofCode(event.getCode())
                  .ifPresent(aggregate -> aggregate.peek(PutRecordDo::putIncrCount).save(repository::save));
    }
}
