package com.prize.lottery.infrast.persist.mapper;

import com.prize.lottery.infrast.persist.po.PackInfoPo;
import com.prize.lottery.infrast.persist.po.PackPrivilegePo;
import com.prize.lottery.infrast.persist.po.PackStatisticsPo;
import com.prize.lottery.infrast.persist.vo.PackMetricsVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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

    int addPackStatistics(List<PackStatisticsPo> list);

    List<PackMetricsVo> getPackMetricsVo();

    List<PackStatisticsPo> getDayPackStatistics(@Param("metricDate") LocalDate metricDate);

    List<PackInfoPo> getUserBuyPackList(Long userId);

}
