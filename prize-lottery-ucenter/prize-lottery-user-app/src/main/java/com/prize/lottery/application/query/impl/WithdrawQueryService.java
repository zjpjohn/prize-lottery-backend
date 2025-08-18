package com.prize.lottery.application.query.impl;

import com.cloud.arch.page.Page;
import com.cloud.arch.utils.CollectionUtils;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.assembler.WithdrawAssembler;
import com.prize.lottery.application.query.IWithdrawQueryService;
import com.prize.lottery.application.query.dto.AdmWithdrawQuery;
import com.prize.lottery.application.query.dto.AppWithdrawQuery;
import com.prize.lottery.application.query.dto.LevelListQuery;
import com.prize.lottery.application.query.vo.*;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.TransferScene;
import com.prize.lottery.infrast.persist.mapper.*;
import com.prize.lottery.infrast.persist.po.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class WithdrawQueryService implements IWithdrawQueryService {

    private final UserInfoMapper     userInfoMapper;
    private final WithdrawRuleMapper withdrawRuleMapper;
    private final UserWithdrawMapper userWithdrawMapper;
    private final UserInviteMapper   userInviteMapper;
    private final ExpertAcctMapper   expertAcctMapper;
    private final WithdrawAssembler  withdrawAssembler;

    @Override
    public List<Map<String, Object>> transferScenes() {
        return Arrays.stream(TransferScene.values()).map(TransferScene::toMap).collect(Collectors.toList());
    }

    @Override
    public WithdrawRulePo getWithdrawRule(Long id) {
        return withdrawRuleMapper.getWithdrawRuleById(id);
    }

    @Override
    public List<WithdrawRulePo> getWithdrawRules() {
        return withdrawRuleMapper.getAllWithdrawRules();
    }

    @Override
    public WithdrawRuleVo sceneUsingRule(String scene) {
        WithdrawRulePo rule = withdrawRuleMapper.getSceneUsingRule(scene);
        Assert.notNull(rule, ResponseHandler.WITHDRAW_RULE_NONE);
        return withdrawAssembler.toVo(rule);
    }

    @Override
    public Page<AppWithdrawVo> getAppUserWithdrawList(AppWithdrawQuery query) {
        return query.from()
                    .count(userWithdrawMapper::countUserWithdraws)
                    .query(userWithdrawMapper::getUserWithdrawList)
                    .map(withdrawAssembler::toWithdrawVo);
    }

    @Override
    public Page<AppWithdrawVo> getAppAgentWithdrawList(AppWithdrawQuery query) {
        return query.from()
                    .count(userInviteMapper::countAgentWithdraws)
                    .query(userInviteMapper::getAgentWithdrawList)
                    .map(withdrawAssembler::toWithdrawVo);
    }

    @Override
    public Page<AppWithdrawVo> getAppExpertWithdrawList(AppWithdrawQuery query) {
        return query.from()
                    .count(expertAcctMapper::countExpertWithdraws)
                    .query(expertAcctMapper::getExpertWithdrawList)
                    .map(withdrawAssembler::toWithdrawVo);
    }

    @Override
    public Page<UserWithdrawVo> getAdmUserWithdrawList(AdmWithdrawQuery query) {
        return query.from()
                    .count(userWithdrawMapper::countUserWithdraws)
                    .query(userWithdrawMapper::getUserWithdrawList)
                    .flatMap(list -> {
                        List<Long>            userIdList = CollectionUtils.distinctList(list, UserWithdrawPo::getUserId);
                        List<UserInfoPo>      users      = userInfoMapper.getUserInfoByIdList(userIdList);
                        Map<Long, UserInfoPo> userMap    = CollectionUtils.toMap(users, UserInfoPo::getId);
                        return list.stream().map(withdraw -> {
                            UserWithdrawVo withdrawVo = withdrawAssembler.toVo(withdraw);
                            UserInfoPo     userInfo   = userMap.get(withdrawVo.getUserId());
                            withdrawVo.setNickname(userInfo.getNickname());
                            withdrawVo.setPhone(userInfo.getPhone());
                            return withdrawVo;
                        }).collect(Collectors.toList());
                    });
    }

    @Override
    public Page<AgentWithdrawVo> getAdmAgentWithdrawList(AdmWithdrawQuery query) {
        return query.from()
                    .count(userInviteMapper::countAgentWithdraws)
                    .query(userInviteMapper::getAgentWithdrawList)
                    .flatMap(list -> {
                        List<Long>            userIds = CollectionUtils.distinctList(list, AgentWithdrawPo::getUserId);
                        List<UserInfoPo>      users   = userInfoMapper.getUserInfoByIdList(userIds);
                        Map<Long, UserInfoPo> userMap = CollectionUtils.toMap(users, UserInfoPo::getId);
                        return list.stream().map(withdraw -> {
                            AgentWithdrawVo withdrawVo = withdrawAssembler.toVo(withdraw);
                            UserInfoPo      userInfo   = userMap.get(withdraw.getUserId());
                            withdrawVo.setNickname(userInfo.getNickname());
                            withdrawVo.setPhone(userInfo.getPhone());
                            return withdrawVo;
                        }).collect(Collectors.toList());
                    });
    }

    @Override
    public Page<ExpertWithdrawVo> getAdmExpertWithdrawList(AdmWithdrawQuery query) {
        return query.from()
                    .count(expertAcctMapper::countExpertWithdraws)
                    .query(expertAcctMapper::getExpertWithdrawList)
                    .flatMap(list -> {
                        List<Long>            userIds = CollectionUtils.distinctList(list, ExpertWithdrawPo::getUserId);
                        List<UserInfoPo>      users   = userInfoMapper.getUserInfoByIdList(userIds);
                        Map<Long, UserInfoPo> userMap = CollectionUtils.toMap(users, UserInfoPo::getId);
                        return list.stream().map(withdraw -> {
                            ExpertWithdrawVo withdrawVo = withdrawAssembler.toVo(withdraw);
                            UserInfoPo       userInfo   = userMap.get(withdraw.getUserId());
                            withdrawVo.setNickname(userInfo.getNickname());
                            withdrawVo.setPhone(userInfo.getPhone());
                            return withdrawVo;
                        }).collect(Collectors.toList());
                    });
    }

    @Override
    public UserWithdrawVo getUserWithdraw(String seqNo) {
        UserWithdrawPo withdraw = userWithdrawMapper.getWithdrawBySeqNo(seqNo);
        Assert.notNull(withdraw, ResponseHandler.WITHDRAW_RECORD_NONE);

        UserWithdrawVo withdrawVo = withdrawAssembler.toVo(withdraw);
        userInfoMapper.getUserInfoById(withdraw.getUserId()).ifPresent(user -> {
            withdrawVo.setNickname(user.getNickname());
            withdrawVo.setPhone(user.getPhone());
        });
        return withdrawVo;
    }

    @Override
    public AgentWithdrawVo getAgentWithdraw(String seqNo) {
        AgentWithdrawPo withdraw = userInviteMapper.getWithdrawBySeqNo(seqNo);
        Assert.notNull(withdraw, ResponseHandler.WITHDRAW_RECORD_NONE);

        AgentWithdrawVo withdrawVo = withdrawAssembler.toVo(withdraw);
        userInfoMapper.getUserInfoById(withdraw.getUserId()).ifPresent(user -> {
            withdrawVo.setNickname(user.getNickname());
            withdrawVo.setPhone(user.getPhone());
        });
        return withdrawVo;
    }

    @Override
    public ExpertWithdrawVo getExpertWithdraw(String seqNo) {
        ExpertWithdrawPo withdraw = expertAcctMapper.getWithdrawBySeqNo(seqNo);
        Assert.notNull(withdraw, ResponseHandler.WITHDRAW_RECORD_NONE);

        ExpertWithdrawVo withdrawVo = withdrawAssembler.toVo(withdraw);
        userInfoMapper.getUserInfoById(withdraw.getUserId()).ifPresent(user -> {
            withdrawVo.setNickname(user.getNickname());
            withdrawVo.setPhone(user.getPhone());
        });
        return withdrawVo;
    }

    @Override
    public Page<WithdrawLevelPo> withdrawLevelList(LevelListQuery query) {
        return query.from()
                    .count(withdrawRuleMapper::countWithdrawLevels)
                    .query(withdrawRuleMapper::getWithdrawLevelList);
    }

    @Override
    public WithdrawLevelPo withdrawLevel(Long id) {
        return withdrawRuleMapper.getWithdrawLevel(id);
    }

    @Override
    public WithdrawLevelPo sceneUsingLevel(String scene) {
        return withdrawRuleMapper.getSceneUsingLevel(scene);
    }

}
