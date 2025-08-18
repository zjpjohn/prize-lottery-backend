package com.prize.lottery.application.query;


import com.prize.lottery.application.vo.AppVerifyVo;
import com.prize.lottery.infrast.persist.po.AppVerifyPo;

public interface IAppVerifyQueryService {

    AppVerifyPo getAppVerifyInfo(String appNo);

    AppVerifyVo releasedAppVerify(String appNo);

}
