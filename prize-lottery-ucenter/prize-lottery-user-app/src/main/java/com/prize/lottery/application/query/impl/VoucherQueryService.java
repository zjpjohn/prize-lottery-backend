package com.prize.lottery.application.query.impl;

import com.cloud.arch.page.Page;
import com.prize.lottery.application.query.IVoucherQueryService;
import com.prize.lottery.application.query.dto.AdmVoucherLogQuery;
import com.prize.lottery.application.query.dto.AdmVoucherQuery;
import com.prize.lottery.application.query.dto.AppVoucherLogQuery;
import com.prize.lottery.application.query.executor.AppVoucherQueryExecutor;
import com.prize.lottery.application.query.vo.AppVoucherInfoVo;
import com.prize.lottery.application.query.vo.VoucherItemVo;
import com.prize.lottery.infrast.persist.mapper.VoucherInfoMapper;
import com.prize.lottery.infrast.persist.po.UserVoucherLogPo;
import com.prize.lottery.infrast.persist.po.UserVoucherPo;
import com.prize.lottery.infrast.persist.po.VoucherInfoPo;
import com.prize.lottery.infrast.persist.vo.UserDrawVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoucherQueryService implements IVoucherQueryService {

    private final VoucherInfoMapper       voucherMapper;
    private final AppVoucherQueryExecutor appVoucherQueryExecutor;

    @Override
    public VoucherInfoPo getVoucherInfo(String seqNo) {
        return voucherMapper.getVoucherByNo(seqNo);
    }

    @Override
    public Page<VoucherInfoPo> getVoucherList(AdmVoucherQuery query) {
        return query.from().count(voucherMapper::countVoucherInfos).query(voucherMapper::getVoucherInfoList);
    }

    @Override
    public Page<UserVoucherLogPo> getVoucherLogList(AdmVoucherLogQuery query) {
        return query.from().count(voucherMapper::countVoucherLogs).query(voucherMapper::getVoucherLogList);
    }

    @Override
    public Page<UserVoucherLogPo> getUserVoucherLogList(AppVoucherLogQuery query) {
        return query.from().count(voucherMapper::countVoucherLogs).query(voucherMapper::getVoucherLogList);
    }

    @Override
    public List<AppVoucherInfoVo> appVoucherList(Long userId) {
        return appVoucherQueryExecutor.execute(userId);
    }

    @Override
    public List<VoucherItemVo> canDrawVoucherList(Long userId) {
        return appVoucherQueryExecutor.canDrawVouchers(userId);
    }

    @Override
    public UserVoucherPo getUserVoucher(Long userId) {
        return voucherMapper.getUserVoucher(userId).orElseGet(() -> UserVoucherPo.empty(userId));
    }

    @Override
    public List<UserDrawVo> getLatestUserDraw() {
        return voucherMapper.getLatestUserDraws(8);
    }
}
