package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Lists;
import com.prize.lottery.domain.omit.model.LottoKl8OmitDo;
import com.prize.lottery.domain.omit.repository.ILottoKl8OmitRepository;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.infrast.repository.converter.LottoOmitConverter;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.po.lottery.LotteryKl8OmitPo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LottoKl8OmitRepository implements ILottoKl8OmitRepository {

    private final LottoOmitConverter omitConverter;
    private final LotteryInfoMapper  lotteryMapper;

    @Override
    public void save(LottoKl8OmitDo omit) {
        LotteryKl8OmitPo kl8Omit = omitConverter.toPo(omit);
        lotteryMapper.addKl8Omits(Lists.newArrayList(kl8Omit));
    }

    @Override
    public void saveBatch(List<LottoKl8OmitDo> omits) {
        List<LotteryKl8OmitPo> kl8Omits = omitConverter.toPoList(omits);
        lotteryMapper.addKl8Omits(kl8Omits);
    }

    @Override
    public Optional<LottoKl8OmitDo> latestOmit() {
        return Optional.ofNullable(lotteryMapper.getLatestKl8Omit()).map(omitConverter::toDo);
    }

    @Override
    public LottoKl8OmitDo ofPeriod(String period) {
        LotteryKl8OmitPo lottoKl8Omit = lotteryMapper.getLottoKl8Omit(period);
        Assert.notNull(lottoKl8Omit, ResponseHandler.NO_LOTTERY_OMIT);
        return omitConverter.toDo(lottoKl8Omit);
    }

}
