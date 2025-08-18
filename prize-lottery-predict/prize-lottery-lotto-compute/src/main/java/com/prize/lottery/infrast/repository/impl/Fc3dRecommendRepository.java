package com.prize.lottery.infrast.repository.impl;

import com.prize.lottery.domain.fc3d.repository.IFc3dRecommendRepository;
import com.prize.lottery.domain.share.model.N3ComRecommendDo;
import com.prize.lottery.infrast.repository.converter.Fc3dRecommendConverter;
import com.prize.lottery.mapper.Fc3dComRecommendMapper;
import com.prize.lottery.po.fc3d.Fc3dComRecommendPo;
import com.prize.lottery.po.fc3d.Fc3dEarlyWarningPo;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class Fc3dRecommendRepository implements IFc3dRecommendRepository {

    private final Fc3dComRecommendMapper fc3dComRecommendMapper;
    private final Fc3dRecommendConverter fc3dRecommendConverter;

    public Fc3dRecommendRepository(Fc3dComRecommendMapper fc3dComRecommendMapper,
                                   Fc3dRecommendConverter fc3dRecommendConverter) {
        this.fc3dComRecommendMapper = fc3dComRecommendMapper;
        this.fc3dRecommendConverter = fc3dRecommendConverter;
    }

    @Override
    public void save(N3ComRecommendDo recommend) {
        Fc3dComRecommendPo po = fc3dRecommendConverter.toPo(recommend);
        fc3dComRecommendMapper.saveFc3dComRecommend(po);
        if (!CollectionUtils.isEmpty(recommend.getWarnings())) {
            List<Fc3dEarlyWarningPo> warnings = recommend.getWarnings()
                                                         .stream()
                                                         .map(e -> fc3dRecommendConverter.toPo(recommend.getPeriod(), e))
                                                         .collect(Collectors.toList());
            fc3dComRecommendMapper.saveEarlyWarning(warnings);
        }
    }

    @Override
    public Optional<N3ComRecommendDo> getBy(String period) {
        return Optional.ofNullable(fc3dComRecommendMapper.getFc3dComRecommend(period))
                       .map(fc3dRecommendConverter::toDo);
    }

}
