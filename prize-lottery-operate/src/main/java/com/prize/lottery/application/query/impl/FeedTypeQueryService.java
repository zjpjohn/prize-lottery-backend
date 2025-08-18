package com.prize.lottery.application.query.impl;

import com.cloud.arch.page.Page;
import com.cloud.arch.utils.CollectionUtils;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.assembler.FeedbackAssembler;
import com.prize.lottery.application.query.IFeedTypeQueryService;
import com.prize.lottery.application.query.dto.TypeListQuery;
import com.prize.lottery.application.vo.AppFeedTypeVo;
import com.prize.lottery.application.vo.FeedbackTypeVo;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.mapper.AppFeedbackMapper;
import com.prize.lottery.infrast.persist.mapper.AppInfoMapper;
import com.prize.lottery.infrast.persist.po.AppInfoPo;
import com.prize.lottery.infrast.persist.po.FeedbackTypePo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedTypeQueryService implements IFeedTypeQueryService {

    private final FeedbackAssembler assembler;
    private final AppFeedbackMapper feedbackMapper;
    private final AppInfoMapper     appInfoMapper;

    @Override
    public FeedbackTypeVo getFeedbackType(Long id) {
        FeedbackTypePo feedbackType = feedbackMapper.getFeedbackType(id);
        Assert.notNull(feedbackType, ResponseHandler.FEEDBACK_TYPE_NOT_EXIST);
        FeedbackTypeVo typeVo = assembler.toVo(feedbackType);

        Optional.ofNullable(appInfoMapper.getAppInfo(typeVo.getAppNo()))
                .map(AppInfoPo::getName)
                .ifPresent(typeVo::setAppName);
        return typeVo;
    }

    @Override
    public Page<FeedbackTypeVo> getFeedbackTypeList(TypeListQuery query) {
        return query.from()
                    .count(feedbackMapper::countFeedbackTypes)
                    .query(feedbackMapper::getFeedbackTypeList)
                    .flatMap(assembler::toTypeVos)
                    .ifPresent(list -> {
                        List<String>        appNos  = CollectionUtils.distinctList(list, FeedbackTypePo::getAppNo);
                        List<AppInfoPo>     appList = appInfoMapper.getAppListByNos(appNos);
                        Map<String, String> appMap  = CollectionUtils.toMap(appList, AppInfoPo::getSeqNo, AppInfoPo::getName);
                        list.forEach(v -> v.setAppName(appMap.get(v.getAppNo())));
                    });
    }

    @Override
    public AppFeedTypeVo getAppFeedType(String appNo, String version) {
        AppInfoPo appInfo = appInfoMapper.getAppInfo(appNo);
        Assert.notNull(appInfo, ResponseHandler.APP_NOT_EXIST);

        List<FeedbackTypePo> typeList = feedbackMapper.getFeedbackTypesByApp(appNo);
        List<String> types = typeList.stream()
                                     .filter(v -> v.suitableVersion(version))
                                     .map(FeedbackTypePo::getType)
                                     .collect(Collectors.toList());

        AppFeedTypeVo appFeedType = new AppFeedTypeVo();
        appFeedType.setAppNo(appNo);
        appFeedType.setVersion(version);
        appFeedType.setTypes(types);
        return appFeedType;
    }

}
