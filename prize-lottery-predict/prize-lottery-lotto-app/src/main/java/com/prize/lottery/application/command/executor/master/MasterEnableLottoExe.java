package com.prize.lottery.application.command.executor.master;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.dto.MasterEnableCmd;
import com.prize.lottery.domain.master.model.MasterInfo;
import com.prize.lottery.domain.master.repository.IMasterInfoRepository;
import com.prize.lottery.dto.ExpertAcctRepo;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.facade.ICloudUserAccountFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class MasterEnableLottoExe {

    private final ICloudUserAccountFacade cloudUserAccountFacade;
    private final IMasterInfoRepository   masterInfoRepository;

    /**
     * 开启专家预测彩种
     */
    @Transactional
    public void execute(MasterEnableCmd command) {
        ExpertAcctRepo userExpert = cloudUserAccountFacade.getCloudUserExpert(command.getUserId());
        Assert.notNull(userExpert, ResponseHandler.MASTER_NONE);

        LotteryEnum lottery    = LotteryEnum.findOf(command.getEnable());
        MasterInfo  masterInfo = masterInfoRepository.getMasterInfo(userExpert.getMasterId());

        MasterInfo enabled = masterInfo.enableLotto(lottery);
        masterInfoRepository.saveMasterInfo(enabled);
    }

}
