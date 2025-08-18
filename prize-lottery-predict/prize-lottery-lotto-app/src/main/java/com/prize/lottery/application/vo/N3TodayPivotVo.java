package com.prize.lottery.application.vo;

import com.prize.lottery.value.ForecastValue;
import com.prize.lottery.value.MasterValue;
import com.prize.lottery.value.StatHitValue;
import com.prize.lottery.vo.LotteryMasterVo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class N3TodayPivotVo {

    private String            period;
    private ForecastValue     best;
    private ForecastValue     second;
    private ForecastValue     quality;
    private Integer           edits;
    private Integer           browse;
    private LocalDateTime     calcTime;
    private LocalDateTime     gmtModify;
    private List<PivotMaster> masters;

    @Data
    @NoArgsConstructor
    public static class PivotMaster {
        private MasterValue  master;
        private StatHitValue hit;
        private Integer      rank;

        public PivotMaster(LotteryMasterVo masterVo) {
            this.master = masterVo.getMaster();
            this.hit    = masterVo.getHit();
            this.rank   = masterVo.getIRank();
        }
    }
}
