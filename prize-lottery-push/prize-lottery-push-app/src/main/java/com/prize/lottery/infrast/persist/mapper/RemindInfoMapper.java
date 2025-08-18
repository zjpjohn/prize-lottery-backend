package com.prize.lottery.infrast.persist.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.infrast.persist.po.RemindInfoPo;
import com.prize.lottery.infrast.persist.po.RemindMailBoxPo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RemindInfoMapper {

    int addRemindInfo(RemindInfoPo remind);

    int addRemindMailbox(List<RemindMailBoxPo> mailBoxList);

    List<RemindInfoPo> getRemindList(PageCondition condition);

    int countReminds(PageCondition condition);

    RemindMailBoxPo getRemindMailbox(@Param("receiverId") Long receiverId, @Param("channel") String channel);

    List<RemindMailBoxPo> getRemindMailBoxList(@Param("receiverId") Long receiverId,
                                               @Param("channels") List<String> channels);

    List<RemindInfoPo> getChannelLatestReminds(@Param("receiverId") Long receiverId,
                                               @Param("channels") List<String> channels);

}
