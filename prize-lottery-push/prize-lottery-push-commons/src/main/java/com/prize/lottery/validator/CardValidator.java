package com.prize.lottery.validator;

import com.prize.lottery.event.MessageEvent;
import com.prize.lottery.utils.MessageConstants;
import org.apache.commons.lang3.StringUtils;

public class CardValidator extends LinkValidator {
    @Override
    public boolean validate(MessageEvent event) {
        if (!super.validate(event)) {
            return false;
        }
        String cover = event.get(MessageConstants.KEY_CONTENT_COVER);
        return StringUtils.isNotBlank(cover);
    }
}
