package com.prize.lottery.infrast.persist.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.infrast.persist.po.AnnounceInfoPo;
import com.prize.lottery.infrast.persist.po.AnnounceMailboxPo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnounceInfoMapper {

    int addAnnounceInfo(AnnounceInfoPo announce);

    int editAnnounceInfo(AnnounceInfoPo announce);

    AnnounceInfoPo getAnnounceInfo(Long id);

    int addAnnounceMailbox(List<AnnounceMailboxPo> mailboxList);

    List<AnnounceInfoPo> getAnnounceList(PageCondition condition);

    int countAnnounces(PageCondition condition);

    AnnounceMailboxPo getAnnounceMailbox(@Param("receiverId") Long receiverId, @Param("channel") String channel);

    List<AnnounceMailboxPo> getAnnounceMailboxList(@Param("receiverId") Long receiverId,
                                                   @Param("channels") List<String> channels);

    List<AnnounceInfoPo> getChannelLatestAnnounces(List<String> channels);

}
