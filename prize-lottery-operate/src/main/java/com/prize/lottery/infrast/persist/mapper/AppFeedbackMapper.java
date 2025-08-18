package com.prize.lottery.infrast.persist.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.infrast.persist.po.AppFeedbackPo;
import com.prize.lottery.infrast.persist.po.FeedbackTypePo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface AppFeedbackMapper {

    int addAppFeedback(AppFeedbackPo feedback);

    int editAppFeedback(AppFeedbackPo feedback);

    AppFeedbackPo getAppFeedback(Long id);

    List<AppFeedbackPo> getFeedbackList(PageCondition condition);

    int countFeedbacks(PageCondition condition);

    int addFeedbackType(FeedbackTypePo feedbackType);

    int editFeedbackType(FeedbackTypePo feedbackType);

    int removeFeedType(Long id);

    int sortFeedbackType(@Param("id") Long id, @Param("sort") Integer sort, @Param("position") Integer position);

    Optional<Integer> typeMaxSort(String appNo);

    FeedbackTypePo getFeedbackType(Long id);

    List<FeedbackTypePo> getFeedbackTypesByApp(String appNo);

    List<FeedbackTypePo> getFeedbackTypeList(PageCondition condition);

    int countFeedbackTypes(PageCondition condition);

}
