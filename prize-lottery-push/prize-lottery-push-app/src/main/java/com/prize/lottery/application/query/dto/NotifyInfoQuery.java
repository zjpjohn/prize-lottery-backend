package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.infrast.persist.enums.CommonState;
import com.prize.lottery.infrast.persist.enums.NotifyNotice;
import com.prize.lottery.infrast.persist.enums.OpenType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class NotifyInfoQuery extends PageQuery {

    private static final long serialVersionUID = -3688256702290488024L;

    @NotNull(message = "标签分组标识为空")
    private Long         groupId;
    @Enumerable(ranges = {"1", "2"}, message = "推送类型错误")
    private Integer      type;
    @Enumerable(ranges = {"1", "2"}, message = "在线类型错误")
    private Integer      online;
    private NotifyNotice notice;
    private OpenType     openType;
    private CommonState  state;

}
