package com.prize.lottery.infrast.persist.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.infrast.persist.po.PutChannelPo;
import com.prize.lottery.infrast.persist.po.PutRecordPo;
import com.prize.lottery.infrast.persist.vo.PutChannelStatsVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PutChannelMapper {

    int addPutChannel(PutChannelPo channel);

    int editPutChannel(PutChannelPo channel);

    PutChannelPo getPutChannel(Long id);

    PutChannelPo getPutChannelByNo(String bizNo);

    List<PutChannelPo> getPutChannelList(PageCondition condition);

    int countPutChannels(PageCondition condition);

    int addPutRecord(PutRecordPo record);

    int editPutRecord(PutRecordPo record);

    PutRecordPo getPutRecord(Long id);

    PutRecordPo getPutRecordByCode(String code);

    List<PutRecordPo> getPutRecordsByChannel(String channel);

    PutChannelStatsVo getChannelStats(String code);

    List<PutChannelStatsVo> getChannelStatsList(List<String> codes);


}
