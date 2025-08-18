package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.infrast.persist.enums.ConfState;
import com.prize.lottery.infrast.persist.enums.ConfType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AppConfQuery extends PageQuery {

    private static final long serialVersionUID = -4857423106440438811L;

    private String  appNo;
    @Enumerable(enums = ConfType.class, message = "配置值类型错误")
    private Integer type;
    @Enumerable(enums = ConfState.class, message = "配置状态错误")
    private Integer state;

}
