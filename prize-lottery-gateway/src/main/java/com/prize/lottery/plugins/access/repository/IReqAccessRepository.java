package com.prize.lottery.plugins.access.repository;


import com.prize.lottery.plugins.access.domain.WhitePattern;

import java.util.List;

public interface IReqAccessRepository {

    List<WhitePattern> getWhitePatterns();

    List<String> getBlackList();
}
