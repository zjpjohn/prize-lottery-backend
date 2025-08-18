package com.prize.lottery.application.query.service.impl;

import com.prize.lottery.application.assembler.LotteryPickAssembler;
import com.prize.lottery.application.query.dto.LotteryPickQuery;
import com.prize.lottery.application.query.service.ILotteryPickQueryService;
import com.prize.lottery.application.vo.LotteryPickVo;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.mapper.LotteryIndexMapper;
import com.prize.lottery.mapper.LotteryPickMapper;
import com.prize.lottery.mapper.Num3LottoIndexMapper;
import com.prize.lottery.po.lottery.LotteryPickPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LotteryPickQueryService implements ILotteryPickQueryService {

    private final LotteryPickMapper    pickMapper;
    private final LotteryIndexMapper   indexMapper;
    private final LotteryPickAssembler assembler;
    private final Num3LottoIndexMapper num3IndexMapper;

    @Override
    public LotteryPickVo getLotteryPick(LotteryPickQuery query) {
        String lottery = query.getLottery().getType();
        String indexPeriod = Optional.ofNullable(query.getPeriod())
                                     .filter(StringUtils::isNotBlank)
                                     .orElseGet(() -> indexMapper.latestPeriod(lottery));
        LotteryPickPo pickPo = pickMapper.getLotteryPickByUk(lottery, indexPeriod, query.getUserId());
        return assembler.toVo(pickPo);
    }

    @Override
    public List<String> indexPeriods(String lottery) {
        return indexMapper.indexPeriodList(lottery);
    }

    @Override
    public List<String> num3IndexPeriods(LotteryEnum lottery) {
        return num3IndexMapper.getIndexPeriods(lottery, 30);
    }

}
