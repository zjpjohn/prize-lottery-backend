package com.prize.lottery.domain.order.model.entity;

import com.cloud.arch.aggregate.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PackPrivilege implements Entity<Long> {

    private static final long serialVersionUID = -3595849711157047317L;

    private Long    id;
    private String  name;
    private String  icon;
    private String  content;
    private Integer sorted;

}
