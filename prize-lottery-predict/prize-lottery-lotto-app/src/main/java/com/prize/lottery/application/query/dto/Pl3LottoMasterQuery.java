package com.prize.lottery.application.query.dto;

import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.enums.Pl3Channel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Pl3LottoMasterQuery extends BaseLottoMasterQuery {

    private static final long serialVersionUID = 4448024045297508308L;

    @Enumerable(enums = Pl3Channel.class, message = "")
    private String type;

}
