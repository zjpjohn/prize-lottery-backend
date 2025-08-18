package com.prize.lottery.transfer;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TransBatchResponse {

    private String              batchNo;
    private TransBatchState     state;
    private Long                successAmount;
    private Integer             successNum;
    private Long                failAmount;
    private Integer             failNum;
    private String              closeReason;
    private LocalDateTime       latestTime;
    private List<TransferEntry> items;

    @Data
    public static class TransferEntry {
        private String        transNo;
        private TransferState state;
    }
}
