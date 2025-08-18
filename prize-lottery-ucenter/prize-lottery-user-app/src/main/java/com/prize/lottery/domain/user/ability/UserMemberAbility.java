package com.prize.lottery.domain.user.ability;

import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.pack.model.aggregate.PackInfoDo;
import com.prize.lottery.domain.pack.repository.IPackInfoRepository;
import com.prize.lottery.domain.user.model.UserMember;
import com.prize.lottery.domain.user.repository.IUserInfoRepository;
import com.prize.lottery.domain.user.repository.IUserMemberRepository;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.PackState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserMemberAbility {

    private final IUserInfoRepository   userRepository;
    private final IPackInfoRepository   packRepository;
    private final IUserMemberRepository memberRepository;

    /**
     * 手动开通会员
     */
    public void manualMember(Long userId, String packNo, String channel) {
        Assert.state(userRepository.exist(userId), ResponseHandler.USER_INFO_NONE);
        PackInfoDo pack = packRepository.ofPackNo(packNo)
                                        .filter(p -> p.getState() == PackState.USING)
                                        .orElseThrow(ResponseHandler.PACK_NONE);
        memberRepository.ofUser(userId)
                        .map(agg -> agg.peek(member -> member.renewMember(pack.getSeqNo(), pack.getName(), pack.getDiscount(), channel, pack.getTimeUnit())))
                        .orElseGet(() -> AggregateFactory.create(new UserMember(userId, pack.getSeqNo(), pack.getName(), pack.getPrice(), channel, pack.getTimeUnit())))
                        .save(memberRepository::save);
    }

}
