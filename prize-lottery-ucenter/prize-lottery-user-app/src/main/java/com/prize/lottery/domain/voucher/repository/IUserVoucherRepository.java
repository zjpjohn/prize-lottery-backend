package com.prize.lottery.domain.voucher.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.voucher.model.aggregate.BatchUserVoucherDo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface IUserVoucherRepository {

    void save(Aggregate<Long, BatchUserVoucherDo> aggregate);

    Aggregate<Long, BatchUserVoucherDo> ofUser(Long userId, Integer limit);

    Aggregate<Long, BatchUserVoucherDo> ofExpired(Integer limit);

    LocalDateTime lastDraw(Long userId, String seqNo);

    Map<String, LocalDateTime> lastDraws(Long userId, List<String> seqNo);

}
