package com.prize.lottery.validator;

import com.prize.lottery.event.MessageEvent;
import com.prize.lottery.utils.MessageConstants;
import org.apache.commons.lang3.StringUtils;

public class LinkValidator extends TextValidator {
    @Override
    public boolean validate(MessageEvent event) {
        if (!super.validate(event)) {
            return false;
        }
        String link = event.get(MessageConstants.KEY_CONTENT_LINK);
        return StringUtils.isNotBlank(link);
    }
}
