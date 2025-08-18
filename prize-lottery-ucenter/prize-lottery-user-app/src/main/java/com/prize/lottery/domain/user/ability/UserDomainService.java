package com.prize.lottery.domain.user.ability;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.event.publisher.DomainEventPublisher;
import com.prize.lottery.application.command.dto.BaseUserAuthCmd;
import com.prize.lottery.domain.channel.event.ChannelInviteEvent;
import com.prize.lottery.domain.user.event.InviteRewardEvent;
import com.prize.lottery.domain.user.factory.UserInfoFactory;
import com.prize.lottery.domain.user.model.UserBalance;
import com.prize.lottery.domain.user.model.UserInfo;
import com.prize.lottery.domain.user.model.UserInvite;
import com.prize.lottery.domain.user.repository.IUserBalanceRepository;
import com.prize.lottery.domain.user.repository.IUserInfoRepository;
import com.prize.lottery.domain.user.repository.IUserInviteRepository;
import com.prize.lottery.domain.user.repository.IUserLoginRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDomainService {

    private final UserInfoFactory        userInfoFactory;
    private final IUserInfoRepository    userInfoRepository;
    private final IUserLoginRepository   userLoginRepository;
    private final IUserBalanceRepository userBalanceRepository;
    private final IUserInviteRepository  userInviteRepository;

    public Pair<UserInfo, UserInvite> userRegister(BaseUserAuthCmd command) {
        //用户邀请码校验
        command.checkInviteCode();
        //保存用户信息
        UserInfo                  userInfo  = userInfoFactory.create(command);
        Aggregate<Long, UserInfo> aggregate = AggregateFactory.create(userInfo);
        userInfoRepository.save(aggregate);
        //创建用户余额账户
        userBalanceRepository.save(new UserBalance(userInfo.getId()));
        //创建邀请账户信息
        UserInvite userInvite = userInfoFactory.createInvite(userInfo.getId());
        AggregateFactory.create(userInvite).save(userInviteRepository::save);
        //设备已注册绑定不发布邀请事件
        if (userLoginRepository.hasBindDevice(command.getDeviceId())) {
            return Pair.of(userInfo, userInvite);
        }
        //用户邀请，发布用户被邀请奖励事件
        if (command.isUserShare()) {
            InviteRewardEvent inviteEvent = new InviteRewardEvent(userInfo.getId(), command.getInvite());
            DomainEventPublisher.publish(inviteEvent);
            return Pair.of(userInfo, userInvite);
        }
        //渠道邀请发布渠道邀请事件
        if (command.isChannelShare()) {
            ChannelInviteEvent channelEvent = new ChannelInviteEvent(userInfo.getId(), command.getInvite());
            DomainEventPublisher.publish(channelEvent);
        }
        return Pair.of(userInfo, userInvite);
    }

}
