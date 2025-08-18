package com.prize.lottery.application.query.service;


import com.cloud.arch.page.Page;
import com.cloud.arch.page.PageQuery;
import com.prize.lottery.application.query.dto.Kl8LottoMasterQuery;
import com.prize.lottery.application.query.dto.Kl8RankQuery;
import com.prize.lottery.application.query.dto.SubscribeQuery;
import com.prize.lottery.enums.Kl8Channel;
import com.prize.lottery.po.kl8.Kl8IcaiInfoPo;
import com.prize.lottery.po.kl8.Kl8MasterInfoPo;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.ICaiRankedDataVo;
import com.prize.lottery.vo.LotteryMasterVo;
import com.prize.lottery.vo.kl8.Kl8FullCensusVo;
import com.prize.lottery.vo.kl8.Kl8MasterDetail;
import com.prize.lottery.vo.kl8.Kl8MasterRankVo;
import com.prize.lottery.vo.kl8.Kl8MasterSubscribeVo;

import java.util.List;

public interface IKl8LottoQueryService {

    Period latestPeriod();

    List<String> lastPeriods(Integer size);

    /**
     * 最新排名期号
     */
    String latestRank();

    /**
     * 快乐8预测专家详情
     *
     * @param masterId 专家标识
     * @param userId   用户标识
     */
    Kl8MasterDetail queryMasterDetail(String masterId, Long userId);

    /**
     * 分页查询快乐8预测专家
     *
     * @param query 查询条件
     */
    Page<Kl8MasterInfoPo> getKl8MasterList(PageQuery query);

    /**
     * 查询排序的预测数据
     *
     * @param query 查询条件
     */
    Page<ICaiRankedDataVo> getKl8RankedDataList(Kl8RankQuery query);

    /**
     * 分页查询专家排行榜
     *
     * @param query 查询条件
     */
    Page<Kl8MasterRankVo> getKl8MasterRankList(PageQuery query);

    /**
     * 查询专家预测历史数据
     *
     * @param masterId 专家标识
     */
    List<Kl8IcaiInfoPo> getMasterForecastHistories(String masterId);

    /**
     * 分页查询用户订阅快乐8预测专家
     *
     * @param query 查询条件
     */
    Page<Kl8MasterSubscribeVo> getKl8MasterSubscribeList(SubscribeQuery query);

    /**
     * 查询全量统计图表
     *
     * @param channel 数据指标字段
     * @param period  预测期号
     */
    Kl8FullCensusVo fullChart(Kl8Channel channel, String period);

    /**
     * 分页查询快乐8排名专家
     *
     * @param query 查询条件
     */
    Page<LotteryMasterVo> getKl8LotteryMasters(Kl8LottoMasterQuery query);
}
