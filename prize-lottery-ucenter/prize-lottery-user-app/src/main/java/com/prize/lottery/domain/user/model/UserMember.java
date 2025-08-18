package com.prize.lottery.domain.user.model;

import com.cloud.arch.aggregate.AggregateRoot;
import com.cloud.arch.utils.IdWorker;
import com.prize.lottery.application.consumer.event.UserMemberEvent;
import com.prize.lottery.domain.user.valobj.MemberLog;
import com.prize.lottery.infrast.persist.enums.MemberState;
import com.prize.lottery.infrast.persist.enums.TimeUnit;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserMember implements AggregateRoot<Long> {

    private static final long serialVersionUID = 1663014631288676443L;

    private Long          userId;
    private Integer       times;
    private LocalDateTime expireAt;
    private LocalDateTime lastExpire;
    private MemberState   state;
    private LocalDateTime renewTime;
    private Integer       version;
    private MemberLog     log;

    /**
     * 后台手动开通会员
     */
    public UserMember(Long userId, String packNo, String packName, Long payed, String channel, TimeUnit timeUnit) {
        this.userId    = userId;
        this.version   = 0;
        this.times     = 1;
        this.state     = MemberState.NORMAL;
        this.renewTime = LocalDateTime.now();
        this.expireAt  = timeUnit.expireAt(LocalDateTime.now());
        this.log       = buildLog(packNo, packName, payed, channel, timeUnit, LocalDateTime.now());
    }

    /**
     * 续费时创建会员信息
     */
    public UserMember(UserMemberEvent event, TimeUnit timeUnit) {
        this.userId    = event.getUserId();
        this.version   = 0;
        this.times     = 1;
        this.state     = MemberState.NORMAL;
        this.renewTime = LocalDateTime.now();
        this.expireAt  = timeUnit.expireAt(LocalDateTime.now());
        this.log       = buildLog(event, timeUnit, LocalDateTime.now());
    }

    /**
     * 抽奖获得会员创建会员信息
     */
    public UserMember(Long userId, Integer duration) {
        this.userId    = userId;
        this.version   = 0;
        this.times     = 1;
        this.state     = MemberState.NORMAL;
        this.renewTime = LocalDateTime.now();
        this.expireAt  = LocalDateTime.now().plusDays(duration);
    }

    /**
     * 抽奖获得会员
     */
    public void renewMember(Integer duration) {
        this.times++;
        this.renewTime  = LocalDateTime.now();
        this.lastExpire = this.expireAt;
        LocalDateTime expireStart = expireStart(lastExpire);
        this.expireAt = expireStart.plusDays(duration);
    }

    /**
     * 续费会员
     */
    public void renewMember(UserMemberEvent event, TimeUnit timeUnit) {
        this.times++;
        this.renewTime  = LocalDateTime.now();
        this.lastExpire = this.expireAt;
        LocalDateTime expireStart = expireStart(lastExpire);
        this.expireAt = timeUnit.expireAt(expireStart);
        this.log      = buildLog(event, timeUnit, expireStart);
    }

    /**
     * 后台手动开通会员
     */
    public void renewMember(String packNo, String packName, Long payed, String channel, TimeUnit timeUnit) {
        this.times++;
        this.renewTime  = LocalDateTime.now();
        this.lastExpire = this.expireAt;
        LocalDateTime expireStart = expireStart(lastExpire);
        this.expireAt = timeUnit.expireAt(expireStart);
        this.log      = buildLog(packNo, packName, payed, channel, timeUnit, expireStart);
    }

    public UserMember expire() {
        UserMember member = new UserMember();
        member.setUserId(userId);
        member.setState(MemberState.EXPIRED);
        return member;
    }

    /**
     * 计算续费开始时间
     */
    private LocalDateTime expireStart(LocalDateTime lastExpire) {
        LocalDateTime current = LocalDateTime.now();
        return lastExpire.isBefore(current) ? current : lastExpire;
    }

    /**
     * 构建会员记录日志
     */
    private MemberLog buildLog(UserMemberEvent event, TimeUnit timeUnit, LocalDateTime expireStart) {
        MemberLog memberLog = new MemberLog();
        memberLog.setType(1);
        memberLog.setTimeUnit(timeUnit);
        memberLog.setExpireStart(expireStart);
        memberLog.setExpireEnd(this.expireAt);
        memberLog.setOrderNo(event.getOrderNo());
        memberLog.setPackNo(event.getPackNo());
        memberLog.setPackName(event.getName());
        memberLog.setPayed(event.getPayed());
        memberLog.setChannel(event.getChannel());
        return memberLog;
    }

    private MemberLog buildLog(String packNo,
                               String packName,
                               Long payed,
                               String channel,
                               TimeUnit timeUnit,
                               LocalDateTime expireStart) {
        MemberLog memberLog = new MemberLog();
        memberLog.setType(2);
        memberLog.setPayed(payed);
        memberLog.setPackNo(packNo);
        memberLog.setPackName(packName);
        memberLog.setChannel(channel);
        memberLog.setTimeUnit(timeUnit);
        memberLog.setExpireStart(expireStart);
        memberLog.setExpireEnd(this.expireAt);
        memberLog.setOrderNo(String.valueOf(IdWorker.nextId()));
        return memberLog;
    }

    @Override
    public boolean isNew() {
        return this.version == 0;
    }

    @Override
    public void setId(Long id) {
        this.userId = id;
    }

    @Override
    public Long getId() {
        return this.userId;
    }
}
