package com.prize.lottery.application.query.dto;

import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.enums.QlcChannel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QlcLottoMasterQuery extends BaseLottoMasterQuery {

    private static final long serialVersionUID = -5646886820845007391L;

    @Enumerable(enums= QlcChannel.class, message = "类型错误")
    private String type;

}
