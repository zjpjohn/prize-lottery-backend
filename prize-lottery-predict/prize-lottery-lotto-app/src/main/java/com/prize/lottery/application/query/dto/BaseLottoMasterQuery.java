package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.validation.annotation.Enumerable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BaseLottoMasterQuery extends PageQuery {

    private static final long serialVersionUID = 7488342779616920215L;

    private String  period;
    @Enumerable(ranges = {"0", "1"}, message = "参数错误，可选值[0,1]")
    private Integer vip;
    @Enumerable(ranges = {"0", "1"}, message = "参数错误，可选值[0,1]")
    private Integer hot;

}
