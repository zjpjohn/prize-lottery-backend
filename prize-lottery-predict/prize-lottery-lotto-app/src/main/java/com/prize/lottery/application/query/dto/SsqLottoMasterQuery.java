package com.prize.lottery.application.query.dto;

import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.enums.SsqChannel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SsqLottoMasterQuery extends BaseLottoMasterQuery {

    private static final long serialVersionUID = -4321765387728156216L;

    @Enumerable(enums = SsqChannel.class, message = "类型错误")
    private String type;

}
