package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.domain.pack.model.aggregate.PackInfoDo;
import com.prize.lottery.domain.pack.repository.IPackInfoRepository;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.mapper.PackInfoMapper;
import com.prize.lottery.infrast.persist.po.PackInfoPo;
import com.prize.lottery.infrast.repository.converter.PackInfoConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PackInfoRepository implements IPackInfoRepository {

    private final PackInfoMapper    mapper;
    private final PackInfoConverter converter;

    @Override
    public void save(Aggregate<Long, PackInfoDo> aggregate) {
        PackInfoDo root = aggregate.getRoot();
        if (root.isNew()) {
            PackInfoPo packInfo = converter.toPo(root);
            mapper.addPackInfo(packInfo);
            return;
        }
        PackInfoDo changed = aggregate.changed();
        if (changed != null) {
            PackInfoPo packInfo = converter.toPo(changed);
            mapper.editPackInfo(packInfo);
        }

    }

    @Override
    public void delete(String packNo) {
        mapper.delPackInfo(packNo);
    }

    @Override
    public Aggregate<Long, PackInfoDo> ofNo(String packNo) {
        return this.ofPackNo(packNo).map(AggregateFactory::create).orElseThrow(ResponseHandler.PACK_NONE);
    }

    @Override
    public boolean existName(String name) {
        return mapper.existPackName(name) == 1;
    }

    /**
     * 查询指定编号的套餐
     *
     * @param packNo 套餐编号
     */
    @Override
    public Optional<PackInfoDo> ofPackNo(String packNo) {
        return Optional.ofNullable(mapper.getPackInfoByNo(packNo)).map(converter::toDo);
    }

}
