package com.prize.lottery.application.query.impl;

import com.cloud.arch.page.Page;
import com.google.common.collect.Lists;
import com.prize.lottery.application.query.IChannelInfoQueryService;
import com.prize.lottery.application.query.dto.ChannelListQuery;
import com.prize.lottery.application.query.executor.ChannelAnnounceQueryExecutor;
import com.prize.lottery.application.query.executor.ChannelRemindQueryExecutor;
import com.prize.lottery.application.query.vo.ChannelMessageVo;
import com.prize.lottery.domain.facade.IUserInfoFacade;
import com.prize.lottery.domain.facade.dto.UserInfo;
import com.prize.lottery.infrast.persist.mapper.ChannelInfoMapper;
import com.prize.lottery.infrast.persist.po.ChannelInfoPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChannelInfoQueryService implements IChannelInfoQueryService {

    private final ChannelInfoMapper            channelMapper;
    private final ChannelAnnounceQueryExecutor announceQueryExecutor;
    private final ChannelRemindQueryExecutor remindQueryExecutor;
    private final IUserInfoFacade            userInfoFacade;

    @Override
    public Page<ChannelInfoPo> channelList(ChannelListQuery query) {
        return query.from().count(channelMapper::countChannels).query(channelMapper::getChannelList);
    }

    @Override
    public ChannelInfoPo channelDetail(String channel) {
        return channelMapper.getChannelByUk(channel);
    }

    @Override
    public List<ChannelInfoPo> getUsingTypedChannels(Integer type) {
        return channelMapper.getUsingTypedChannels(type);
    }

    @Override
    public List<ChannelMessageVo> messageList(Long userId) {
        UserInfo userInfo = userInfoFacade.queryUserInfo(userId);
        CompletableFuture<List<ChannelMessageVo>> announceAsync =
                CompletableFuture.supplyAsync(() -> announceQueryExecutor.execute(userId, userInfo.getScopes()));
        CompletableFuture<List<ChannelMessageVo>> remindAsync =
                CompletableFuture.supplyAsync(() -> remindQueryExecutor.execute(userId, userInfo.getScopes()));
        List<ChannelMessageVo> messages = Lists.newArrayList();
        messages.addAll(announceAsync.join());
        messages.addAll(remindAsync.join());
        return messages;
    }

}
