package com.prize.lottery.infrast.repository.impl;

import com.prize.lottery.domain.pl3.repository.IPl3RecommendRepository;
import com.prize.lottery.domain.share.model.N3ComRecommendDo;
import com.prize.lottery.infrast.repository.converter.Pl3RecommendConverter;
import com.prize.lottery.mapper.Pl3ComRecommendMapper;
import com.prize.lottery.po.pl3.Pl3ComRecommendPo;
import com.prize.lottery.po.pl3.Pl3EarlyWarningPo;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class Pl3RecommendRepository implements IPl3RecommendRepository {

    private final Pl3ComRecommendMapper pl3ComRecommendMapper;
    private final Pl3RecommendConverter pl3RecommendConverter;

    public Pl3RecommendRepository(Pl3ComRecommendMapper pl3ComRecommendMapper,
                                  Pl3RecommendConverter pl3RecommendConverter) {
        this.pl3ComRecommendMapper = pl3ComRecommendMapper;
        this.pl3RecommendConverter = pl3RecommendConverter;
    }

    @Override
    public void save(N3ComRecommendDo recommend) {
        Pl3ComRecommendPo po = pl3RecommendConverter.toPo(recommend);
        pl3ComRecommendMapper.saveComRecommend(po);
        if (!CollectionUtils.isEmpty(recommend.getWarnings())) {
            List<Pl3EarlyWarningPo> warnings = recommend.getWarnings()
                                                        .stream()
                                                        .map(e -> pl3RecommendConverter.toPo(recommend.getPeriod(), e))
                                                        .collect(Collectors.toList());
            pl3ComRecommendMapper.saveEarlyWarning(warnings);
        }
    }

    @Override
    public Optional<N3ComRecommendDo> getBy(String period) {
        return Optional.ofNullable(pl3ComRecommendMapper.getComRecommend(period)).map(pl3RecommendConverter::toDo);
    }

}
