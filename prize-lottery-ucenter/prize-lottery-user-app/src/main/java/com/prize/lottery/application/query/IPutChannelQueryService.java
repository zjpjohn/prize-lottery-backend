package com.prize.lottery.application.query;


import com.cloud.arch.page.Page;
import com.prize.lottery.application.query.dto.PutChannelQuery;
import com.prize.lottery.application.query.vo.PutChannelVo;
import com.prize.lottery.infrast.persist.po.PutRecordPo;

import java.util.List;

public interface IPutChannelQueryService {

    Page<PutChannelVo> getChannelList(PutChannelQuery query);

    PutChannelVo getPutChannel(String code);

    List<PutRecordPo> getPutRecords(String channel);
}
