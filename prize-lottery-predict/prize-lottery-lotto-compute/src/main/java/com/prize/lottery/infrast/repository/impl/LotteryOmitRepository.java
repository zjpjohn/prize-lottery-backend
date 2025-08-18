package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.omit.model.LotteryOmitDo;
import com.prize.lottery.domain.omit.repository.ILotteryOmitRepository;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.infrast.repository.converter.LottoOmitConverter;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.po.lottery.LotteryOmitPo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LotteryOmitRepository implements ILotteryOmitRepository {

    private final LottoOmitConverter omitConverter;
    private final LotteryInfoMapper  lotteryMapper;

    @Override
    public void save(LotteryOmitDo omit) {
        LotteryOmitPo lotteryOmit = omitConverter.toPo(omit);
        lotteryMapper.addLotteryOmitInfo(lotteryOmit);
    }

    @Override
    public void saveBatch(List<LotteryOmitDo> omits) {
        List<LotteryOmitPo> omitList = omitConverter.toOmitList(omits);
        lotteryMapper.addLotteryOmits(omitList);
    }

    @Override
    public Optional<LotteryOmitDo> latestOmit(LotteryEnum type) {
        return Optional.ofNullable(lotteryMapper.getLatestLotteryOmit(type.getType())).map(omitConverter::toDo);
    }

    @Override
    public LotteryOmitDo ofPeriod(LotteryEnum type, String period) {
        LotteryOmitPo omitInfo = lotteryMapper.getLotteryOmitInfo(type.getType(), period);
        Assert.notNull(omitInfo, ResponseHandler.NO_LOTTERY_OMIT);
        return omitConverter.toDo(omitInfo);
    }

}
