package com.prize.lottery.application.executor;

import com.cloud.arch.utils.CollectionUtils;
import com.prize.lottery.domain.n3item.domain.N3ItemCensusDo;
import com.prize.lottery.domain.n3item.valobj.N3ItemCensus;
import com.prize.lottery.domain.n3item.valobj.N3ItemForecast;
import com.prize.lottery.enums.Fc3dChannel;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.mapper.Fc3dIcaiMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class Fc3dItemAnalyzeExe {

    private final Fc3dIcaiMapper mapper;

    public N3ItemCensusDo execute(String period) {
        String last = LotteryEnum.FC3D.lastPeriod(period);
        List<N3ItemCensus> censusList = Arrays.stream(Fc3dChannel.values())
                                              .filter(Fc3dChannel::isVipChannel)
                                              .map(channel -> mapper.getFc3dRankedDataList(channel.getChannel(), period, last, 15))
                                              .filter(CollectionUtils::isNotEmpty)
                                              .map(list -> list.stream()
                                                               .map(N3ItemForecast::of)
                                                               .collect(Collectors.toList()))
                                              .map(N3ItemCensus::new)
                                              .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(censusList)) {
            return null;
        }
        return new N3ItemCensusDo(censusList);
    }

}
