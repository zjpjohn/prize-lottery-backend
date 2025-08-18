package com.prize.lottery.application.query.service.impl;

import com.cloud.arch.page.Page;
import com.cloud.arch.page.PageCondition;
import com.cloud.arch.utils.CollectionUtils;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.assembler.MasterInfoAssembler;
import com.prize.lottery.application.query.dto.*;
import com.prize.lottery.application.query.executor.MasterDetailQueryExe;
import com.prize.lottery.application.query.executor.MineMasterQueryExe;
import com.prize.lottery.application.query.service.IMasterQueryService;
import com.prize.lottery.application.vo.LottoBrowseVo;
import com.prize.lottery.application.vo.MasterInfoDetailVo;
import com.prize.lottery.application.vo.RecentBrowseRecordVo;
import com.prize.lottery.dto.UserInfoRepo;
import com.prize.lottery.enums.BrowseType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.facade.ICloudUserAccountFacade;
import com.prize.lottery.mapper.MasterFeedsMapper;
import com.prize.lottery.mapper.MasterGladMapper;
import com.prize.lottery.mapper.MasterInfoMapper;
import com.prize.lottery.po.master.MasterBrowsePo;
import com.prize.lottery.po.master.MasterInfoPo;
import com.prize.lottery.po.master.MasterLotteryPo;
import com.prize.lottery.value.MasterValue;
import com.prize.lottery.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MasterQueryService implements IMasterQueryService {

    private final MasterInfoMapper        masterInfoMapper;
    private final MasterInfoAssembler     masterInfoAssembler;
    private final MasterDetailQueryExe    masterDetailQueryExe;
    private final MineMasterQueryExe      mineMasterQueryExe;
    private final MasterGladMapper        masterGladMapper;
    private final MasterFeedsMapper       masterFeedsMapper;
    private final ICloudUserAccountFacade userAccountFacade;

    /**
     * 查询专家详情
     *
     * @param masterId 专家标识
     */
    @Override
    public MasterInfoDetailVo getMasterInfoDetail(String masterId, Long userId, Integer search) {
        return masterDetailQueryExe.query(masterId, userId, search);
    }

    /**
     * 用户查询自己作为预测专家信息
     *
     * @param userId 用户标识
     */
    @Override
    public MasterInfoDetailVo getMineMasterDetail(Long userId) {
        return mineMasterQueryExe.query(userId);
    }

    /**
     * 管理端查询专家详情
     *
     * @param masterId 专家标识
     */
    @Override
    public MasterInfoDetailVo getAdmMasterInfoVo(String masterId) {
        //专家信息
        MasterInfoPo master = masterInfoMapper.getMasterInfoBySeq(masterId, true);
        Assert.notNull(master, ResponseHandler.MASTER_NONE);
        //专家开通彩种
        List<MasterLotteryPo> lotteries = masterInfoMapper.getMasterEnabledLotteries(masterId);

        return masterInfoAssembler.toVo(master, lotteries, null);
    }

    /**
     * @param query 分页查询条件
     */
    @Override
    public Page<MasterInfoVo> getMasterInfoList(MasterListQuery query) {
        return query.from()
                    .count(masterInfoMapper::countMasterInfos)
                    .query(masterInfoMapper::getMasterInfoList)
                    .forEach(master -> {
                        List<LotteryEnum> enables = LotteryEnum.enables(master.getEnable());
                        master.setLotteries(enables);
                    });
    }

    /**
     * 分页查询关注专家
     *
     * @param query 查询条件
     */
    @Override
    public Page<MasterSubscribeVo> getUserSubscribeMasters(FocusMasterQuery query) {
        return query.from()
                    .count(masterInfoMapper::countSubscribeMasters)
                    .query(masterInfoMapper::getSubscribeMastersList);
    }

    @Override
    public Page<MasterFocusVo> getUserFocusMasters(FocusMasterQuery query) {
        return query.from()
                    .count(masterInfoMapper::countUserMasterFocus)
                    .query(masterInfoMapper::getUserMasterFocusList);
    }

    /**
     * 推荐关注专家
     */
    @Override
    public List<RecommendMasterVo> getRecommendMasters() {
        return Arrays.stream(LotteryEnum.values())
                     .filter(LotteryEnum::recommended)
                     .parallel()
                     .map(e -> masterInfoMapper.getTypedRecommendMaster(e.getType(), e.getField()))
                     .filter(Objects::nonNull)
                     .collect(Collectors.toList());
    }

    /**
     * 搜索专家
     *
     * @param query 查询条件
     */
    @Override
    public Page<MasterValue> matchMasterQuery(MatchNameQuery query) {
        return query.from()
                    .count(masterInfoMapper::countMastersLikeName)
                    .query(masterInfoMapper::getMastersLikeName)
                    .map(master -> new MasterValue(master.getSeq(), master.getName(), master.getAvatar()));
    }

    /**
     * 搜索专家
     *
     * @param name  专家名称
     * @param limit 返回条数
     */
    @Override
    public List<MasterValue> matchMasterList(String name, Integer limit) {
        List<MasterInfoPo> masters = masterInfoMapper.matchMasterListByName(name, limit);
        if (CollectionUtils.isEmpty(masters)) {
            return Collections.emptyList();
        }
        return masters.stream()
                      .map(master -> new MasterValue(master.getSeq(), master.getName(), master.getAvatar()))
                      .collect(Collectors.toList());
    }

    /**
     * 查询热门搜索专家
     *
     * @param limit 返回条数
     */
    @Override
    public List<MasterValue> getHotMasterList(Integer limit) {
        List<MasterInfoPo> masters = masterInfoMapper.getHotMasterList(limit);
        return masters.stream()
                      .map(master -> new MasterValue(master.getSeq(), master.getName(), master.getAvatar()))
                      .collect(Collectors.toList());
    }

    /**
     * 分页查询浏览记录
     */
    @Override
    public Page<BrowseRecordVo> getUserBrowseRecords(UserBrowseQuery query) {
        return query.from()
                    .count(masterInfoMapper::countUserBrowseRecords)
                    .query(masterInfoMapper::getUserBrowseRecords);
    }

    /**
     * 查询用户最近浏览的专家
     *
     * @param userId 用户标识
     * @param type   彩票类型
     */
    @Override
    public RecentBrowseRecordVo getRecentBrowseMasters(Long userId, LotteryEnum type) {
        LocalDateTime        current  = LocalDateTime.now();
        LocalDateTime        start    = current.minusDays(7);
        int                  count    = masterInfoMapper.countRecentBrowseRecords(userId, type.value(), BrowseType.FORECAST.value(), current, start);
        RecentBrowseRecordVo recordVo = new RecentBrowseRecordVo();
        recordVo.setCount(count);
        if (count > 0) {
            List<BrowseRecordVo> records = masterInfoMapper.getRecentBrowseRecords(userId, type.value(), BrowseType.FORECAST.value(), current, start);
            recordVo.setRecords(records);
        }
        return recordVo;
    }

    /**
     * 分页查询专家预测喜讯
     *
     * @param query 查询条件
     */
    @Override
    public Page<MasterGladVo> getMasterGladList(MasterGladQuery query) {
        return query.from().count(masterGladMapper::countMasterGlads).query(masterGladMapper::getMasterGladList);
    }

    /**
     * 查询最新预测专家喜讯
     */
    @Override
    public List<MasterGladVo> getAppGladList() {
        return PageCondition.build(8).list(masterGladMapper::getMasterGladList);
    }

    /**
     * 移动端查询信息流列表
     */
    @Override
    public List<MasterFeedVo> getAppMasterFeeds(FeedAppQuery query) {
        return query.from().setParam("state", 1).list(masterFeedsMapper::getMasterFeedList);
    }

    /**
     * 管理端分页查询信息流
     */
    @Override
    public Page<MasterFeedVo> getMasterFeedList(FeedAdmQuery query) {
        return query.from().count(masterFeedsMapper::countMasterFeeds).query(masterFeedsMapper::getMasterFeedList);
    }

    @Override
    public Page<LottoBrowseVo> lottoBrowseList(LottoBrowseQuery query) {
        return query.from()
                    .count(masterInfoMapper::countLottoBrowses)
                    .query(masterInfoMapper::getLottoBrowseList)
                    .flatMap(browses -> {
                        List<Long>              userIds    = CollectionUtils.distinctList(browses, MasterBrowsePo::getUserId);
                        List<UserInfoRepo>      userList   = userAccountFacade.getUserList(userIds);
                        Map<Long, UserInfoRepo> userMap    = CollectionUtils.toMap(userList, UserInfoRepo::getUserId);
                        List<LottoBrowseVo>     browseList = masterInfoAssembler.toBrowseList(browses);
                        browseList.forEach(browse -> Optional.ofNullable(userMap.get(browse.getUserId()))
                                                             .map(UserInfoRepo::getPhone)
                                                             .ifPresent(browse::setPhone));
                        return browseList;
                    });
    }

}
