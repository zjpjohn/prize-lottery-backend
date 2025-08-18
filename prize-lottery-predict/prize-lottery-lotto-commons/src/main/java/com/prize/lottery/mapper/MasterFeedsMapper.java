package com.prize.lottery.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.po.master.MasterFeedsPo;
import com.prize.lottery.vo.MasterFeedVo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public interface MasterFeedsMapper {

    int addMasterFeeds(List<MasterFeedsPo> feedList);

    int editMasterFeeds(MasterFeedsPo feeds);

    MasterFeedsPo getMasterFeedsByUk(@Param("type") LotteryEnum type, @Param("masterId") String masterId);

    @MapKey("type")
    List<Map<String, String>> getFeedsMaxPeriods();

    String getFeedsMaxPeriod(String type);

    int removeMasterFeeds(@Param("type") LotteryEnum type,
                          @Param("rate") Double rate,
                          @Param("hit") Integer hit,
                          @Param("time") LocalDateTime time);

    int removeTypedFeedsLtPeriods(List<Map<String, String>> list);

    int removeFeedsLtPeriod(@Param("type") String type, @Param("period") String period);

    int removeFeedsRecord(@Param("type") String type, @Param("masterId") String masterId);

    List<MasterFeedVo> getMasterFeedList(PageCondition condition);

    int countMasterFeeds(PageCondition condition);

}

