package com.prize.lottery.infrast.spider.open;

import com.google.common.collect.Lists;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.po.lottery.LotteryInfoPo;
import com.prize.lottery.utils.DanMaUtils;
import com.prize.lottery.utils.Num3ZuHeUtils;

import java.util.List;

public class DanMarker {

    private List<String> twoMaList = Lists.newArrayList();
    private List<String> dateDan   = Lists.newArrayList();
    private List<String> duiMaDan  = Lists.newArrayList();

    public DanMarker(LotteryInfoPo lottery) {
        if (lottery != null) {
            twoMaList = Num3ZuHeUtils.twoMaList(lottery.getRed());
            dateDan   = DanMaUtils.dateDan(lottery.getLotDate().plusDays(1));
            duiMaDan  = duiMaDan(lottery);
        }
    }

    private List<String> duiMaDan(LotteryInfoPo lottery) {
        String type = lottery.getType();
        if (LotteryEnum.FC3D.getType().equals(type)) {
            return DanMaUtils.duiMaDan(lottery.getRed(), DanMaUtils.Mode.MIDDLE.getMode());
        }
        return DanMaUtils.duiMaDan(lottery.getRed(), DanMaUtils.Mode.BIG.getMode());
    }

    public int mark(String dan2) {
        if (!twoMaList.contains(dan2)) {
            return 0;
        }
        int mark = 1;
        if (dateDan.stream().anyMatch(dan2::contains)) {
            mark = 2;
        }
        if (mark == 2 && duiMaDan.stream().anyMatch(dan2::contains)) {
            mark = 3;
        }
        return mark;
    }

}
