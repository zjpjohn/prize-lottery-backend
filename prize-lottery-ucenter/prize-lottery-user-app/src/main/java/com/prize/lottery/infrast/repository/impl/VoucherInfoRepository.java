package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.domain.voucher.model.aggregate.VoucherInfoDo;
import com.prize.lottery.domain.voucher.repository.IVoucherInfoRepository;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.mapper.VoucherInfoMapper;
import com.prize.lottery.infrast.persist.po.VoucherInfoPo;
import com.prize.lottery.infrast.repository.converter.VoucherConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class VoucherInfoRepository implements IVoucherInfoRepository {

    private final VoucherInfoMapper mapper;
    private final VoucherConverter  converter;

    @Override
    public void save(Aggregate<Long, VoucherInfoDo> aggregate) {
        VoucherInfoDo root = aggregate.getRoot();
        if (root.isNew()) {
            VoucherInfoPo voucherInfo = converter.toPo(root);
            mapper.addVoucherInfo(voucherInfo);
            return;
        }
        aggregate.ifChanged().map(converter::toPo).ifPresent(mapper::editVoucherInfo);
    }

    @Override
    public Aggregate<Long, VoucherInfoDo> ofId(Long id) {
        return Optional.ofNullable(mapper.getVoucherById(id))
                       .map(converter::toDo)
                       .map(AggregateFactory::create)
                       .orElseThrow(ResponseHandler.VOUCHER_NOT_EXIST);
    }

    @Override
    public VoucherInfoDo ofNo(String seqNo) {
        return Optional.ofNullable(mapper.getVoucherByNo(seqNo))
                       .map(converter::toDo)
                       .orElseThrow(ResponseHandler.VOUCHER_NOT_EXIST);
    }

    @Override
    public List<VoucherInfoDo> ofNoList(List<String> seqNos) {
        return mapper.getVoucherByNoList(seqNos).stream().map(converter::toDo).collect(Collectors.toList());
    }
}
