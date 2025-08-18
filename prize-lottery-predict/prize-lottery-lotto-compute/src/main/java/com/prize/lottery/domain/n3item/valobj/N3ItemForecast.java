package com.prize.lottery.domain.n3item.valobj;

import com.prize.lottery.value.ForecastValue;
import com.prize.lottery.vo.fc3d.Fc3dIcaiDataVo;
import com.prize.lottery.vo.pl3.Pl3IcaiDataVo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class N3ItemForecast {

    private final String        masterId;
    private final String        period;
    private final Integer       rank;
    private final ForecastValue dan2;
    private final ForecastValue dan3;
    private final ForecastValue kill1;

    public static N3ItemForecast of(Fc3dIcaiDataVo data) {
        return new N3ItemForecast(data.getMasterId(), data.getPeriod(), data.getRank(), data.getDan2(), data.getDan3(), data.getKill1());
    }

    public static N3ItemForecast of(Pl3IcaiDataVo data) {
        return new N3ItemForecast(data.getMasterId(), data.getPeriod(), data.getRank(), data.getDan2(), data.getDan3(), data.getKill1());
    }
}
