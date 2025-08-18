package com.prize.lottery.application.command.service.impl;

import com.prize.lottery.application.command.dto.LotteryIndexCmd;
import com.prize.lottery.application.command.dto.LottoN3PickCmd;
import com.prize.lottery.application.command.dto.LottoPickCmd;
import com.prize.lottery.application.command.executor.lotto.index.LotteryIndexBrowseExe;
import com.prize.lottery.application.command.service.ILotteryPickCommandService;
import com.prize.lottery.application.vo.LotteryIndexVo;
import com.prize.lottery.application.vo.Num3LottoIndexVo;
import com.prize.lottery.domain.pick.model.LotteryPickDo;
import com.prize.lottery.domain.pick.repository.ILotteryPickRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LotteryPickCommandService implements ILotteryPickCommandService {

    private final LotteryIndexBrowseExe  browseExe;
    private final ILotteryPickRepository pickRepository;

    @Override
    public void lottoPick(Long userId, LottoN3PickCmd command) {
        LotteryPickDo lotteryPick = new LotteryPickDo(command, userId);
        pickRepository.save(lotteryPick);
    }

    @Override
    public void lottoPick(Long userId, LottoPickCmd command) {
        LotteryPickDo lotteryPick = new LotteryPickDo(command, userId);
        pickRepository.save(lotteryPick);
    }

    @Override
    @Transactional
    public LotteryIndexVo lotteryIndex(LotteryIndexCmd command) {
        return browseExe.execute(command);
    }

    @Override
    public Num3LottoIndexVo num3Index(LotteryIndexCmd command) {
        return browseExe.num3Index(command);
    }
}
