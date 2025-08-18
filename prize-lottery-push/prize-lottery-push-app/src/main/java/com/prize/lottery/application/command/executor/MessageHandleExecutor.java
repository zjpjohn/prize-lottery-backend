package com.prize.lottery.application.command.executor;

import com.prize.lottery.application.assembler.ChannelAssembler;
import com.prize.lottery.event.MessageEvent;
import com.prize.lottery.event.MessageType;
import com.prize.lottery.infrast.persist.enums.CommonState;
import com.prize.lottery.infrast.persist.mapper.AnnounceInfoMapper;
import com.prize.lottery.infrast.persist.mapper.ChannelInfoMapper;
import com.prize.lottery.infrast.persist.mapper.RemindInfoMapper;
import com.prize.lottery.infrast.persist.po.AnnounceInfoPo;
import com.prize.lottery.infrast.persist.po.ChannelInfoPo;
import com.prize.lottery.infrast.persist.po.RemindInfoPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageHandleExecutor {

    private final ChannelInfoMapper  channelMapper;
    private final AnnounceInfoMapper announceMapper;
    private final RemindInfoMapper   remindMapper;
    private final ChannelAssembler   channelAssembler;

    @Transactional
    public void execute(MessageEvent event, MessageType type) {
        ChannelInfoPo channel = channelMapper.getChannelByUk(event.getChannel());
        if (channel == null || channel.getState() != CommonState.USING) {
            log.warn("无效或暂未上线消息渠道[{}]，请确认配置", event.getChannel());
            return;
        }
        if (channel.getType() == 0) {
            AnnounceInfoPo announceInfo = channelAssembler.toAnnounce(event, type);
            announceMapper.addAnnounceInfo(announceInfo);
            return;
        }
        RemindInfoPo remindInfo = channelAssembler.toRemind(event, type);
        remindMapper.addRemindInfo(remindInfo);
    }
}
