package com.prize.lottery.po.master;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AvatarInfoPo {

    private Long          id;
    private String        key;
    private String        uri;
    private LocalDateTime gmtCreate;

    public AvatarInfoPo(String key, String uri) {
        this.key = key;
        this.uri = uri;
    }
}
