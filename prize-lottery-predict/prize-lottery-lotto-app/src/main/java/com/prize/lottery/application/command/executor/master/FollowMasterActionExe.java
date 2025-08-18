package com.prize.lottery.application.command.executor.master;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.dto.SubscribeMasterCmd;
import com.prize.lottery.domain.master.model.MasterInfo;
import com.prize.lottery.domain.master.repository.IMasterInfoRepository;
import com.prize.lottery.domain.user.model.UserSubscribe;
import com.prize.lottery.domain.user.repository.IUserMasterRepository;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class FollowMasterActionExe {

    private final IMasterInfoRepository masterInfoRepository;
    private final IUserMasterRepository userMasterRepository;

    /**
     * 批量订阅专家
     */
    public void batchSubscribe(List<SubscribeMasterCmd> commands) {
        List<UserSubscribe> subscribes = commands.stream()
                                                 .map(c -> new UserSubscribe(c).subscribe())
                                                 .collect(Collectors.toList());
        userMasterRepository.saveBatch(subscribes);
    }

    /**
     * 订阅专家
     *
     * @param command 订阅专家命令
     */
    public void subscribe(SubscribeMasterCmd command) {
        MasterInfo masterInfo = masterInfoRepository.getMasterInfo(command.getMasterId());
        Assert.notNull(masterInfo, ResponseHandler.MASTER_NONE);

        //订阅专家
        UserSubscribe subscribe  = new UserSubscribe(command);
        Boolean       subscribed = userMasterRepository.hasSubscribed(subscribe);
        Assert.state(subscribed, ResponseHandler.HAS_SUBSCRIBE_MASTER);

        subscribe.subscribe();
        userMasterRepository.save(subscribe);
    }

    /**
     * 追踪专家字段
     */
    public void traceMaster(SubscribeMasterCmd command) {
        UserSubscribe subscribe = userMasterRepository.ofUk(command.getUserId(), command.getMasterId(), command.getType());
        userMasterRepository.save(subscribe.traceMaster(command.getTrace()));
    }

    /**
     * 重点关注或取消重点关注
     */
    public void specialOrCancel(SubscribeMasterCmd command) {
        UserSubscribe subscribe = userMasterRepository.ofUk(command.getUserId(), command.getMasterId(), command.getType());
        userMasterRepository.save(subscribe.specialOrCancel());
    }

    /**
     * 取消订阅指定专家
     *
     * @param command 取消订阅命令
     */
    public void unsubscribe(SubscribeMasterCmd command) {
        UserSubscribe subscribe = userMasterRepository.ofUk(command.getUserId(), command.getMasterId(), command.getType());
        userMasterRepository.removeSubscribe(subscribe.unsubscribe());
    }
}
