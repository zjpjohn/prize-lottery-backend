package com.prize.lottery.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.po.kl8.Kl8MasterInfoPo;
import com.prize.lottery.po.kl8.Kl8MasterRankPo;
import com.prize.lottery.po.kl8.Kl8MasterRatePo;
import com.prize.lottery.vo.LotteryMasterVo;
import com.prize.lottery.vo.kl8.Kl8MasterDetail;
import com.prize.lottery.vo.kl8.Kl8MasterRankVo;
import com.prize.lottery.vo.kl8.Kl8MasterSubscribeVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Kl8MasterMapper {

    int addKl8Masters(List<Kl8MasterInfoPo> kl8Masters);

    int editKl8Masters(List<Kl8MasterInfoPo> kl8Masters);

    int updateKl8MasterLatest(List<Kl8MasterInfoPo> kl8Masters);

    Kl8MasterInfoPo getKl8MasterInfo(String masterId);

    int existKl8MasterInfo(String masterId);

    Kl8MasterInfoPo getKl8MastersByThirdId(Long thirdId);

    List<Kl8MasterInfoPo> getAllKl8Masters();

    List<Kl8MasterInfoPo> getKl8MasterList(PageCondition condition);

    int countKl8MasterList(PageCondition condition);

    int addKl8MasterRates(List<Kl8MasterRatePo> rates);

    int hasExistRatePeriod(String period);

    int hasExistRankPeriod(String period);

    int addKl8MasterRanks(List<Kl8MasterRankPo> ranks);

    List<Kl8MasterRankVo> getKl8MasterRankList(PageCondition condition);

    int countKl8MasterRanks(PageCondition condition);

    String latestKl8RankPeriod();

    List<String> getUnRankedMasterPeriods();

    List<Kl8MasterRatePo> getKl8MasterRates(String period);

    String latestKl8RatePeriod();

    Kl8MasterDetail getKl8MasterDetail(String masterId);

    List<Kl8MasterSubscribeVo> getKl8MasterSubscribeList(PageCondition condition);

    int countKl8MasterSubscribes(PageCondition condition);

    List<LotteryMasterVo> getKl8LottoMasterList(PageCondition condition);

    int countKl8LottoMasters(PageCondition condition);
}
