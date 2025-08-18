package com.prize.lottery.domain.fc3d.model;

import com.prize.lottery.enums.WarningEnums;
import com.prize.lottery.value.WarningValue;
import lombok.Data;

import java.util.List;

@Data
public class Fc3dEarlyWarningDo {

    private WarningEnums type;
    private WarningValue warn;

    public Fc3dEarlyWarningDo(WarningEnums type, List<String> values) {
        this.type = type;
        this.warn = new WarningValue(values);
    }

}
