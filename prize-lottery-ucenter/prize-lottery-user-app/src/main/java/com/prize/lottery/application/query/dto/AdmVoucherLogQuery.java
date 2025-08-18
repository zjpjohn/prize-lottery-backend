package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.infrast.persist.enums.VoucherExpire;
import com.prize.lottery.infrast.persist.enums.VoucherLogState;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class AdmVoucherLogQuery extends PageQuery {

    private static final long serialVersionUID = 4434561152012427799L;

    @NotBlank(message = "代金券标识为空")
    private String  bizNo;
    @Enumerable(enums = VoucherLogState.class, message = "领取代金券状态错误")
    private Integer state;
    @Enumerable(enums = VoucherExpire.class, message = "代金券过期状态错误")
    private Integer expired;

}
