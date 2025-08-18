package com.prize.lottery.domain.withdraw.specs;

import com.prize.lottery.infrast.persist.enums.TransferScene;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WithdrawValObj {

    private String        seqNo;
    private Long          userId;
    private TransferScene scene;
    private Long          withdraw;
    private Long          witRmb;

}
