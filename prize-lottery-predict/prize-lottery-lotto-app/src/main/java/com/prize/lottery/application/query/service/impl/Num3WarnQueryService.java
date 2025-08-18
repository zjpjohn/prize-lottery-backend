package com.prize.lottery.application.query.service.impl;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.query.dto.Num3LayerQuery;
import com.prize.lottery.application.query.dto.Num3WarnQuery;
import com.prize.lottery.application.query.service.INum3WarnQueryService;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.mapper.Num3ComWarnMapper;
import com.prize.lottery.mapper.Num3LayerMapper;
import com.prize.lottery.po.lottery.Num3ComWarningPo;
import com.prize.lottery.po.lottery.Num3LayerFilterPo;
import com.prize.lottery.vo.Num3LayerStateVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class Num3WarnQueryService implements INum3WarnQueryService {

    private final Num3ComWarnMapper warnMapper;
    private final Num3LayerMapper   layerMapper;

    @Override
    public Page<Num3ComWarningPo> getComWarningList(Num3WarnQuery query) {
        return query.from().count(warnMapper::countNum3ComWarns).query(warnMapper::getNum3ComWarnList);
    }

    @Override
    public Num3ComWarningPo getComWarning(Long id) {
        return warnMapper.getNum3ComWarnById(id).orElseThrow(ResponseHandler.WARNING_NONE);
    }

    @Override
    public List<String> comWarningPeriods(LotteryEnum type) {
        return warnMapper.comWarnPeriods(type);
    }

    @Override
    public Page<Num3LayerFilterPo> getNum3LayerList(Num3LayerQuery query) {
        return query.from().count(layerMapper::countNum3LayerFilters).query(layerMapper::getNum3LayerFilters);
    }

    @Override
    public Num3LayerFilterPo getNum3Layer(Long id) {
        Num3LayerFilterPo num3Layer = layerMapper.getNum3LayerFilter(id);
        return Assert.notNull(num3Layer, ResponseHandler.LAYER_NONE);
    }

    @Override
    public List<String> num3LayerPeriods(LotteryEnum type) {
        return layerMapper.layerPeriods(type);
    }

    @Override
    public Num3LayerStateVo getNum3LayerState(LotteryEnum type) {
        Assert.state(type == LotteryEnum.FC3D || type == LotteryEnum.PL3, ResponseHandler.LOTTERY_TYPE_ERROR);
        return layerMapper.getNum3LayerState(type).orElseGet(() -> Num3LayerStateVo.empty(type));
    }

}
