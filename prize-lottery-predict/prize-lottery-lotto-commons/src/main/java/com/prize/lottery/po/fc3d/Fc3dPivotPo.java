package com.prize.lottery.po.fc3d;

import com.prize.lottery.value.ForecastValue;
import com.prize.lottery.value.PivotMaster;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Data
public class Fc3dPivotPo {

    private Long          id;
    //预测期号
    private String        period;
    //第一胆码
    private ForecastValue best;
    //第二胆码
    private ForecastValue second;
    //优质号码
    private ForecastValue quality;
    //推荐专家
    private PivotMaster   master;
    //编辑次数
    private Integer       edits;
    //计算时间
    private LocalDateTime calcTime;
    //数据版本号
    private Integer       version;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

    public List<String> masterIds() {
        return Optional.of(master).map(PivotMaster::getMasters).orElseGet(Collections::emptyList);
    }
}
