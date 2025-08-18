package com.prize.lottery.infrast.persist.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.infrast.persist.po.ExpertAcctPo;
import com.prize.lottery.infrast.persist.po.ExpertIncomePo;
import com.prize.lottery.infrast.persist.po.ExpertMetricsPo;
import com.prize.lottery.infrast.persist.po.ExpertWithdrawPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ExpertAcctMapper {

    int saveExpertAcct(ExpertAcctPo expertAcct);

    ExpertAcctPo getExpertAcctByUserId(Long userId);

    ExpertAcctPo getExpertAcctByMasterId(String masterId);

    int saveExpertBalance(ExpertAcctPo acct);

    int addExpertIncome(ExpertIncomePo incomeLog);

    List<ExpertIncomePo> getExpertIncomeList(PageCondition condition);

    int countExpertIncomes(PageCondition condition);

    int addExpertMetrics(ExpertMetricsPo metrics);

    ExpertMetricsPo getLatestMetrics(Long userId);

    List<ExpertMetricsPo> getExpertMetricsList(@Param("userId") Long userId,
                                               @Param("startDay") LocalDate startDay,
                                               @Param("endDay") LocalDate endDay);

    int addExpertWithdraw(ExpertWithdrawPo withdraw);

    int editExpertWithdraw(ExpertWithdrawPo withdraw);

    ExpertWithdrawPo getWithdrawById(Long id);

    ExpertWithdrawPo getWithdrawBySeqNo(String seqNo);

    ExpertWithdrawPo latestWithdraw(Long userId);

    List<ExpertWithdrawPo> getExpertWithdrawList(PageCondition condition);

    int countExpertWithdraws(PageCondition condition);

}
