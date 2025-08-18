package com.prize.lottery.application.query.executor;

import com.google.common.collect.Maps;
import com.prize.lottery.application.assembler.ChannelAssembler;
import com.prize.lottery.application.query.vo.ChannelMessageVo;
import com.prize.lottery.infrast.persist.mapper.AnnounceInfoMapper;
import com.prize.lottery.infrast.persist.mapper.ChannelInfoMapper;
import com.prize.lottery.infrast.persist.po.AnnounceInfoPo;
import com.prize.lottery.infrast.persist.po.AnnounceMailboxPo;
import com.prize.lottery.infrast.persist.po.ChannelInfoPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChannelAnnounceQueryExecutor {

    private final ChannelInfoMapper  channelMapper;
    private final AnnounceInfoMapper announceMapper;
    private final ChannelAssembler   channelAssembler;

    /**
     * 查询用户消息中心公告列表
     *
     * @param userId 用户标识
     * @param scopes 渠道可见范围
     */
    public List<ChannelMessageVo> execute(Long userId, List<Integer> scopes) {
        try {
            List<ChannelInfoPo> announceChannels = channelMapper.getTypeUsedChannels(0, scopes);
            if (CollectionUtils.isEmpty(announceChannels)) {
                return Collections.emptyList();
            }
            List<String> channels = announceChannels.stream()
                                                    .map(ChannelInfoPo::getChannel)
                                                    .collect(Collectors.toList());

            List<AnnounceMailboxPo>        mailboxes = announceMapper.getAnnounceMailboxList(userId, channels);
            Map<String, AnnounceMailboxPo> boxMap    = Maps.uniqueIndex(mailboxes, AnnounceMailboxPo::getChannel);

            List<AnnounceInfoPo>        announces   = announceMapper.getChannelLatestAnnounces(channels);
            Map<String, AnnounceInfoPo> announceMap = Maps.uniqueIndex(announces, AnnounceInfoPo::getChannel);

            return announceChannels.stream().map(channel -> {
                ChannelMessageVo channelMessage = channelAssembler.toVo(channel);
                AnnounceInfoPo   announce       = announceMap.get(channel.getChannel());
                int              hasRead        = 1;
                if (announce != null) {
                    channelMessage.setTitle(announce.getTitle());
                    channelMessage.setLatestTime(announce.getGmtCreate());
                    AnnounceMailboxPo mailbox = boxMap.get(channel.getChannel());
                    if (mailbox == null || mailbox.getAnnounceId() < announce.getId()) {
                        hasRead = 0;
                    }
                }
                channelMessage.setRead(hasRead);
                return channelMessage;
            }).collect(Collectors.toList());
        } catch (Exception error) {
            log.error(error.getMessage(), error);
        }
        return Collections.emptyList();
    }

}
