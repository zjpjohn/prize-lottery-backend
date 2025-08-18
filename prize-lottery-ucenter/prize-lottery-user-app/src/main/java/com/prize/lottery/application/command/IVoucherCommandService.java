package com.prize.lottery.application.command;


import com.prize.lottery.application.command.dto.DrawBatchVoucherCmd;
import com.prize.lottery.application.command.dto.DrawVoucherCmd;
import com.prize.lottery.application.command.dto.VoucherCreateCmd;
import com.prize.lottery.application.command.dto.VoucherEditCmd;
import com.prize.lottery.application.command.vo.DrawVoucherVo;

import java.util.List;

public interface IVoucherCommandService {

    void createVoucher(VoucherCreateCmd command);

    void editVoucher(VoucherEditCmd command);

    DrawVoucherVo drawVoucher(DrawVoucherCmd command);

    List<DrawVoucherVo> drawBatchVoucher(DrawBatchVoucherCmd command);

    Integer voucherExpire(Integer limit);

}
