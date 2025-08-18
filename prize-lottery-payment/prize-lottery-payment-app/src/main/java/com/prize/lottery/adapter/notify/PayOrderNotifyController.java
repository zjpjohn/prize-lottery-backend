package com.prize.lottery.adapter.notify;

import com.prize.lottery.application.command.IPayOrderCommandService;
import com.prize.lottery.application.command.impl.PayOrderCommandService;
import com.prize.lottery.application.command.vo.WxNotifyResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@Validated
@RestController
@RequestMapping("/notify")
@RequiredArgsConstructor
public class PayOrderNotifyController {

    private final IPayOrderCommandService payOrderCommandService;

    /**
     * 微信支付回调处理
     */
    @PostMapping("/wxpay")
    public WxNotifyResult wxNotify(HttpServletRequest request) {
        try {
            return payOrderCommandService.wxPayNotify(request);
        } catch (Exception error) {
            log.error("微信支付回调处理异常", error);
        }
        return WxNotifyResult.serverError();
    }

    /**
     * 支付宝支付回调处理
     */
    @PostMapping("/alipay")
    public String aliNotify(HttpServletRequest request) {
        try {
            return payOrderCommandService.aliPayNotify(request);
        } catch (Exception error) {
            log.error("支付宝回调处理错误:", error);
        }
        return PayOrderCommandService.ALI_NOTIFY_FAIL;
    }

}
