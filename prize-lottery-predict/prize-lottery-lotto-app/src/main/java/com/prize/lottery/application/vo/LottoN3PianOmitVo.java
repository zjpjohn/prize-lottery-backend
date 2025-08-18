package com.prize.lottery.application.vo;

import com.google.common.collect.Maps;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.po.lottery.LottoPianOmitPo;
import com.prize.lottery.value.Omit;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class LottoN3PianOmitVo {

    private String             period;
    private LotteryEnum        type;
    private String             lottery;
    private Map<Integer, Omit> cb1 = Maps.newHashMap();
    private Map<Integer, Omit> cb2 = Maps.newHashMap();
    private Map<Integer, Omit> cb3 = Maps.newHashMap();

    public void leveledOmits(List<LottoPianOmitPo> datas) {
        Map<Integer, LottoPianOmitPo> grouped = Maps.uniqueIndex(datas, LottoPianOmitPo::getLevel);
        grouped.forEach((index, value) -> {
            cb1.put(index, value.getCb1());
            cb2.put(index, value.getCb2());
            cb3.put(index, value.getCb3());
        });
    }

}
