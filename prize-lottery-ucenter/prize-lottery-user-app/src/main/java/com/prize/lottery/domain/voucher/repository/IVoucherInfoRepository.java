package com.prize.lottery.domain.voucher.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.voucher.model.aggregate.VoucherInfoDo;

import java.util.List;

public interface IVoucherInfoRepository {

    void save(Aggregate<Long, VoucherInfoDo> aggregate);

    Aggregate<Long, VoucherInfoDo> ofId(Long id);

    VoucherInfoDo ofNo(String seqNo);

    List<VoucherInfoDo> ofNoList(List<String> seqNos);

}
