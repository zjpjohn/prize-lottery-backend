package com.prize.lottery.application.command.executor;

import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.notify.model.NotifyTagGroupDo;
import com.prize.lottery.domain.notify.repository.ITagGroupRepository;
import com.prize.lottery.infrast.external.push.TagBasedBindFacade;
import com.prize.lottery.infrast.external.push.request.TagBasedBindDto;
import com.prize.lottery.infrast.persist.mapper.NotifyAppMapper;
import com.prize.lottery.infrast.persist.po.NotifyDevicePo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagGroupBindExecutor {

    private final ITagGroupRepository repository;
    private final NotifyAppMapper     appMapper;
    private final TagBasedBindFacade  bindFacade;

    @Transactional
    public void execute(Long appKey, Long groupId) {
        List<NotifyDevicePo> devices      = appMapper.getWithoutBindDevices(appKey, groupId, 500);
        int                  bindReqCount = 0;
        while (!CollectionUtils.isEmpty(devices) && bindReqCount < 4) {
            Aggregate<Long, NotifyTagGroupDo> aggregate  = repository.ofId(groupId);
            NotifyTagGroupDo                  root       = aggregate.getRoot();
            List<String>                      deviceList = devices.stream()
                                                                  .map(NotifyDevicePo::getDeviceId)
                                                                  .collect(Collectors.toList());
            if (!root.bindDevices(deviceList)) {
                break;
            }
            TagBasedBindDto bindDto = new TagBasedBindDto();
            bindDto.setAppKey(appKey);
            bindDto.setDevices(deviceList);
            bindDto.setTagName(root.getPickedTag().getTagName());
            if (bindFacade.execute(bindDto)) {
                repository.save(aggregate);
            }
            bindReqCount++;
            devices = appMapper.getWithoutBindDevices(appKey, groupId, 500);
        }
    }
}
