package com.prize.lottery.application.query;


import com.prize.lottery.application.query.vo.PackPrivilegeVo;
import com.prize.lottery.infrast.persist.po.PackInfoPo;
import com.prize.lottery.infrast.persist.po.PackPrivilegePo;
import com.prize.lottery.infrast.persist.vo.PackMetricsVo;

import java.util.List;

public interface IPackInfoQueryService {

    List<PackInfoPo> getPackList();

    PackInfoPo getPackInfo(String packNo);

    List<PackPrivilegePo> getPackPrivileges();

    List<PackPrivilegeVo> getPrivilegeList();

    List<PackInfoPo> getIssuedPackList(Long userId);

    List<PackMetricsVo> packMetrics();

}
