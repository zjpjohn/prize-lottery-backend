package com.prize.lottery.application.query;


import com.cloud.arch.page.Page;
import com.prize.lottery.application.query.dto.ChannelListQuery;
import com.prize.lottery.application.query.vo.ChannelMessageVo;
import com.prize.lottery.infrast.persist.po.ChannelInfoPo;

import java.util.List;

public interface IChannelInfoQueryService {

    Page<ChannelInfoPo> channelList(ChannelListQuery query);

    ChannelInfoPo channelDetail(String channel);

    List<ChannelInfoPo> getUsingTypedChannels(Integer type);

    List<ChannelMessageVo> messageList(Long userId);

}
