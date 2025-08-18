package com.prize.lottery.infrast.repository.converter;

import com.prize.lottery.domain.lottery.model.LotteryFairTrialDo;
import com.prize.lottery.domain.omit.model.*;
import com.prize.lottery.po.lottery.*;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LottoOmitConverter {

    LotteryFairTrialPo toTrial(LotteryFairTrialDo trial);

    List<LotteryFairTrialPo> toTrialList(List<LotteryFairTrialDo> trials);

    LotteryKl8OmitPo toPo(LottoKl8OmitDo omit);

    List<LotteryKl8OmitPo> toPoList(List<LottoKl8OmitDo> omits);

    LotteryOmitPo toPo(LotteryOmitDo omit);

    List<LotteryOmitPo> toOmitList(List<LotteryOmitDo> omits);

    LottoKl8OmitDo toDo(LotteryKl8OmitPo omit);

    LotteryOmitDo toDo(LotteryOmitPo omit);

    LotterySumOmitPo toPo(LottoSumOmitDo omit);

    List<LotterySumOmitPo> toSumList(List<LottoSumOmitDo> omits);

    LottoSumOmitDo toDo(LotterySumOmitPo omit);

    LotteryKuaOmitPo toPo(LottoKuaOmitDo omit);

    List<LotteryKuaOmitPo> toKuaList(List<LottoKuaOmitDo> omits);

    LottoKuaOmitDo toDo(LotteryKuaOmitPo omit);

    LotteryCodePo toPo(LotteryCodeDo code);

    List<LotteryCodePo> toCodeList(List<LotteryCodeDo> codes);

    LotteryDanDo toDo(LotteryDanPo dan);

    LotteryDanPo toPo(LotteryDanDo dan);

    List<LotteryDanPo> toDanPoList(List<LotteryDanDo> danList);

    LotteryOttDo toDo(LotteryOttPo ott);

    LotteryOttPo toPo(LotteryOttDo ott);

    List<LotteryOttPo> toOttPoList(List<LotteryOttDo> ottList);

    LottoPianOmitPo toPo(LottoPianOmitDo omit);

    List<LottoPianOmitPo> toPianList(List<LottoPianOmitDo> omits);

    LotteryTrendOmitDo toDo(LotteryTrendOmitPo omit);

    LotteryTrendOmitPo toPo(LotteryTrendOmitDo omit);

    List<LotteryTrendOmitPo> toTrendList(List<LotteryTrendOmitDo> omitList);

    LotteryMatchOmitDo toDo(LotteryMatchOmitPo omit);

    LotteryMatchOmitPo toPo(LotteryMatchOmitDo omit);

    List<LotteryMatchOmitPo> toMatchList(List<LotteryMatchOmitDo> omitList);

    LotteryItemOmitDo toDo(LotteryItemOmitPo omit);

    LotteryItemOmitPo toPo(LotteryItemOmitDo omit);

    List<LotteryItemOmitPo> toItemList(List<LotteryItemOmitDo> omitList);

    LotteryPl5OmitPo toPo(LottoP5ItemOmitDo omitDo);

    List<LotteryPl5OmitPo> toP5List(List<LottoP5ItemOmitDo> omitList);

    LottoP5ItemOmitDo toDo(LotteryPl5OmitPo omit);

}
