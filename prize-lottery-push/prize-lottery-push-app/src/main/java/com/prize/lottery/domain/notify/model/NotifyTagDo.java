package com.prize.lottery.domain.notify.model;

import com.cloud.arch.aggregate.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class NotifyTagDo implements Entity<Long> {

    private static final long serialVersionUID = 5689956680011427897L;

    private Long    id;
    private Long    appKey;
    private Long    groupId;
    private String  tagName;
    private Integer binds;

    public NotifyTagDo(Long appKey, String tagName) {
        this.appKey  = appKey;
        this.tagName = tagName;
        this.binds   = 0;
    }

    public void incrBinds(int binds) {
        this.binds = this.binds + binds;
    }

}
