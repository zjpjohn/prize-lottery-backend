package com.prize.lottery.infrast.persist.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.infrast.persist.po.ChannelInfoPo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChannelInfoMapper {

    int addChannelInfo(ChannelInfoPo channel);

    int editChannelInfo(ChannelInfoPo channel);

    ChannelInfoPo getChannelById(Long id);

    ChannelInfoPo getChannelByUk(String channel);

    List<ChannelInfoPo> getChannelInfoByUkList(List<String> channels);

    List<ChannelInfoPo> getUsingTypedChannels(Integer type);

    List<ChannelInfoPo> getChannelList(PageCondition condition);

    int countChannels(PageCondition condition);

    List<ChannelInfoPo> getTypeUsedChannels(@Param("type") Integer type, @Param("scopes") List<Integer> scopes);

}
