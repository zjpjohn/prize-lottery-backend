package com.prize.lottery.domain.lottery.ability;

import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.application.cmd.HoneySingleCmd;
import com.prize.lottery.domain.lottery.model.LotteryHoneyDo;
import com.prize.lottery.domain.lottery.repository.IHoneyRepository;
import com.prize.lottery.enums.LotteryEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class HoneyDomainAbility {

    private final IHoneyRepository repository;

    /**
     * 添加配胆图
     */
    public void addHoney(LotteryEnum type, HoneySingleCmd command) {
        LotteryHoneyDo lotteryHoney =
            new LotteryHoneyDo(command.getPeriod(), type, command.getValues(), command.getLottoDate());
        AggregateFactory.create(lotteryHoney).save(repository::save);
    }

    /**
     * 批量添加配胆图
     */
    public void addHoneyList(LotteryEnum type, List<HoneySingleCmd> commands) {
        List<LotteryHoneyDo> honeyList = commands.stream()
                                                 .map(e -> new LotteryHoneyDo(e.getPeriod(), type, e.getValues(), e.getLottoDate()))
                                                 .collect(Collectors.toList());
        repository.saveBatch(honeyList);
    }

}
