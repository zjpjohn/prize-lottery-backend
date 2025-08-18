package com.prize.lottery.application.query.dto;

import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.enums.DltChannel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DltLottoMasterQuery extends BaseLottoMasterQuery {

    private static final long serialVersionUID = -4701451236261674530L;

    @Enumerable(enums = DltChannel.class, message = "类型错误")
    private String type;

}
