package com.prize.lottery.infrast.persist.po;

import com.prize.lottery.infrast.persist.enums.TransferScene;
import com.prize.lottery.infrast.persist.enums.WithLevelSate;
import com.prize.lottery.infrast.persist.value.WithdrawLevels;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WithdrawLevelPo {

    private Long           id;
    private TransferScene  scene;
    private WithdrawLevels levels;
    private WithLevelSate  state;
    private String         remark;
    private LocalDateTime  gmtCreate;
    private LocalDateTime  gmtModify;

}
