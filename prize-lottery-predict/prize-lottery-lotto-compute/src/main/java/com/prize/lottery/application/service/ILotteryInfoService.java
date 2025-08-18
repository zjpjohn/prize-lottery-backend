package com.prize.lottery.application.service;


import com.prize.lottery.application.cmd.AroundBatchCmd;
import com.prize.lottery.application.cmd.AroundSingleCmd;
import com.prize.lottery.application.cmd.HoneySingleCmd;
import com.prize.lottery.application.cmd.TrialCreateCmd;
import com.prize.lottery.enums.LotteryEnum;

import java.util.List;

public interface ILotteryInfoService {

    String fetchLatestLottery(String type);

    String fetchLotteries(String type, Integer size);

    String fetchLotteries(String type, String start, String end);

    void fetchLotteryTrial(String type, String period);

    void fetchLotteryTrials(String type, Integer size);

    void loadInitialOmit();

    void calcLotteryOmit(LotteryEnum type);

    void loadKuaOmit(LotteryEnum type);

    void calcKuaOmit(LotteryEnum type);

    void loadSumOmit(LotteryEnum type);

    void calcSumOmit(LotteryEnum type);

    void codeInitialize(LotteryEnum type);

    void loadTrendOmit(LotteryEnum type);

    void calcTrendOmit(LotteryEnum type);

    void loadMatchOmit(LotteryEnum type);

    void calcMatchOmit(LotteryEnum type);

    void loadItemOmit(LotteryEnum type);

    void calcItemOmit(LotteryEnum type);

    void initLottoDan(LotteryEnum type);

    void initLottoOtt(LotteryEnum type);

    void addAroundSingle(AroundSingleCmd cmd);

    void addAroundBatch(AroundBatchCmd cmd);

    void removeAround(Long id);

    void fetchAround(Integer size);

    void calcAroundResult(LotteryEnum type, String period);

    void addHoneySingle(LotteryEnum type, HoneySingleCmd cmd);

    void addHoneyBatch(LotteryEnum type, List<HoneySingleCmd> cmdList);

    void removeHoney(Long id);

    void calcNum3Index(LotteryEnum type, String period);

    void initPianOmits(LotteryEnum type);

    void calcPianOmit(LotteryEnum type, String period);

    void createFaireTrial(TrialCreateCmd command);

    void initCalcNum3Com(LotteryEnum type, Integer limit);

    void initCalcNum3Same(LotteryEnum type, Integer limit);

}
