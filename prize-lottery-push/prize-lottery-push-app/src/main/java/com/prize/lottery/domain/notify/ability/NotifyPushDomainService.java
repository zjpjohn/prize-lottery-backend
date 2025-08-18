package com.prize.lottery.domain.notify.ability;

import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.notify.model.NotifyInfoDo;
import com.prize.lottery.domain.notify.model.NotifyTagDo;
import com.prize.lottery.domain.notify.model.NotifyTagGroupDo;
import com.prize.lottery.domain.notify.repository.INotifyInfoRepository;
import com.prize.lottery.domain.notify.repository.ITagGroupRepository;
import com.prize.lottery.infrast.external.push.TagBasedPushFacade;
import com.prize.lottery.infrast.external.push.request.TagBasedPushDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class NotifyPushDomainService {

    private final TagBasedPushFacade    pushFacade;
    private final ITagGroupRepository   tagRepository;
    private final INotifyInfoRepository notifyRepository;

    @Transactional
    public void tagGroupPush(Long notifyId) {
        Aggregate<Long, NotifyInfoDo> notifyAggregate = notifyRepository.ofId(notifyId);
        NotifyInfoDo                  notifyInfo      = notifyAggregate.getRoot();
        if (!notifyInfo.canPush()) {
            log.warn("当前推送[{}]处于非推送状态，请确认状态", notifyInfo.getId());
            return;
        }
        Aggregate<Long, NotifyTagGroupDo> aggregate = tagRepository.ofId(notifyInfo.getGroupId());
        NotifyTagGroupDo                  tagGroup  = aggregate.getRoot();
        List<String> tagList = tagGroup.getTagList().stream().map(NotifyTagDo::getTagName).collect(Collectors.toList());
        Boolean pushResult = notifyInfo.notifyPush(tagList, taskDo -> {
            TagBasedPushDto push = new TagBasedPushDto();
            push.setAppKey(tagGroup.getAppKey());
            push.setTitle(notifyInfo.getTitle());
            push.setBody(notifyInfo.getBody());
            push.setOnline(notifyInfo.getOnline() == 2);
            push.setBarType(notifyInfo.getBarType());
            push.setBarPriority(notifyInfo.getBarPriority());
            push.setExtParams(notifyInfo.getExtParams());
            push.setPushTime(taskDo.getPushTime());
            push.setNotifyChannel(notifyInfo.getChannel());
            push.setOpenType(notifyInfo.getOpenType());
            push.setActivity(notifyInfo.getOpenActivity());
            push.setUrl(notifyInfo.getOpenUrl());
            push.setNotifyType(notifyInfo.getNotice().name());
            return pushFacade.execute(push);
        });
        if (!pushResult) {
            log.warn("当前推送[{}]不能创建推送任务", notifyInfo.getId());
        }
        notifyRepository.save(notifyAggregate);
    }

}
