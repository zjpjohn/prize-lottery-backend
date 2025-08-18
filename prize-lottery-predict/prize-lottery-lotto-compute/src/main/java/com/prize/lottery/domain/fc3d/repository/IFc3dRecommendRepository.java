package com.prize.lottery.domain.fc3d.repository;



import com.prize.lottery.domain.share.model.N3ComRecommendDo;

import java.util.Optional;

public interface IFc3dRecommendRepository {

    void save(N3ComRecommendDo recommend);

    Optional<N3ComRecommendDo> getBy(String period);

}
