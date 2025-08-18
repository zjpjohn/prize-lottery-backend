package com.prize.lottery.application.assembler;

import com.prize.lottery.application.vo.*;
import com.prize.lottery.po.fc3d.Fc3dPivotPo;
import com.prize.lottery.po.lottery.*;
import com.prize.lottery.po.pl3.Pl3PivotPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LotteryAssembler {

    @Mapping(source = "position.positions", target = "positions")
    LotteryCodeVo toVo(LotteryCodePo code);

    LotteryDanVo toVo(LotteryDanPo dan);

    LotteryOttVo toVo(LotteryOttPo dan);

    N3TodayPivotVo toVo(Fc3dPivotPo pivot);

    N3TodayPivotVo toVo(Pl3PivotPo pivot);

    @Mapping(source = "lottoDate", target = "date")
    LotteryAroundVo toVo(LotteryAroundPo around);

    @Mapping(source = "lottoDate", target = "date")
    LotteryHoneyVo toVo(LotteryHoneyPo honey);

}
