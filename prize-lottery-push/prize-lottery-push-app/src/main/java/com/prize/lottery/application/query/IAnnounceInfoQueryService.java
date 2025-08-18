package com.prize.lottery.application.query;


import com.cloud.arch.page.Page;
import com.prize.lottery.application.query.dto.AnnounceAdmQuery;
import com.prize.lottery.application.query.dto.MessageAppQuery;
import com.prize.lottery.application.query.vo.MessageInfoVo;
import com.prize.lottery.infrast.persist.po.AnnounceInfoPo;

public interface IAnnounceInfoQueryService {

    Page<MessageInfoVo> announceAppQuery(MessageAppQuery query);

    Page<AnnounceInfoPo> announceAdmQuery(AnnounceAdmQuery query);

}
