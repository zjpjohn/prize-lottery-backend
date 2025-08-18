package com.prize.lottery.application.consumer;

import com.prize.lottery.application.command.executor.MessageHandleExecutor;
import com.prize.lottery.application.consumer.event.MessageCreateEvent;
import com.prize.lottery.event.MessageType;
import com.prize.lottery.validator.Validators;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageCenterHandler {

    private final MessageHandleExecutor handleExecutor;

    @EventListener
    public void handle(MessageCreateEvent event) {
        MessageType messageType = MessageType.of(event.getType());
        if (messageType == null || Validators.isNotValid(event, messageType)) {
            log.warn("message event is not validate,event info:{}", event);
            return;
        }
        handleExecutor.execute(event, messageType);
    }
}
