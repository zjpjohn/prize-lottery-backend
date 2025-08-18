package com.prize.lottery.application.query.executor;

import com.google.common.collect.Maps;
import com.prize.lottery.application.assembler.ChannelAssembler;
import com.prize.lottery.application.query.vo.ChannelMessageVo;
import com.prize.lottery.infrast.persist.mapper.ChannelInfoMapper;
import com.prize.lottery.infrast.persist.mapper.RemindInfoMapper;
import com.prize.lottery.infrast.persist.po.ChannelInfoPo;
import com.prize.lottery.infrast.persist.po.RemindInfoPo;
import com.prize.lottery.infrast.persist.po.RemindMailBoxPo;
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
public class ChannelRemindQueryExecutor {

    private final ChannelInfoMapper channelMapper;
    private final RemindInfoMapper  remindMapper;
    private final ChannelAssembler  channelAssembler;

    /**
     * 查询用户消息中心提醒列表
     *
     * @param userId 用户标识
     * @param scopes 可见范围列表
     */
    public List<ChannelMessageVo> execute(Long userId, List<Integer> scopes) {
        try {
            List<ChannelInfoPo> remindChannels = channelMapper.getTypeUsedChannels(1, scopes);
            if (CollectionUtils.isEmpty(remindChannels)) {
                return Collections.emptyList();
            }
            List<String> channels = remindChannels.stream().map(ChannelInfoPo::getChannel).collect(Collectors.toList());
            List<RemindMailBoxPo>        mailBoxes = remindMapper.getRemindMailBoxList(userId, channels);
            Map<String, RemindMailBoxPo> boxMap    = Maps.uniqueIndex(mailBoxes, RemindMailBoxPo::getChannel);

            List<RemindInfoPo>        reminds   = remindMapper.getChannelLatestReminds(userId, channels);
            Map<String, RemindInfoPo> remindMap = Maps.uniqueIndex(reminds, RemindInfoPo::getChannel);

            return remindChannels.stream().map(channel -> {
                ChannelMessageVo channelMessage = channelAssembler.toVo(channel);
                RemindInfoPo     remind         = remindMap.get(channel.getChannel());
                int              hasRead        = 1;
                if (remind != null) {
                    channelMessage.setTitle(remind.getTitle());
                    channelMessage.setLatestTime(remind.getGmtCreate());
                    RemindMailBoxPo mailBox = boxMap.get(channel.getChannel());
                    if (mailBox == null || mailBox.getRemindId() < remind.getId()) {
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
