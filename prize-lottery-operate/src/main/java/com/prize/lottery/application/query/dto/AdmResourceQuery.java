package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import com.prize.lottery.infrast.persist.enums.ResourceState;
import com.prize.lottery.infrast.persist.enums.ResourceType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class AdmResourceQuery extends PageQuery {

    private static final long serialVersionUID = -7829970361118357666L;

    @NotBlank(message = "应用标识为空")
    private String        appNo;
    /**
     * 资源状态
     */
    private ResourceState state;
    /**
     * 资源类型
     */
    private ResourceType  type;
}
