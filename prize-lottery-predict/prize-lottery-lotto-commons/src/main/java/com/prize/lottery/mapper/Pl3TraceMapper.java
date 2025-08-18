package com.prize.lottery.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.po.trace.No3TraceResultPo;
import com.prize.lottery.po.trace.TraceMasterPo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Pl3TraceMapper {

    int addTraceMasters(TraceMasterPo master);

    int delTraceMaster(String masterId);

    TraceMasterPo getTraceMaster(String masterId);

    List<TraceMasterPo> getAllTraceMasters();

    int addTraceResults(List<No3TraceResultPo> results);

    int editTraceResults(List<No3TraceResultPo> results);

    No3TraceResultPo getTraceResult(String period);

    List<No3TraceResultPo> getTraceResultList(PageCondition condition);

    int countTraceResults(PageCondition condition);

}
