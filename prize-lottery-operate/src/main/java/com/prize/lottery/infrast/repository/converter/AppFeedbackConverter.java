package com.prize.lottery.infrast.repository.converter;

import com.prize.lottery.domain.app.model.AppFeedbackDo;
import com.prize.lottery.domain.app.model.FeedbackTypeDo;
import com.prize.lottery.infrast.persist.po.AppFeedbackPo;
import com.prize.lottery.infrast.persist.po.FeedbackTypePo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppFeedbackConverter {

    AppFeedbackDo toDo(AppFeedbackPo feedback);

    AppFeedbackPo toPo(AppFeedbackDo feedback);

    FeedbackTypePo toPo(FeedbackTypeDo type);

    FeedbackTypeDo toDo(FeedbackTypePo type);

}
