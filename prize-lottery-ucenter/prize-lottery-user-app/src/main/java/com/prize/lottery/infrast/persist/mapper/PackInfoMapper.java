package com.prize.lottery.infrast.persist.mapper;

import com.prize.lottery.infrast.persist.dto.MetricsTimeDto;
import com.prize.lottery.infrast.persist.po.PackInfoPo;
import com.prize.lottery.infrast.persist.po.PackPrivilegePo;
import com.prize.lottery.infrast.persist.vo.PackDayMetricsVo;
import com.prize.lottery.infrast.persist.vo.PackMetricsVo;
import com.prize.lottery.infrast.persist.vo.PackTimeMetricsVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PackInfoMapper {

    int addPackInfo(PackInfoPo packInfo);

    int editPackInfo(PackInfoPo packInfo);

    int delPackInfo(String packNo);

    int existPackName(String name);

    PackInfoPo getPackInfoByNo(String packNo);

    List<PackInfoPo> getAllPackInfoList();

    List<PackInfoPo> getUsingPackList(Boolean excludeTrial);

    int addPrivilegeList(List<PackPrivilegePo> privileges);

    int editPrivilegeList(List<PackPrivilegePo> privileges);

    int removePrivileges(@Param("idList") List<Long> idList);

    List<PackPrivilegePo> getPackPrivileges();

    int countPackPrivileges();

    List<PackInfoPo> getUserBuyPackList(Long userId);

    PackTimeMetricsVo getPackTimeMetrics(MetricsTimeDto time);

    List<PackMetricsVo> getPackTotalMetrics();

    List<PackDayMetricsVo> getPackDayMetrics(@Param("startDay") LocalDateTime startDay,
                                             @Param("endDay") LocalDateTime endDay);

}
