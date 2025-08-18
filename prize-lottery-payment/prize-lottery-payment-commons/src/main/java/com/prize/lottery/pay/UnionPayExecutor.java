package com.prize.lottery.pay;

import com.cloud.arch.executor.Executor;
import jakarta.servlet.http.HttpServletRequest;


public interface UnionPayExecutor extends Executor<PayChannel> {

    UnionOrder execute(UnionPayRequest request);

    NotifyResult notify(HttpServletRequest request);

    OrderResult query(String orderNo, String mchId);

    CloseResult close(String orderNo, String mchId);

}
