package com.prize.lottery.infrast.persist.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.infrast.persist.po.TransferAuditPo;
import com.prize.lottery.infrast.persist.po.TransferBatchPo;
import com.prize.lottery.infrast.persist.po.TransferRecordPo;
import com.prize.lottery.infrast.persist.po.TransferStatisticsPo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransferRecordMapper {

    int addTransferBatch(TransferBatchPo batch);

    int editTransferBatch(TransferBatchPo batch);

    TransferBatchPo getTransferBatchById(Long id);

    TransferBatchPo getTransferBatch(String batchNo);

    List<TransferBatchPo> getTransferBatchList(PageCondition condition);

    Integer countTransferBatches(PageCondition condition);

    int addTransferRecords(List<TransferRecordPo> records);

    int editTransferRecords(List<TransferRecordPo> record);

    TransferRecordPo getTransferRecordById(Long id);

    TransferRecordPo getTransferRecord(String transNo);

    List<TransferRecordPo> getTransferRecordsByBatchNo(String batchNo);

    TransferRecordPo getTransferRecordByBizNo(String bizNo);

    List<TransferRecordPo> getTransferRecordList(PageCondition condition);

    Integer countTransferRecords(PageCondition condition);

    List<TransferRecordPo> getProcessingTransferList(LocalDateTime time);

    int addTransferAudit(TransferAuditPo audit);

    TransferAuditPo getAuditInfoById(Long id);

    TransferAuditPo getAuditInfo(String auditNo);

    List<TransferAuditPo> getAuditListByTansNo(String transNo);

    int addTransferStatistics(TransferStatisticsPo statistics);

    List<TransferStatisticsPo> getLatestTransferMetrics(Integer limit);

    List<TransferStatisticsPo> getStatisticsList(@Param("startDay") LocalDate startDay,
                                                 @Param("endDay") LocalDate endDay);

    TransferStatisticsPo getDateTransferStatistics(@Param("metricDate") LocalDate metricDate);

    TransferStatisticsPo getTransferSumStatistics(@Param("startDay") LocalDate startDay,
                                                  @Param("endDay") LocalDate endDay);

}
