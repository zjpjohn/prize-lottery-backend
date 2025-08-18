package com.prize.lottery.application.query.executor;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.query.dto.MessageAppQuery;
import com.prize.lottery.domain.facade.IUserInfoFacade;
import com.prize.lottery.domain.facade.dto.UserInfo;
import com.prize.lottery.infrast.error.ResponseErrorHandler;
import com.prize.lottery.infrast.persist.enums.CommonState;
import com.prize.lottery.infrast.persist.mapper.ChannelInfoMapper;
import com.prize.lottery.infrast.persist.po.ChannelInfoPo;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public abstract class AbsMessageExecutor<T> {

    protected final ChannelInfoMapper channelMapper;
    protected final IUserInfoFacade   userInfoFacade;

    public Page<T> execute(MessageAppQuery query) {
        UserInfo      userInfo = userInfoFacade.queryUserInfo(query.getUserId());
        List<Integer> scopes  = userInfo.getScopes();
        ChannelInfoPo channel = channelMapper.getChannelByUk(query.getChannel());
        //通知消息渠道判断
        Assert.state(channel != null, ResponseErrorHandler.CHANNEL_NOT_EXIST);
        //消息渠道状态判断
        Assert.state(channel.getState() == CommonState.USING,
                ResponseErrorHandler.CHANNEL_NO_PRIVILEGE);
        //消息渠道可见性判断
        Assert.state(scopes.contains(channel.getScope().getScope()),
                ResponseErrorHandler.CHANNEL_NO_PRIVILEGE);
        //分页查询
        return this.doExecute(query);
    }

    protected abstract Page<T> doExecute(MessageAppQuery query);
}
