package com.prize.lottery.application.assembler;

import com.prize.lottery.application.vo.*;
import com.prize.lottery.po.dlt.DltIcaiPo;
import com.prize.lottery.po.fc3d.Fc3dIcaiPo;
import com.prize.lottery.po.pl3.Pl3IcaiPo;
import com.prize.lottery.po.qlc.QlcIcaiPo;
import com.prize.lottery.po.ssq.SsqIcaiPo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ForecastAssembler {

    DltForecastVo toDltVo(DltIcaiPo icai);

    Fc3dForecastVo toFc3dVo(Fc3dIcaiPo icai);

    Pl3ForecastVo toPl3Vo(Pl3IcaiPo icai);

    SsqForecastVo toSsqVo(SsqIcaiPo icai);

    QlcForecastVo toQlcVo(QlcIcaiPo icai);

}
