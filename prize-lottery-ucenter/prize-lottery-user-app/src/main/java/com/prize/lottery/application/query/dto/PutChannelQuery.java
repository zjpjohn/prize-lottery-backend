package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import com.prize.lottery.infrast.persist.enums.ChannelState;
import com.prize.lottery.infrast.persist.enums.ChannelType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PutChannelQuery extends PageQuery {
    private static final long serialVersionUID = -4576556755055893157L;

    private String       appNo;
    private ChannelType  type;
    private ChannelState state;

}
