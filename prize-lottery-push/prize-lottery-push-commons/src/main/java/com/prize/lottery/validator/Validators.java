package com.prize.lottery.validator;


import com.prize.lottery.event.MessageEvent;
import com.prize.lottery.event.MessageType;

import java.util.EnumMap;

public class Validators implements Validator {

    public static final EnumMap<MessageType, Validator> VALIDATOR_CACHE = new EnumMap<>(MessageType.class);

    static {
        VALIDATOR_CACHE.put(MessageType.TEXT, new TextValidator());
        VALIDATOR_CACHE.put(MessageType.LINK, new LinkValidator());
        VALIDATOR_CACHE.put(MessageType.CARD, new CardValidator());
    }

    /**
     * 校验消息内容
     *
     * @param event 消息内容
     * @param type  消息类型
     */
    public static boolean isNotValid(MessageEvent event, MessageType type) {
        Validator validator   = VALIDATOR_CACHE.get(type);
        Integer   eventSource = event.getSource();
        if (eventSource == null || !validator.validate(event)) {
            return true;
        }
        return eventSource != 0 && event.getUserId() == null;
    }

}
