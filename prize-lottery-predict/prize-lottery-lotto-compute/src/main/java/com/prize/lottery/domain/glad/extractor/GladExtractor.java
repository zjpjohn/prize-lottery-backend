package com.prize.lottery.domain.glad.extractor;


import com.cloud.arch.executor.Executor;
import com.prize.lottery.domain.glad.domain.MasterGladDo;
import com.prize.lottery.enums.LotteryEnum;

import java.util.List;

public interface GladExtractor extends Executor<LotteryEnum> {

    List<MasterGladDo> extract();

}
