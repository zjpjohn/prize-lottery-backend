package com.prize.lottery.value;

import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import lombok.Data;

@Data
@TypeHandler(type = Type.JSON)
public class AroundResult {

    private Integer lotto;
    private Integer level1;
    private Integer level2;
    private Integer tuo;

}
