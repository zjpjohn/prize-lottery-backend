package com.prize.lottery.domain.channel.event;

import com.cloud.arch.event.annotations.Publish;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Publish
@NoArgsConstructor
@AllArgsConstructor
public class ChannelInviteEvent {

    //用户标识
    private Long   userId;
    //渠道分享码
    private String code;

}
