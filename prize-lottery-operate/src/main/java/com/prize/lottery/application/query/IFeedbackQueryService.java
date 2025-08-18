package com.prize.lottery.application.query;


import com.cloud.arch.page.Page;
import com.prize.lottery.application.query.dto.FeedbackListQuery;
import com.prize.lottery.application.vo.AppFeedbackVo;

public interface IFeedbackQueryService {

    AppFeedbackVo getAppFeedback(Long id);

    Page<AppFeedbackVo> getFeedbackList(FeedbackListQuery query);

}
