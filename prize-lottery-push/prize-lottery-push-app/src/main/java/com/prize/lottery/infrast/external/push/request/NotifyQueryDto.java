package com.prize.lottery.infrast.external.push.request;

import java.time.LocalDateTime;

public record NotifyQueryDto(Long appKey, LocalDateTime startTime, LocalDateTime endTime) {

}
