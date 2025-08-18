package com.prize.lottery.infrast.repository.converter;

import com.prize.lottery.domain.voucher.model.aggregate.VoucherInfoDo;
import com.prize.lottery.domain.voucher.model.entity.UserVoucher;
import com.prize.lottery.domain.voucher.valobj.VoucherOperation;
import com.prize.lottery.infrast.persist.po.UserVoucherLogPo;
import com.prize.lottery.infrast.persist.po.UserVoucherPo;
import com.prize.lottery.infrast.persist.po.VoucherInfoPo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VoucherConverter {

    VoucherInfoPo toPo(VoucherInfoDo voucher);

    VoucherInfoDo toDo(VoucherInfoPo voucher);

    UserVoucherLogPo toLogPo(UserVoucher voucher);

    UserVoucher toLogDo(UserVoucherLogPo log);

    List<UserVoucherLogPo> toLogPoList(List<UserVoucher> vouchers);

    List<UserVoucher> toLogDoList(List<UserVoucherLogPo> logList);

    UserVoucherPo toVoucherPo(VoucherOperation operation);

    List<UserVoucherPo> toVoucherPoList(List<VoucherOperation> operations);

}
