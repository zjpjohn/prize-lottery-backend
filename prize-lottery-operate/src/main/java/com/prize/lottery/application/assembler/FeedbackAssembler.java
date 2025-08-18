package com.prize.lottery.application.assembler;

import com.prize.lottery.application.command.dto.FeedbackCreateCmd;
import com.prize.lottery.application.vo.AppFeedbackVo;
import com.prize.lottery.application.vo.FeedbackTypeVo;
import com.prize.lottery.domain.app.model.AppFeedbackDo;
import com.prize.lottery.infrast.persist.po.AppFeedbackPo;
import com.prize.lottery.infrast.persist.po.FeedbackTypePo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FeedbackAssembler {

    AppFeedbackDo toDO(FeedbackCreateCmd command);

    AppFeedbackVo toVo(AppFeedbackPo feedback);

    List<AppFeedbackVo> toFeedbackVos(List<AppFeedbackPo> feedbacks);

    FeedbackTypeVo toVo(FeedbackTypePo type);

    List<FeedbackTypeVo> toTypeVos(List<FeedbackTypePo> types);

}
