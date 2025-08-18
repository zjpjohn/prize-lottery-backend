package com.prize.lottery.po.master;

import com.prize.lottery.enums.LotteryEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MasterEvictPo {

    private Long          id;
    private LotteryEnum   type;
    private String        masterId;
    private LocalDateTime gmtCreate;

    public MasterEvictPo(LotteryEnum type, String masterId) {
        this.type     = type;
        this.masterId = masterId;
    }

    public static MasterEvictPo fc3d(String masterId) {
        return new MasterEvictPo(LotteryEnum.FC3D, masterId);
    }

    public static MasterEvictPo pl3(String masterId) {
        return new MasterEvictPo(LotteryEnum.PL3, masterId);
    }

    public static MasterEvictPo ssq(String masterId) {
        return new MasterEvictPo(LotteryEnum.SSQ, masterId);
    }

    public static MasterEvictPo dlt(String masterId) {
        return new MasterEvictPo(LotteryEnum.DLT, masterId);
    }

    public static MasterEvictPo qlc(String masterId) {
        return new MasterEvictPo(LotteryEnum.QLC, masterId);
    }
}
