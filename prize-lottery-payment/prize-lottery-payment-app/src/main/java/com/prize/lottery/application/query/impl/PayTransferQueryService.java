package com.prize.lottery.application.query.impl;

import com.cloud.arch.page.Page;
import com.cloud.arch.page.PageCondition;
import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Maps;
import com.prize.lottery.application.assembler.PayTransferAssembler;
import com.prize.lottery.application.query.IPayTransferQueryService;
import com.prize.lottery.application.query.dto.AdmTransferQuery;
import com.prize.lottery.application.query.dto.AppTransferQuery;
import com.prize.lottery.application.query.vo.AdmTransferRecordVo;
import com.prize.lottery.application.query.vo.AppTransferRecordVo;
import com.prize.lottery.domain.facade.IUserAccountFacade;
import com.prize.lottery.domain.facade.dto.UserInfo;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.mapper.TransferRecordMapper;
import com.prize.lottery.infrast.persist.po.TransferAuditPo;
import com.prize.lottery.infrast.persist.po.TransferRecordPo;
import com.prize.lottery.infrast.persist.po.TransferStatisticsPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayTransferQueryService implements IPayTransferQueryService {

    private final IUserAccountFacade   userAccountFacade;
    private final TransferRecordMapper transferRecordMapper;
    private final PayTransferAssembler payTransferAssembler;

    @Override
    public Page<AppTransferRecordVo> appTransferList(AppTransferQuery query) {
        return query.from()
                    .count(transferRecordMapper::countTransferRecords)
                    .query(transferRecordMapper::getTransferRecordList)
                    .map(payTransferAssembler::toAppVo);
    }

    @Override
    public List<TransferAuditPo> transferAuditList(String transNo) {
        return transferRecordMapper.getAuditListByTansNo(transNo);
    }

    @Override
    public AdmTransferRecordVo admTransInfo(String transNo) {
        TransferRecordPo record = transferRecordMapper.getTransferRecord(transNo);
        Assert.notNull(record, ResponseHandler.TRANSFER_RECORD_NONE);
        UserInfo userInfo = userAccountFacade.getUserInfo(record.getUserId());
        return payTransferAssembler.toAdmVo(record, userInfo);
    }

    @Override
    public AppTransferRecordVo appTransInfo(String bizNo) {
        TransferRecordPo record = transferRecordMapper.getTransferRecordByBizNo(bizNo);
        Assert.notNull(record, ResponseHandler.TRANSFER_RECORD_NONE);
        return payTransferAssembler.toAppVo(record);
    }

    @Override
    public Page<AdmTransferRecordVo> admTransferList(AdmTransferQuery query) {
        PageCondition condition = query.from();
        if (StringUtils.hasText(query.getPhone())) {
            UserInfo userInfo = userAccountFacade.getUserInfo(query.getPhone());
            condition.setParam("userId", userInfo.getUserId());
            return condition.count(transferRecordMapper::countTransferRecords)
                            .query(transferRecordMapper::getTransferRecordList)
                            .map(record -> payTransferAssembler.toAdmVo(record, userInfo));
        }
        return condition.count(transferRecordMapper::countTransferRecords)
                        .query(transferRecordMapper::getTransferRecordList)
                        .flatMap(records -> {
                            List<Long> userList = records.stream()
                                                         .map(TransferRecordPo::getUserId)
                                                         .distinct()
                                                         .collect(Collectors.toList());
                            List<UserInfo>      userInfos = userAccountFacade.getUserList(userList);
                            Map<Long, UserInfo> map       = Maps.uniqueIndex(userInfos, UserInfo::getUserId);
                            return records.stream().map(record -> {
                                UserInfo userInfo = map.get(record.getUserId());
                                return payTransferAssembler.toAdmVo(record, userInfo);
                            }).collect(Collectors.toList());
                        });
    }

    @Override
    public List<TransferStatisticsPo> getTransferStatistics() {
        return transferRecordMapper.getLatestTransferMetrics(10);
    }
}
