package com.prize.lottery.application.query;


import com.prize.lottery.application.query.vo.CensusLineChartVo;
import com.prize.lottery.application.query.vo.PackPrivilegeVo;
import com.prize.lottery.infrast.persist.po.PackInfoPo;
import com.prize.lottery.infrast.persist.po.PackPrivilegePo;
import com.prize.lottery.infrast.persist.vo.MemberTotalMetricsVo;
import com.prize.lottery.infrast.persist.vo.PackMetricsVo;
import com.prize.lottery.infrast.persist.vo.PackTimeMetricsVo;

import java.util.List;

public interface IPackInfoQueryService {

    List<PackInfoPo> getPackList();

    PackInfoPo getPackInfo(String packNo);

    List<PackPrivilegePo> getPackPrivileges();

    List<PackPrivilegeVo> getPrivilegeList();

    List<PackInfoPo> getIssuedPackList(Long userId);

    List<PackInfoPo> getUsingPackList();

    List<PackMetricsVo> packMetrics();

    PackTimeMetricsVo packTimeMetrics();

    CensusLineChartVo<Long> packCensus(Integer day);

}
