package com.prize.lottery.application.query.dto;

import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.enums.Kl8Channel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Kl8LottoMasterQuery extends BaseLottoMasterQuery {

    private static final long serialVersionUID = 5029612889078083512L;

    @Enumerable(enums = Kl8Channel.class, message = "类型错误")
    private String type;

}
