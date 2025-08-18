package com.prize.lottery.validator;

import com.prize.lottery.event.MessageEvent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

public interface Validator {

    default boolean validate(MessageEvent event) {
        return event.getType() != null
                && StringUtils.isNotBlank(event.getChannel())
                && StringUtils.isNotBlank(event.getTitle())
                && !CollectionUtils.isEmpty(event.getContent());
    }

}
