package com.prize.lottery.domain.voucher.valobj;

import com.prize.lottery.domain.voucher.model.aggregate.VoucherInfoDo;
import lombok.Getter;

@Getter
public class DrawVoucherObj {

    private final String  seqNo;
    private final Integer voucher;
    private final Integer expire;

    public DrawVoucherObj(String seqNo, Integer voucher, Integer expire) {
        this.seqNo   = seqNo;
        this.voucher = voucher;
        this.expire  = expire;
    }

    public DrawVoucherObj(VoucherInfoDo voucher) {
        this.seqNo   = voucher.getSeqNo();
        this.voucher = voucher.getVoucher();
        this.expire  = voucher.getExpire();
    }

}
