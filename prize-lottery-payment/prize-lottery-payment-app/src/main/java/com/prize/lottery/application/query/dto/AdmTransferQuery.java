package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.Ignore;
import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.infrast.persist.enums.AuditState;
import com.prize.lottery.pay.PayChannel;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdmTransferQuery extends PageQuery {

    private static final long serialVersionUID = -2574515335399907572L;

    /**
     * 提现场景查询
     */
    private String        scene;
    /**
     * 手机号查询
     */
    @Ignore
    @Pattern(regexp = "^(1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8})$", message = "手机格式错误")
    private String        phone;
    /**
     * 提现渠道查询
     */
    @Enumerable(enums = PayChannel.class, message = "提现渠道错误")
    private String        channel;
    /**
     * 审核状态查询
     */
    @Enumerable(enums = AuditState.class, message = "审核状态错误")
    private Integer       audit;
    /**
     * 查询起始时间
     */
    @Past(message = "起始时间为过去时间")
    private LocalDateTime start;
    /**
     * 查询截止时间
     */
    private LocalDateTime end;

}
