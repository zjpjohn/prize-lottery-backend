package com.prize.lottery.domain.share.model;

import com.prize.lottery.enums.WarningEnums;
import com.prize.lottery.value.WarningValue;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class EarlyWarning {

    private WarningEnums type;
    private WarningValue warn;

    public EarlyWarning(WarningEnums type, List<String> values) {
        this.type = type;
        this.warn = new WarningValue(values);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        EarlyWarning that = (EarlyWarning) o;
        return type == that.type;
    }

}
