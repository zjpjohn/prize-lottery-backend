package com.prize.lottery.application.assembler;

import com.prize.lottery.application.command.dto.VoucherCreateCmd;
import com.prize.lottery.application.command.dto.VoucherEditCmd;
import com.prize.lottery.application.query.vo.AppVoucherInfoVo;
import com.prize.lottery.application.query.vo.VoucherItemVo;
import com.prize.lottery.domain.voucher.model.aggregate.VoucherInfoDo;
import com.prize.lottery.infrast.persist.po.VoucherInfoPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VoucherAssembler {

    void toDo(VoucherCreateCmd command, @MappingTarget VoucherInfoDo voucher);

    @Mapping(source = "interval", target = "interval")
    @Mapping(source = "disposable", target = "disposable")
    void toDo(VoucherEditCmd command, @MappingTarget VoucherInfoDo voucher);

    AppVoucherInfoVo toVo(VoucherInfoPo voucher);

    VoucherItemVo toItemVo(VoucherInfoPo voucher);

}
