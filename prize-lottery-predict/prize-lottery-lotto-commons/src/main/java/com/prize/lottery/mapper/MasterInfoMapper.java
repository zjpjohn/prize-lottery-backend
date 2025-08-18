package com.prize.lottery.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.po.master.*;
import com.prize.lottery.value.MasterValue;
import com.prize.lottery.vo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MasterInfoMapper {

    int addMasterInfo(MasterInfoPo masterInfo);

    int addMasterList(List<MasterInfoPo> masters);

    int editMasterInfo(MasterInfoPo masterInfo);

    int editMasterInfoList(List<MasterInfoPo> masters);

    int accumBatchMasters(List<MasterInfoPo> masters);

    int hasMasterInfo();

    int hasTypeMasterInfo(@Param("type") LotteryEnum type);

    MasterInfoPo getMasterInfoById(Long id);

    MasterValue getMasterValue(String masterId);

    MasterInfoPo getMasterInfoByPhone(String phone);

    MasterInfoPo getMasterInfoBySeq(@Param("seq") String seq, @Param("detail") Boolean detail);

    List<MasterInfoVo> getMasterInfoList(PageCondition condition);

    int countMasterInfos(PageCondition condition);

    List<MasterInfoPo> matchMasterListByName(@Param("name") String name, @Param("limit") Integer limit);

    List<MasterInfoPo> getMastersLikeName(PageCondition condition);

    int countMastersLikeName(PageCondition condition);

    List<MasterInfoPo> getHotMasterList(@Param("limit") Integer limit);

    int addMasterLotteries(List<MasterLotteryPo> masterLotteries);

    int saveMasterLottery(MasterLotteryPo masterLottery);

    int editMasterLottery(MasterLotteryPo masterLottery);

    int editMasterLotteries(List<MasterLotteryPo> masterLotteries);

    int editMasterLatestPeriod(MasterLotteryPo masterLottery);

    MasterLotteryPo getMasterLotteryById(Long id);

    MasterLotteryPo getMasterLotteryByUk(@Param("type") String type, @Param("masterId") String masterId);

    int existMasterLottery(@Param("masterId") String masterId, @Param("type") String type);

    List<MasterLotteryPo> getSourceMasterLotteries(@Param("type") String type, @Param("source") Integer source);

    List<MasterLotteryPo> getMasterEnabledLotteries(String masterId);

    List<MasterInfoPo> getAllICaiMasters();

    int addMasterBrowse(MasterBrowsePo masterBrowse);

    int hasBrowsedMaster(MasterBrowsePo masterBrowse);

    List<MasterBrowsePo> getLottoBrowseList(PageCondition condition);

    int countLottoBrowses(PageCondition condition);

    int addMasterSubscribe(MasterSubscribePo masterSubscribe);

    int addMasterSubscribes(List<MasterSubscribePo> subscribes);

    int editMasterSubscribe(MasterSubscribePo subscribe);

    int delMasterSubscribe(Long id);

    MasterSubscribePo getMasterSubscribeById(Long id);

    MasterSubscribePo getMasterSubscribeUk(@Param("userId") Long userId,
                                           @Param("masterId") String masterId,
                                           @Param("type") String type);

    int hasSubscribeMaster(MasterSubscribePo subscribe);

    List<MasterSubscribeVo> getSubscribeMastersList(PageCondition condition);

    Integer countSubscribeMasters(PageCondition condition);

    int addMasterFocus(List<MasterFocusPo> items);

    int removeMasterFocus(Long id);

    int hasFocusMaster(@Param("userId") Long userId, @Param("masterId") String masterId);

    MasterFocusPo getFocusMaster(@Param("userId") Long userId, @Param("masterId") String masterId);

    List<MasterFocusVo> getUserMasterFocusList(PageCondition condition);

    int countUserMasterFocus(PageCondition condition);

    RecommendMasterVo getTypedRecommendMaster(@Param("type") String type, @Param("channel") String channel);

    List<BrowseRecordVo> getRecentBrowseRecords(@Param("userId") Long userId,
                                                @Param("type") String type,
                                                @Param("source") Integer source,
                                                @Param("current") LocalDateTime current,
                                                @Param("start") LocalDateTime start);

    int countRecentBrowseRecords(@Param("userId") Long userId,
                                 @Param("type") String type,
                                 @Param("source") Integer source,
                                 @Param("current") LocalDateTime current,
                                 @Param("start") LocalDateTime start);

    List<BrowseRecordVo> getUserBrowseRecords(PageCondition condition);

    int countUserBrowseRecords(PageCondition condition);

    List<MasterBrowsePo> getUserForecastBrowses(@Param("userId") Long userId,
                                                @Param("period") String period,
                                                @Param("type") String type);

    int masterBrowseAmount(@Param("period") String period,
                           @Param("type") String type,
                           @Param("source") Integer source,
                           @Param("sourceId") String sourceId);

    int materBrowseSourceAmount(@Param("type") String type, @Param("source") Integer source);

    List<String> getExistTypeMasters(@Param("type") LotteryEnum type, @Param("masterIds") List<String> masterIds);

    List<MasterInfoPo> getExistMasters(@Param("masterIds") List<String> masterIds);

    int delMasterBrowses(@Param("type") LotteryEnum type, @Param("masterIds") List<String> masterIds);

    int delMasterSubscribes(@Param("type") LotteryEnum type, @Param("masterIds") List<String> masterIds);

    int delLotteryMasters(@Param("type") LotteryEnum type, @Param("masterIds") List<String> masterIds);

    int addMasterEvicts(List<MasterEvictPo> evicts);

    List<MasterEvictPo> getMasterEvicts(@Param("type") LotteryEnum type);

    int delMasterEvicts(@Param("type") LotteryEnum type);

    List<String> getAllMasterIds();

    List<String> getTypeMasterIds(@Param("type") LotteryEnum type);

}
