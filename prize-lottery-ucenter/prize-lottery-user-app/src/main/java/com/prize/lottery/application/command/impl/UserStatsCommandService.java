package com.prize.lottery.application.command.impl;

import com.prize.lottery.application.command.IUserStatsCommandService;
import com.prize.lottery.infrast.persist.mapper.UserStatisticsMapper;
import com.prize.lottery.infrast.persist.po.UserStatisticsPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserStatsCommandService implements IUserStatsCommandService {

    private final UserStatisticsMapper mapper;

    @Override
    @Transactional
    public void dateReport(LocalDate date) {
        UserStatisticsPo userMetrics = mapper.reportUserMetrics(date);
        if (userMetrics != null) {
            userMetrics.setDay(date);
            mapper.addUserStatistics(userMetrics);
        }
    }
}
