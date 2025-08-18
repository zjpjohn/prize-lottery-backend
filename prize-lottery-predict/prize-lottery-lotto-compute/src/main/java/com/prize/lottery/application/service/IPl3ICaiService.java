package com.prize.lottery.application.service;


import com.prize.lottery.application.cmd.DkRecommendCmd;
import com.prize.lottery.application.cmd.N3TodayPivotCmd;
import com.prize.lottery.application.cmd.Pl3ComSelectedCmd;
import com.prize.lottery.application.vo.DanKillCalcResult;
import com.prize.lottery.application.vo.N3DifferAnalyzeVo;
import com.prize.lottery.application.vo.Pl3ComRecommendVo;
import com.prize.lottery.application.vo.Pl3DkRecommendVo;
import com.prize.lottery.domain.n3item.domain.N3ItemCensusDo;
import com.prize.lottery.enums.ChartType;

public interface IPl3ICaiService {

    /**
     * 抓取指定期的预测数据
     *
     * @param period 指定期号
     */
    void fetchForecast(String period);

    /**
     * 抓取最近30期的历史预测数据
     */
    void fetchHistoryForecast();

    /**
     * 增量抓取30期的历史预测数据
     */
    void fetchIncrHistory();

    /**
     * 抓取指定期以前的历史预测数据
     *
     * @param before 指定期号
     * @param size   指定期数
     */
    void fetchLastForecast(String before, Integer size);

    /**
     * 初始化计算历史数据命中
     */
    void batchCalcPl3Hit();

    /**
     * 计算增量历史数据全名命中
     */
    void calcIncrAllHit();

    /**
     * 初始化计算专家历史命中率
     */
    void initCalcMasterRate();

    /**
     * 初始计算增量专家历史命中率
     */
    void initIncrCalcRate();

    /**
     * 初始化计算历史专家排名
     */
    void initCalcMasterRank();

    /**
     * 组合推荐分析计算
     */
    Pl3ComRecommendVo comRecommendAna(Pl3ComSelectedCmd command);

    /**
     * 基于整体统计计算号码推荐
     */
    Pl3DkRecommendVo dkRecommend(DkRecommendCmd cmd);

    /**
     * 结合分类预测计算指数
     */
    N3ItemCensusDo calcItemCensus(String period);

    /**
     * 组合推荐命中计算
     *
     * @param period 推荐期号
     */
    void comRecommendCalc(String period);

    /**
     * 从VIP统计和分类统计中计算胆码和杀码
     */
    DanKillCalcResult calculateDanKill(String period, ChartType type);

    /**
     * 增量分析
     */
    N3DifferAnalyzeVo differAnalyze(String period);

    /**
     * 添加今日要点信息
     */
    void addPl3Pivot(N3TodayPivotCmd command);

    /**
     * 计算今日要点命中
     */
    void calcPivotHit(String period);

    /**
     * 双胆标记
     */
    void calcForecastMark(String period);
}
