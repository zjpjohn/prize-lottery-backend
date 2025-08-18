package com.prize.lottery.application.query.service;


import com.cloud.arch.page.Page;
import com.prize.lottery.application.query.dto.Num3LayerQuery;
import com.prize.lottery.application.query.dto.Num3WarnQuery;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.po.lottery.Num3ComWarningPo;
import com.prize.lottery.po.lottery.Num3LayerFilterPo;
import com.prize.lottery.vo.Num3LayerStateVo;

import java.util.List;

public interface INum3WarnQueryService {

    Page<Num3ComWarningPo> getComWarningList(Num3WarnQuery query);

    Num3ComWarningPo getComWarning(Long id);

    List<String> comWarningPeriods(LotteryEnum type);

    Page<Num3LayerFilterPo> getNum3LayerList(Num3LayerQuery query);

    Num3LayerFilterPo getNum3Layer(Long id);

    List<String> num3LayerPeriods(LotteryEnum type);

    Num3LayerStateVo getNum3LayerState(LotteryEnum type);

}
