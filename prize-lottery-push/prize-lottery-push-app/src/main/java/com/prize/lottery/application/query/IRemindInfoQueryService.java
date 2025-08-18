package com.prize.lottery.application.query;


import com.cloud.arch.page.Page;
import com.prize.lottery.application.query.dto.MessageAppQuery;
import com.prize.lottery.application.query.dto.RemindAdmQuery;
import com.prize.lottery.application.query.vo.MessageInfoVo;
import com.prize.lottery.infrast.persist.po.RemindInfoPo;

public interface IRemindInfoQueryService {

    Page<MessageInfoVo> remindAppQuery(MessageAppQuery query);

    Page<RemindInfoPo> remindAdmQuery(RemindAdmQuery query);

}
