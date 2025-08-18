package com.prize.lottery.validator;

import com.prize.lottery.event.MessageEvent;
import com.prize.lottery.utils.MessageConstants;
import org.apache.commons.lang3.StringUtils;

public class TextValidator implements Validator {

    @Override
    public boolean validate(MessageEvent event) {
        if (!Validator.super.validate(event)) {
            return false;
        }
        String content = event.get(MessageConstants.KEY_CONTENT_TEXT);
        return StringUtils.isNotBlank(content);
    }
}
