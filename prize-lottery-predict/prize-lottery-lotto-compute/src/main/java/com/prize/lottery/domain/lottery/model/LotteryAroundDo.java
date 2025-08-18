package com.prize.lottery.domain.lottery.model;

import com.cloud.arch.aggregate.AggregateRoot;
import com.prize.lottery.enums.AroundType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.value.AroundResult;
import com.prize.lottery.value.AroundValue;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class LotteryAroundDo implements AggregateRoot<Long> {

    private static final long serialVersionUID = -2094054132749521812L;

    private Long         id;
    private String       period;
    private AroundType   type;
    private LotteryEnum  lotto;
    private AroundValue  around;
    private AroundResult result;
    private LocalDate    lottoDate;

    public LotteryAroundDo(String period,
                           AroundType type,
                           LotteryEnum lotto,
                           LocalDate lottoDate,
                           List<AroundValue.AroundCell> cells) {
        this.period    = period;
        this.type      = type;
        this.lotto     = lotto;
        this.around    = new AroundValue(cells);
        this.lottoDate = lottoDate;
    }

    public void calcResult(List<String> balls) {
        this.result = this.around.calc(balls);
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }

}
