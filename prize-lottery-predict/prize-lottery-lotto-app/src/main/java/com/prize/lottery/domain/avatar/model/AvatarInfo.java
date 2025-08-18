package com.prize.lottery.domain.avatar.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AvatarInfo {

    private Long   id;
    private String key;
    private String uri;

    public AvatarInfo(String key, String uri) {
        this.key = key;
        this.uri = uri;
    }
}
