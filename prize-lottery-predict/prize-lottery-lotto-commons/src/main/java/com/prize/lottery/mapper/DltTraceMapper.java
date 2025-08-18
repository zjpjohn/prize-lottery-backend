package com.prize.lottery.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.po.trace.RbTraceResultPo;
import com.prize.lottery.po.trace.TraceMasterPo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DltTraceMapper {

    int addTraceMasters(List<TraceMasterPo> masters);

    int delTraceMaster(@Param("masterId") String masterId, @Param("type") Integer type);

    TraceMasterPo getTraceMaster(@Param("masterId") String masterId, @Param("type") Integer type);

    List<TraceMasterPo> getAllTraceMasters();

    int addTraceResults(List<RbTraceResultPo> results);

    int editTraceResults(List<RbTraceResultPo> results);

    RbTraceResultPo getTraceResult(String period);

    List<RbTraceResultPo> getTraceResultList(PageCondition condition);

    int countTraceResults(PageCondition condition);

}
