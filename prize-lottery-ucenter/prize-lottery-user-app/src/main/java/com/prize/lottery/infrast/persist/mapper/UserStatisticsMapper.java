package com.prize.lottery.infrast.persist.mapper;

import com.prize.lottery.application.query.vo.UserTotalMetricsVo;
import com.prize.lottery.infrast.persist.po.UserStatisticsPo;
import com.prize.lottery.infrast.persist.vo.UserMetricsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface UserStatisticsMapper {

    int addUserStatistics(UserStatisticsPo statistics);

    UserStatisticsPo getStatisticsByDay(LocalDate day);

    List<UserStatisticsPo> getLatestMetricsList(Integer limit);

    List<UserStatisticsPo> getStatisticsList(@Param("startDay") LocalDate startDay, @Param("endDay") LocalDate endDay);

    UserStatisticsPo reportUserMetrics(@Param("day") LocalDate day);

    UserMetricsVo getUserMetricsVo();

    UserMetricsVo getActiveMetricsVo();

    UserTotalMetricsVo getUserTotalStatistics(@Param("startDay") LocalDate startDay, @Param("endDay") LocalDate endDay);

}
