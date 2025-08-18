package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.domain.voucher.model.aggregate.BatchUserVoucherDo;
import com.prize.lottery.domain.voucher.model.entity.UserVoucher;
import com.prize.lottery.domain.voucher.repository.IUserVoucherRepository;
import com.prize.lottery.domain.voucher.valobj.VoucherOperation;
import com.prize.lottery.infrast.persist.mapper.VoucherInfoMapper;
import com.prize.lottery.infrast.persist.po.UserVoucherLogPo;
import com.prize.lottery.infrast.persist.po.UserVoucherPo;
import com.prize.lottery.infrast.repository.converter.VoucherConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserVoucherRepository implements IUserVoucherRepository {

    private final VoucherInfoMapper mapper;
    private final VoucherConverter  converter;

    @Override
    public void save(Aggregate<Long, BatchUserVoucherDo> aggregate) {
        BatchUserVoucherDo     root       = aggregate.getRoot();
        List<VoucherOperation> operations = root.getOperations();
        if (!CollectionUtils.isEmpty(operations)) {
            List<UserVoucherPo> userVouchers = converter.toVoucherPoList(operations);
            mapper.saveUserVoucher(userVouchers);
        }
        if (root.isNew()) {
            List<UserVoucher>      vouchers    = root.getVouchers();
            List<UserVoucherLogPo> voucherLogs = converter.toLogPoList(vouchers);
            mapper.addUserVoucherLogs(voucherLogs);
            return;
        }
        List<UserVoucher> vouchers = aggregate.changedEntities(BatchUserVoucherDo::getVouchers);
        if (!CollectionUtils.isEmpty(vouchers)) {
            List<UserVoucherLogPo> voucherLogs = converter.toLogPoList(vouchers);
            mapper.editUserVoucherLogs(voucherLogs);
        }
    }

    @Override
    public Aggregate<Long, BatchUserVoucherDo> ofUser(Long userId, Integer limit) {
        List<UserVoucherLogPo> voucherLogs      = mapper.getUnUsedVoucher(userId, limit);
        List<UserVoucher>      userVouchers     = converter.toLogDoList(voucherLogs);
        BatchUserVoucherDo     batchUserVoucher = new BatchUserVoucherDo(userVouchers);
        return AggregateFactory.create(batchUserVoucher);
    }

    @Override
    public Aggregate<Long, BatchUserVoucherDo> ofExpired(Integer limit) {
        List<UserVoucherLogPo> voucherLogs      = mapper.getExpiredUserVouchers(limit);
        List<UserVoucher>      userVouchers     = converter.toLogDoList(voucherLogs);
        BatchUserVoucherDo     batchUserVoucher = new BatchUserVoucherDo(userVouchers);
        return AggregateFactory.create(batchUserVoucher);
    }

    @Override
    public LocalDateTime lastDraw(Long userId, String seqNo) {
        return Optional.ofNullable(mapper.getLatestUserVoucher(userId, seqNo))
                       .map(UserVoucherLogPo::getGmtCreate)
                       .orElse(null);
    }

    @Override
    public Map<String, LocalDateTime> lastDraws(Long userId, List<String> seqNos) {
        List<UserVoucherLogPo> userVouchers = mapper.getLatestUserVouchers(userId, seqNos);
        return userVouchers.stream()
                           .collect(Collectors.toMap(UserVoucherLogPo::getBizNo, UserVoucherLogPo::getGmtCreate));
    }

}
