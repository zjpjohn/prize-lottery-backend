package com.prize.lottery.infrast.spider.skill;


import com.prize.lottery.enums.LotteryEnum;

import java.util.Arrays;

public enum LottoSkill {
    FC3D_SKILL(LotteryEnum.FC3D, "410540"),
    PL3_SKILL(LotteryEnum.PL3, "410541"),
    SSQ_SKILL(LotteryEnum.SSQ, "409921"),
    DLT_SKILL(LotteryEnum.DLT, "410358"),
    KL8_SKILL(LotteryEnum.KL8, "430035");

    private final LotteryEnum type;
    private final String      labels;

    LottoSkill(LotteryEnum type, String labels) {
        this.type   = type;
        this.labels = labels;
    }

    public LotteryEnum getType() {
        return type;
    }

    public String getLabels() {
        return labels;
    }

    public static LottoSkill value(LotteryEnum type) {
        return Arrays.stream(values()).filter(e -> e.type == type).findFirst().orElse(null);
    }
}
