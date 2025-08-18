package com.prize.lottery.application.assembler;

import com.prize.lottery.application.query.vo.AdmTransferRecordVo;
import com.prize.lottery.application.query.vo.AppTransferRecordVo;
import com.prize.lottery.domain.facade.dto.UserInfo;
import com.prize.lottery.infrast.persist.po.TransferRecordPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PayTransferAssembler {

    AppTransferRecordVo toAppVo(TransferRecordPo record);

    @Mapping(source = "record.state", target = "state")
    AdmTransferRecordVo toAdmVo(TransferRecordPo record, UserInfo user);
}
