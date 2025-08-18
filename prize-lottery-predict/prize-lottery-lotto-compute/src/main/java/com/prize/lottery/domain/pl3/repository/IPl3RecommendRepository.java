package com.prize.lottery.domain.pl3.repository;


import com.prize.lottery.domain.share.model.N3ComRecommendDo;

import java.util.Optional;

public interface IPl3RecommendRepository {

    void save(N3ComRecommendDo recommend);

    Optional<N3ComRecommendDo> getBy(String period);

}
