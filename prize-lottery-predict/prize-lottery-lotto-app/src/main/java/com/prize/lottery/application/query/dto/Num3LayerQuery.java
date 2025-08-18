package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.validation.annotation.Enumerable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class Num3LayerQuery extends PageQuery {

    private static final long serialVersionUID = -9131238417386126404L;

    @NotBlank(message = "彩种类型不允许为空")
    @Enumerable(ranges = {"fc3d", "pl3"}, message = "彩种类型错误")
    private String  type;
    @Enumerable(ranges = {"0", "1"}, message = "是否编辑标识错误")
    private Integer edit;
    @Enumerable(ranges = {"-1", "0", "1", "2"}, message = "命中表示错误")
    private Integer hit;
    private String  period;

}
