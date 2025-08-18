package com.prize.lottery.application.query.impl;

import com.prize.lottery.application.assembler.ChargeConfAssembler;
import com.prize.lottery.application.query.IChargeConfQueryService;
import com.prize.lottery.application.query.vo.ChargeConfVo;
import com.prize.lottery.infrast.persist.enums.CommonState;
import com.prize.lottery.infrast.persist.mapper.ChargeConfMapper;
import com.prize.lottery.infrast.persist.po.ChargeConfPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChargeConfQueryService implements IChargeConfQueryService {

    private final ChargeConfMapper    mapper;
    private final ChargeConfAssembler assembler;

    @Override
    public List<ChargeConfPo> queryConfList() {
        return mapper.getAllConfigList(null);
    }

    @Override
    public List<ChargeConfVo> usingConfList() {
        List<ChargeConfPo> configList = mapper.getAllConfigList(CommonState.USING.getState());
        return assembler.toVoList(configList);
    }

}
