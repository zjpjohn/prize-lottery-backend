package com.prize.lottery.infrast.repository.impl;

import com.google.common.collect.Lists;
import com.prize.lottery.domain.browse.model.ForecastBrowse;
import com.prize.lottery.domain.browse.repository.IUserBrowseRepository;
import com.prize.lottery.domain.master.model.MasterAccumulate;
import com.prize.lottery.infrast.repository.converter.MasterInfoConverter;
import com.prize.lottery.mapper.MasterInfoMapper;
import com.prize.lottery.po.master.MasterBrowsePo;
import com.prize.lottery.po.master.MasterInfoPo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class UserBrowseRepository implements IUserBrowseRepository {

    private final MasterInfoMapper    mapper;
    private final MasterInfoConverter converter;

    @Override
    public void save(ForecastBrowse browse) {
        MasterBrowsePo browsePo = converter.toPo(browse);
        mapper.addMasterBrowse(browsePo);
        MasterAccumulate accumulate = browse.getMaster();
        if (accumulate != null) {
            MasterInfoPo master = converter.toPo(accumulate);
            mapper.accumBatchMasters(Lists.newArrayList(master));
        }
    }

    @Override
    public boolean isBrowsed(ForecastBrowse browse) {
        MasterBrowsePo browsePo = converter.toPo(browse);
        return mapper.hasBrowsedMaster(browsePo) == 1;
    }

    @Override
    public Integer browses(ForecastBrowse browse) {
        return mapper.masterBrowseAmount(browse.getPeriod().getPeriod(), browse.getType().getType(), browse.getSource()
                                                                                                           .getType(), browse.getSourceId());
    }

    @Override
    public Integer browses(String type, Integer source) {
        return mapper.materBrowseSourceAmount(type, source);
    }
}
