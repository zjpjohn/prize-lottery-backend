package com.prize.lottery.application.consumer.event;


import com.cloud.arch.event.annotations.Subscribe;
import com.prize.lottery.event.MessageEvent;

@Subscribe(name = "cloud-lottery-user-topic", filter = "message-center")
public class MessageCreateEvent extends MessageEvent {
    private static final long serialVersionUID = 5257015529886128395L;
}
