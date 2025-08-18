package com.prize.lottery.application.query.executor;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.prize.lottery.application.assembler.VoucherAssembler;
import com.prize.lottery.application.query.vo.AppVoucherInfoVo;
import com.prize.lottery.application.query.vo.VoucherItemVo;
import com.prize.lottery.infrast.persist.mapper.VoucherInfoMapper;
import com.prize.lottery.infrast.persist.po.UserVoucherLogPo;
import com.prize.lottery.infrast.persist.po.VoucherInfoPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppVoucherQueryExecutor {

    private final VoucherInfoMapper voucherInfoMapper;
    private final VoucherAssembler  voucherAssembler;

    public List<AppVoucherInfoVo> execute(Long userId) {
        List<VoucherInfoPo> vouchers = voucherInfoMapper.getUsingVouchers();
        if (CollectionUtils.isEmpty(vouchers)) {
            return Collections.emptyList();
        }
        List<String>                  bizNos       = Lists.transform(vouchers, VoucherInfoPo::getSeqNo);
        List<UserVoucherLogPo>        userVouchers = voucherInfoMapper.getLatestUserVouchers(userId, bizNos);
        Map<String, UserVoucherLogPo> groupMap     = Maps.uniqueIndex(userVouchers, UserVoucherLogPo::getBizNo);

        List<AppVoucherInfoVo> result = Lists.newArrayList();
        for (VoucherInfoPo voucher : vouchers) {
            UserVoucherLogPo voucherLog = groupMap.get(voucher.getSeqNo());
            if (voucherLog != null && voucher.getDisposable() == 1) {
                //一次性已领取不可领取,直接过滤掉
                continue;
            }
            AppVoucherInfoVo appVoucher = voucherAssembler.toVo(voucher);
            if (voucherLog == null) {
                //未领取可以领取
                appVoucher.setCanDraw(true);
            } else if (voucher.getDisposable() == 0) {
                LocalDateTime lastDraw = voucherLog.getGmtCreate();
                LocalDateTime nextDraw = lastDraw.plusDays(voucher.getInterval());
                appVoucher.setLastDraw(lastDraw);
                appVoucher.setNextDraw(nextDraw);
                //可多次领取代金券，已领取需判断下一次领取时间与当前时间
                appVoucher.setCanDraw(nextDraw.isBefore(LocalDateTime.now()));
            }
            result.add(appVoucher);
        }
        return result;
    }

    public List<VoucherItemVo> canDrawVouchers(Long userId) {
        List<VoucherInfoPo> vouchers = voucherInfoMapper.getUsingVouchers();
        if (CollectionUtils.isEmpty(vouchers)) {
            return Collections.emptyList();
        }
        List<String>                  bizNos       = Lists.transform(vouchers, VoucherInfoPo::getSeqNo);
        List<UserVoucherLogPo>        userVouchers = voucherInfoMapper.getLatestUserVouchers(userId, bizNos);
        Map<String, UserVoucherLogPo> groupMap     = Maps.uniqueIndex(userVouchers, UserVoucherLogPo::getBizNo);
        return vouchers.stream()
                       .filter(voucher -> isCanDraw(voucher, groupMap.get(voucher.getSeqNo())))
                       .map(voucherAssembler::toItemVo)
                       .collect(Collectors.toList());
    }

    private boolean isCanDraw(VoucherInfoPo voucher, UserVoucherLogPo log) {
        if (log == null) {
            return true;
        }
        if (voucher.getDisposable() == 0) {
            LocalDateTime lastDraw = log.getGmtCreate();
            LocalDateTime nextDraw = lastDraw.plusDays(voucher.getInterval());
            return nextDraw.isBefore(LocalDateTime.now());
        }
        return false;
    }
}
