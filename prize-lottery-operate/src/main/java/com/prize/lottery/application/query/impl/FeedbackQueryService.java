package com.prize.lottery.application.query.impl;

import com.cloud.arch.page.Page;
import com.cloud.arch.utils.CollectionUtils;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.assembler.FeedbackAssembler;
import com.prize.lottery.application.query.IFeedbackQueryService;
import com.prize.lottery.application.query.dto.FeedbackListQuery;
import com.prize.lottery.application.vo.AppFeedbackVo;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.mapper.AppFeedbackMapper;
import com.prize.lottery.infrast.persist.mapper.AppInfoMapper;
import com.prize.lottery.infrast.persist.po.AppFeedbackPo;
import com.prize.lottery.infrast.persist.po.AppInfoPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedbackQueryService implements IFeedbackQueryService {

    private final FeedbackAssembler assembler;
    private final AppInfoMapper     appMapper;
    private final AppFeedbackMapper feedbackMapper;

    @Override
    public AppFeedbackVo getAppFeedback(Long id) {
        AppFeedbackPo feedback = feedbackMapper.getAppFeedback(id);
        Assert.notNull(feedback, ResponseHandler.FEEDBACK_NOT_EXIST);
        AppFeedbackVo feedbackVo = assembler.toVo(feedback);

        Optional.ofNullable(appMapper.getAppInfo(feedback.getAppNo()))
                .map(AppInfoPo::getName)
                .ifPresent(feedbackVo::setAppName);
        return feedbackVo;
    }

    @Override
    public Page<AppFeedbackVo> getFeedbackList(FeedbackListQuery query) {
        return query.from()
                    .count(feedbackMapper::countFeedbacks)
                    .query(feedbackMapper::getFeedbackList)
                    .flatMap(assembler::toFeedbackVos)
                    .ifPresent(list -> {
                        List<String>        appNos  = CollectionUtils.distinctList(list, AppFeedbackPo::getAppNo);
                        List<AppInfoPo>     appList = appMapper.getAppListByNos(appNos);
                        Map<String, String> appMap  = CollectionUtils.toMap(appList, AppInfoPo::getSeqNo, AppInfoPo::getName);
                        list.forEach(v -> v.setAppName(appMap.get(v.getAppNo())));
                    });
    }
}
