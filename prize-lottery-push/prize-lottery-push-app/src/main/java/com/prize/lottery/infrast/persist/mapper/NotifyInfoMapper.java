package com.prize.lottery.infrast.persist.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.infrast.persist.po.MetricsNotifyPo;
import com.prize.lottery.infrast.persist.po.NotifyInfoPo;
import com.prize.lottery.infrast.persist.po.NotifyTaskPo;
import com.prize.lottery.infrast.persist.vo.PushMetricsVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NotifyInfoMapper {

    int addNotifyInfo(NotifyInfoPo notifyInfo);

    int editNotifyInfo(NotifyInfoPo notifyInfo);

    NotifyInfoPo getNotifyInfo(Long id);

    List<NotifyInfoPo> getUsingNotifyList(Long appKey);

    List<NotifyInfoPo> getNotifyInfoList(PageCondition condition);

    int countNotifyInfos(PageCondition condition);

    int addNotifyTask(NotifyTaskPo notifyTask);

    int editNotifyTask(NotifyTaskPo notifyTask);

    int editNotifyTasks(List<NotifyTaskPo> tasks);

    NotifyTaskPo getNotifyTask(Long id);

    NotifyTaskPo getNotifyTaskByMsgId(String messageId);

    List<NotifyTaskPo> getNotifyTaskList(PageCondition condition);

    int countNotifyTasks(PageCondition condition);

    int addNotifyMetrics(List<MetricsNotifyPo> list);

    List<MetricsNotifyPo> getMetricsNotifyList(@Param("appKey") Long appKey,
                                               @Param("startDay") LocalDate startDay,
                                               @Param("endDay") LocalDate endDay);

    PushMetricsVo getPushMetricsVo(Long appKey);
}
