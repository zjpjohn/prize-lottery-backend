package com.prize.lottery.application.query.dto;

import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.enums.Fc3dChannel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FsdLottoMasterQuery extends BaseLottoMasterQuery {

    private static final long serialVersionUID = -207959455926234056L;

    @Enumerable(enums = Fc3dChannel.class, message = "类型错误")
    private String type;


}
