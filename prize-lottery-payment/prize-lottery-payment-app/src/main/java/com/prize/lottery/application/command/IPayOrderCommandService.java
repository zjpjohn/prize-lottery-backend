package com.prize.lottery.application.command;


import com.prize.lottery.application.command.dto.ChargeOrderCreateCmd;
import com.prize.lottery.application.command.dto.MemberOrderCreateCmd;
import com.prize.lottery.application.command.vo.UnionOrderVo;
import com.prize.lottery.application.command.vo.WxNotifyResult;
import jakarta.servlet.http.HttpServletRequest;

public interface IPayOrderCommandService {

    String aliPayNotify(HttpServletRequest request);

    WxNotifyResult wxPayNotify(HttpServletRequest request);

    UnionOrderVo memberPrepay(MemberOrderCreateCmd command);

    UnionOrderVo repayOrder(String orderNo);

    UnionOrderVo chargePrepay(ChargeOrderCreateCmd command);

}
