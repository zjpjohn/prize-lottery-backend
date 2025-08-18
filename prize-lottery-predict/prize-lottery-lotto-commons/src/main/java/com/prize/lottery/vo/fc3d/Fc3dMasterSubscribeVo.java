package com.prize.lottery.vo.fc3d;

import com.prize.lottery.value.MasterValue;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Fc3dMasterSubscribeVo {

    private Long          id;
    private Long          userId;
    private MasterValue   master;
    private StatHitValue  dan3;
    private StatHitValue  com7;
    private StatHitValue  kill1;
    private LocalDateTime gmtCreate;
}
