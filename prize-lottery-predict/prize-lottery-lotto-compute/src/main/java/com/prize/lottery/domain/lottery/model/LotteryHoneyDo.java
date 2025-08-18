package com.prize.lottery.domain.lottery.model;

import com.cloud.arch.aggregate.AggregateRoot;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.value.HoneyValue;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class LotteryHoneyDo implements AggregateRoot<Long> {

    private static final long serialVersionUID = -4572589051737536882L;

    private Long        id;
    private String      period;
    private LotteryEnum type;
    private HoneyValue  honey;
    private LocalDate   lottoDate;

    public LotteryHoneyDo(String period, LotteryEnum type, List<String> balls, LocalDate lottoDate) {
        this.period    = period;
        this.type      = type;
        this.honey     = new HoneyValue(balls);
        this.lottoDate = lottoDate;
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }
}
