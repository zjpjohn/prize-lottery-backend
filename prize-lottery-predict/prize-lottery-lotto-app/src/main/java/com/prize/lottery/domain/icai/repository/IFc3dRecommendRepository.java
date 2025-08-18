package com.prize.lottery.domain.icai.repository;


import com.prize.lottery.domain.icai.fc3d.Fc3dComRecommendDo;

import java.util.Optional;

public interface IFc3dRecommendRepository {

    void saveComRecommend(Fc3dComRecommendDo recommend);

    Optional<Fc3dComRecommendDo> getBy(String period);

}
