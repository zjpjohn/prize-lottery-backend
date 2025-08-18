package com.prize.lottery.application.query.impl;

import com.prize.lottery.application.assembler.PackInfoAssembler;
import com.prize.lottery.application.query.IPackInfoQueryService;
import com.prize.lottery.application.query.executor.PackLineChartExecutor;
import com.prize.lottery.application.query.vo.CensusLineChartVo;
import com.prize.lottery.application.query.vo.PackPrivilegeVo;
import com.prize.lottery.infrast.persist.dto.MetricsTimeDto;
import com.prize.lottery.infrast.persist.mapper.PackInfoMapper;
import com.prize.lottery.infrast.persist.po.PackInfoPo;
import com.prize.lottery.infrast.persist.po.PackPrivilegePo;
import com.prize.lottery.infrast.persist.vo.PackMetricsVo;
import com.prize.lottery.infrast.persist.vo.PackTimeMetricsVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PackInfoQueryService implements IPackInfoQueryService {

    private final PackInfoAssembler     assembler;
    private final PackInfoMapper        packInfoMapper;
    private final PackLineChartExecutor packLineChartExecutor;

    @Override
    public List<PackInfoPo> getPackList() {
        return packInfoMapper.getAllPackInfoList();
    }

    @Override
    public PackInfoPo getPackInfo(String packNo) {
        return packInfoMapper.getPackInfoByNo(packNo);
    }

    @Override
    public List<PackPrivilegePo> getPackPrivileges() {
        return packInfoMapper.getPackPrivileges();
    }

    @Override
    public List<PackPrivilegeVo> getPrivilegeList() {
        List<PackPrivilegePo> privileges = packInfoMapper.getPackPrivileges();
        return assembler.toVoList(privileges);
    }

    @Override
    public List<PackInfoPo> getIssuedPackList(Long userId) {
        List<PackInfoPo> packList     = packInfoMapper.getUserBuyPackList(userId);
        boolean          excludeTrial = packList.stream().anyMatch(PackInfoPo::isTrial);
        return packInfoMapper.getUsingPackList(excludeTrial);
    }

    @Override
    public List<PackInfoPo> getUsingPackList() {
        return packInfoMapper.getUsingPackList(false);
    }

    @Override
    public List<PackMetricsVo> packMetrics() {
        return packInfoMapper.getPackTotalMetrics();
    }

    @Override
    public PackTimeMetricsVo packTimeMetrics() {
        MetricsTimeDto timeDto = new MetricsTimeDto();
        return packInfoMapper.getPackTimeMetrics(timeDto);
    }

    @Override
    public CensusLineChartVo<Long> packCensus(Integer day) {
        return packLineChartExecutor.execute(day);
    }

}
