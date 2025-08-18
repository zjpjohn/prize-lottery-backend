package com.prize.lottery.domain.user.event;

import com.cloud.arch.event.annotations.Publish;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Publish
@NoArgsConstructor
@AllArgsConstructor
public class InviteRewardEvent {

    private Long   userId;
    private String invCode;

}
