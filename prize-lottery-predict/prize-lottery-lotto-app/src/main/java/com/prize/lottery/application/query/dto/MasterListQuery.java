package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.enums.MasterChannel;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class MasterListQuery extends PageQuery {

    private static final long serialVersionUID = 6034049286401707530L;

    /**
     * 彩种类型错误
     */
    @Enumerable(enums = LotteryEnum.class, message = "彩种类型错误")
    private String  type;
    /**
     * 专家来源渠道
     */
    @Enumerable(enums = MasterChannel.class, message = "来源渠道错误")
    private Integer source;
    /**
     * 手机号
     */
    @Pattern(regexp = "^(1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8})$", message = "手机格式错误")
    private String  phone;
    /**
     * 专家状态
     */
    @Enumerable(ranges = {"0", "1", "2"}, message = "状态错误")
    private Integer state;

}
