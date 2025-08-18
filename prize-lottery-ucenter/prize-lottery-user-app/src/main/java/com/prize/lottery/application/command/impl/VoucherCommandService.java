package com.prize.lottery.application.command.impl;

import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.application.assembler.VoucherAssembler;
import com.prize.lottery.application.command.IVoucherCommandService;
import com.prize.lottery.application.command.dto.DrawBatchVoucherCmd;
import com.prize.lottery.application.command.dto.DrawVoucherCmd;
import com.prize.lottery.application.command.dto.VoucherCreateCmd;
import com.prize.lottery.application.command.dto.VoucherEditCmd;
import com.prize.lottery.application.command.executor.UserDrawBatchExecutor;
import com.prize.lottery.application.command.executor.UserDrawVoucherExecutor;
import com.prize.lottery.application.command.executor.UserVoucherExpireExecutor;
import com.prize.lottery.application.command.vo.DrawVoucherVo;
import com.prize.lottery.domain.voucher.model.aggregate.VoucherInfoDo;
import com.prize.lottery.domain.voucher.repository.IVoucherInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoucherCommandService implements IVoucherCommandService {

    private final VoucherAssembler          voucherAssembler;
    private final IVoucherInfoRepository    voucherRepository;
    private final UserDrawBatchExecutor     drawBatchExecutor;
    private final UserDrawVoucherExecutor   drawVoucherExecutor;
    private final UserVoucherExpireExecutor voucherExpireExecutor;

    @Override
    @Transactional
    public void createVoucher(VoucherCreateCmd command) {
        VoucherInfoDo voucherInfo = new VoucherInfoDo(command.validate(), voucherAssembler::toDo);
        AggregateFactory.create(voucherInfo).save(voucherRepository::save);
    }

    @Override
    @Transactional
    public void editVoucher(VoucherEditCmd command) {
        voucherRepository.ofId(command.getId())
                         .peek(root -> root.modify(command, voucherAssembler::toDo))
                         .save(voucherRepository::save);
    }

    @Override
    @Transactional
    public DrawVoucherVo drawVoucher(DrawVoucherCmd command) {
        return drawVoucherExecutor.execute(command);
    }

    @Override
    @Transactional
    public List<DrawVoucherVo> drawBatchVoucher(DrawBatchVoucherCmd command) {
        return drawBatchExecutor.execute(command);
    }

    @Override
    @Transactional
    public Integer voucherExpire(Integer limit) {
        return voucherExpireExecutor.execute(limit);
    }

}
