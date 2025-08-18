package com.prize.lottery.application.query;


import com.cloud.arch.page.Page;
import com.prize.lottery.application.query.dto.TypeListQuery;
import com.prize.lottery.application.vo.AppFeedTypeVo;
import com.prize.lottery.application.vo.FeedbackTypeVo;

public interface IFeedTypeQueryService {

    FeedbackTypeVo getFeedbackType(Long id);

    Page<FeedbackTypeVo> getFeedbackTypeList(TypeListQuery query);

    AppFeedTypeVo getAppFeedType(String appNo, String version);

}
