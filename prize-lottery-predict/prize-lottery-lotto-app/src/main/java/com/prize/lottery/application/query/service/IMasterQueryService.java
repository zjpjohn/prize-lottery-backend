package com.prize.lottery.application.query.service;


import com.cloud.arch.page.Page;
import com.prize.lottery.application.query.dto.*;
import com.prize.lottery.application.vo.LottoBrowseVo;
import com.prize.lottery.application.vo.MasterInfoDetailVo;
import com.prize.lottery.application.vo.RecentBrowseRecordVo;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.value.MasterValue;
import com.prize.lottery.vo.*;

import java.util.List;

public interface IMasterQueryService {

    /**
     * 查询专家详情
     *
     * @param masterId 专家标识
     */
    MasterInfoDetailVo getMasterInfoDetail(String masterId, Long userId, Integer search);

    /**
     * 用户查询自己作为预测专家信息
     *
     * @param userId 用户标识
     */
    MasterInfoDetailVo getMineMasterDetail(Long userId);

    /**
     * 管理端查询专家详情
     *
     * @param masterId 专家标识
     */
    MasterInfoDetailVo getAdmMasterInfoVo(String masterId);

    /**
     * 分页查询专家信息
     *
     * @param query 分页查询条件
     */
    Page<MasterInfoVo> getMasterInfoList(MasterListQuery query);

    /**
     * 分页查询订阅专家
     *
     * @param query 查询条件
     */
    Page<MasterSubscribeVo> getUserSubscribeMasters(FocusMasterQuery query);

    /**
     * 分页查询用户关注专家
     *
     * @param query 查询条件
     */
    Page<MasterFocusVo> getUserFocusMasters(FocusMasterQuery query);

    /**
     * 推荐关注专家
     */
    List<RecommendMasterVo> getRecommendMasters();

    /**
     * 搜索专家
     *
     * @param query 查询条件
     */
    Page<MasterValue> matchMasterQuery(MatchNameQuery query);

    /**
     * 搜索专家
     *
     * @param name  专家名称
     * @param limit 返回条数
     */
    List<MasterValue> matchMasterList(String name, Integer limit);

    /**
     * 查询热门搜索专家
     *
     * @param limit 返回条数
     */
    List<MasterValue> getHotMasterList(Integer limit);

    /**
     * 分页查询浏览记录
     */
    Page<BrowseRecordVo> getUserBrowseRecords(UserBrowseQuery query);

    /**
     * 查询用户最近浏览的专家
     *
     * @param userId 用户标识
     * @param type   彩票类型
     */
    RecentBrowseRecordVo getRecentBrowseMasters(Long userId, LotteryEnum type);

    /**
     * 分页查询专家预测喜讯
     *
     * @param query 查询条件
     */
    Page<MasterGladVo> getMasterGladList(MasterGladQuery query);

    /**
     * 查询最新的预测专家喜讯
     */
    List<MasterGladVo> getAppGladList();

    /**
     * 移动端查询信息流列表
     */
    List<MasterFeedVo> getAppMasterFeeds(FeedAppQuery query);

    /**
     * 管理端分页查询信息流
     */
    Page<MasterFeedVo> getMasterFeedList(FeedAdmQuery query);

    /**
     * 用户浏览记录列表
     */
    Page<LottoBrowseVo> lottoBrowseList(LottoBrowseQuery query);

}
