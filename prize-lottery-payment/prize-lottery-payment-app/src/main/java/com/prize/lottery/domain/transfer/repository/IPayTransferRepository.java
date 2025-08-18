package com.prize.lottery.domain.transfer.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.transfer.model.aggregate.PayTransferBatchDo;
import com.prize.lottery.domain.transfer.model.aggregate.PayTransferOneDo;

public interface IPayTransferRepository {

    void saveOne(Aggregate<Long, PayTransferOneDo> aggregate);

    Aggregate<Long, PayTransferOneDo> ofOne(String transNo);

    void saveBatch(Aggregate<Long, PayTransferBatchDo> aggregate);

    Aggregate<Long, PayTransferBatchDo> batchOfBatchNo(String batchNo);

    Aggregate<Long, PayTransferBatchDo> batchOfTransNo(String transNo);

    boolean isAuditing(String transNo);

}
