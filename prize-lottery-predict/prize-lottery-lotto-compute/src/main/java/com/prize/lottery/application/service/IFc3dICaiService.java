package com.prize.lottery.application.service;


import com.prize.lottery.application.cmd.DkRecommendCmd;
import com.prize.lottery.application.cmd.Fc3dComSelectCmd;
import com.prize.lottery.application.cmd.N3TodayPivotCmd;
import com.prize.lottery.application.vo.*;
import com.prize.lottery.domain.n3item.domain.N3ItemCensusDo;
import com.prize.lottery.enums.ChartType;

public interface IFc3dICaiService {

    /**
     * 拉取指定期的预测数据
     *
     * @param period 指定期号
     */
    void fetchForecast(String period);

    /**
     * 抓取最近30期的历史预测数据
     */
    void fetchHistoryForecast();

    /**
     * 增量抓取30期历史预测数据
     */
    void fetchIncrHistory();

    /**
     * 拉取指定期以前的历史预测数据
     */
    void fetchLastForecast(String before, Integer size);

    /**
     * 初始化计算历史数据命中
     */
    void batchCalcFc3dHit();

    /**
     * 计算增量历史数据全名命中
     */
    void calcIncrAllHit();

    /**
     * 初始化计算专家历史命中率
     */
    void initCalcMasterRate();

    /**
     * 增量计算专家命中率
     */
    void incrCalcMasterRate();

    /**
     * 初始化计算历史专家排名
     */
    void initCalcMasterRank();

    /**
     * 增量分析
     *
     * @param period 期号
     */
    N3DifferAnalyzeVo differAnalyze(String period);

    /**
     * 组合计算分析
     *
     * @param period 计算期号
     */
    Fc3dCombineAnalyzeVo combineAnalyze(String period);

    /**
     * 取反分析
     *
     * @param period 计算期号
     */
    Fc3dReverseAnalyzeVo reverseAnalyze(String period);

    /**
     * 组合号码筛选
     */
    Fc3dComRecommendVo comRecommendAnalyze(Fc3dComSelectCmd command);

    /**
     * 胆码杀码计算推荐号码
     */
    Fc3dDkRecommendVo dkRecommend(DkRecommendCmd cmd);

    /**
     * 计算胆码杀码
     */
    DanKillCalcResult danKillCalc(String period, ChartType type);

    /**
     * 结合分类预测计算指数
     */
    N3ItemCensusDo calcItemCensus(String period);

    /**
     * 计算组合推荐命中
     *
     * @param period 推荐期号
     */
    void calcComRecommend(String period);

    /**
     * 添加今日要点信息
     */
    void addFc3dPivot(N3TodayPivotCmd command);

    /**
     * 计算今日要点命中
     */
    void calcPivotHit(String period);

    /**
     * 标记双胆
     */
    void calcForecastMark(String period);
}
