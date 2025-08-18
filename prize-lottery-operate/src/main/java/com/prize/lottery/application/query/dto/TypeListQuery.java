package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TypeListQuery extends PageQuery {
    private static final long serialVersionUID = -5283541890474778255L;

    private String appNo;
    private String version;

}
