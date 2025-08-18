package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import com.prize.lottery.infrast.persist.enums.ActivityState;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ActivityListQuery extends PageQuery {

    private static final long serialVersionUID = -4805983848859809795L;

    private ActivityState state;

}
