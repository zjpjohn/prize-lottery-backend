package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.infrast.persist.enums.VoucherState;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdmVoucherQuery extends PageQuery {

    private static final long serialVersionUID = -389516882909194328L;

    @Enumerable(enums = VoucherState.class, message = "代金券状态错误")
    private Integer state;
    @Enumerable(ranges = {"0", "1"}, message = "一次性标识错误")
    private Integer disposable;

}
