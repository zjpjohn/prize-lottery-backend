package com.prize.lottery.application.command.executor;

import com.prize.lottery.application.command.dto.MessageClearCmd;
import com.prize.lottery.infrast.persist.mapper.AnnounceInfoMapper;
import com.prize.lottery.infrast.persist.mapper.ChannelInfoMapper;
import com.prize.lottery.infrast.persist.mapper.RemindInfoMapper;
import com.prize.lottery.infrast.persist.po.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageAppClearExecutor {

    private final ChannelInfoMapper  channelMapper;
    private final AnnounceInfoMapper announceMapper;
    private final RemindInfoMapper   remindMapper;

    /**
     * 清理用户消息提醒，设置最新消息已读
     */
    public void execute(MessageClearCmd command) {
        List<ChannelInfoPo> channels = channelMapper.getChannelInfoByUkList(command.getChannels());
        Map<Integer, List<String>> groupedChannels = channels.stream()
                                                             .collect(Collectors.groupingBy(ChannelInfoPo::getType, Collectors.mapping(ChannelInfoPo::getChannel, Collectors.toList())));
        List<String> announceChannels = groupedChannels.get(0);
        if (!CollectionUtils.isEmpty(announceChannels)) {
            clearAnnounces(command.getUserId(), announceChannels);
        }
        List<String> remindChannels = groupedChannels.get(1);
        if (!CollectionUtils.isEmpty(remindChannels)) {
            clearRemind(command.getUserId(), remindChannels);
        }

    }

    /**
     * 设置用户最新公告已读
     *
     * @param userId   用户标识
     * @param channels 消息渠道标识
     */
    private void clearAnnounces(Long userId, List<String> channels) {
        List<AnnounceInfoPo> latestAnnounces = announceMapper.getChannelLatestAnnounces(channels);
        if (CollectionUtils.isEmpty(latestAnnounces)) {
            return;
        }
        List<AnnounceMailboxPo> mailboxList = latestAnnounces.stream().map(announce -> {
            AnnounceMailboxPo mailbox = new AnnounceMailboxPo();
            mailbox.setReceiverId(userId);
            mailbox.setAnnounceId(announce.getId());
            mailbox.setChannel(announce.getChannel());
            mailbox.setLatestRead(LocalDateTime.now());
            return mailbox;
        }).collect(Collectors.toList());
        announceMapper.addAnnounceMailbox(mailboxList);
    }

    /**
     * 设置用户最新提醒已读
     *
     * @param userId   用户标识
     * @param channels 消息渠道
     */
    private void clearRemind(Long userId, List<String> channels) {
        List<RemindInfoPo> latestReminds = remindMapper.getChannelLatestReminds(userId, channels);
        if (CollectionUtils.isEmpty(latestReminds)) {
            return;
        }
        List<RemindMailBoxPo> mailBoxList = latestReminds.stream().map(remind -> {
            RemindMailBoxPo mailBox = new RemindMailBoxPo();
            mailBox.setReceiverId(userId);
            mailBox.setRemindId(remind.getId());
            mailBox.setChannel(remind.getChannel());
            mailBox.setLatestRead(LocalDateTime.now());
            return mailBox;
        }).collect(Collectors.toList());
        remindMapper.addRemindMailbox(mailBoxList);
    }
}
