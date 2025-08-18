package com.prize.lottery.infrast.persist.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.infrast.persist.po.UserVoucherLogPo;
import com.prize.lottery.infrast.persist.po.UserVoucherPo;
import com.prize.lottery.infrast.persist.po.VoucherInfoPo;
import com.prize.lottery.infrast.persist.vo.UserDrawVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface VoucherInfoMapper {

    int addVoucherInfo(VoucherInfoPo voucher);

    int editVoucherInfo(VoucherInfoPo voucher);

    VoucherInfoPo getVoucherById(Long id);

    VoucherInfoPo getVoucherByNo(String seqNo);

    List<VoucherInfoPo> getVoucherByNoList(List<String> seqNos);

    List<VoucherInfoPo> getUsingVouchers();

    List<VoucherInfoPo> getVoucherInfoList(PageCondition condition);

    int countVoucherInfos(PageCondition condition);

    int saveUserVoucher(List<UserVoucherPo> userVouchers);

    Optional<UserVoucherPo> getUserVoucher(Long userId);

    int addUserVoucherLogs(List<UserVoucherLogPo> logs);

    int editUserVoucherLogs(List<UserVoucherLogPo> logs);

    UserVoucherLogPo getLatestUserVoucher(@Param("userId") Long userId, @Param("bizNo") String bizNo);

    List<UserVoucherLogPo> getLatestUserVouchers(@Param("userId") Long userId, @Param("bizNos") List<String> bizNos);

    List<UserVoucherLogPo> getUnUsedVoucher(@Param("userId") Long userId, @Param("limit") Integer limit);

    List<UserVoucherLogPo> getVoucherLogList(PageCondition condition);

    int countVoucherLogs(PageCondition condition);

    List<UserVoucherLogPo> getExpiredUserVouchers(Integer limit);

    List<UserDrawVo> getLatestUserDraws(Integer limit);
}
