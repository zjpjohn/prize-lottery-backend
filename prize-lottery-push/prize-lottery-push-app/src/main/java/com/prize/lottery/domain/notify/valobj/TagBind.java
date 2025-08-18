package com.prize.lottery.domain.notify.valobj;

import lombok.Getter;

@Getter
public class TagBind {

    private final Long   appKey;
    private final Long   groupId;
    private final Long   tagId;
    private final String deviceId;

    public TagBind(Long appKey, Long groupId, Long tagId, String deviceId) {
        this.appKey   = appKey;
        this.groupId  = groupId;
        this.tagId    = tagId;
        this.deviceId = deviceId;
    }


}
