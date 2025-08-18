package com.prize.lottery;

import com.cloud.arch.web.impl.DefaultHttpAuthSource;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class LotteryAuthSource extends DefaultHttpAuthSource {

    private final String description;

    public LotteryAuthSource(AuthSourceProperty property) {
        super(property.getIdentity(), property.getDomain(), property.getRetains());
        this.description = property.getDescription();
    }

    @Override
    public String label() {
        return this.description;
    }

}
