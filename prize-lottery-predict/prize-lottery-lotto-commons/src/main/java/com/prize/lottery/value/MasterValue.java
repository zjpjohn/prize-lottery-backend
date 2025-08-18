package com.prize.lottery.value;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MasterValue {
    //专家标识
    private String  masterId;
    //专家名称
    private String  name;
    //专家头像
    private String  avatar;
    //浏览量
    private Integer browse;
    //订阅量
    private Integer subscribe;
    //搜索量
    private Integer searches;

    public MasterValue(String masterId, String name, String avatar) {
        this.masterId = masterId;
        this.name     = name;
        this.avatar   = avatar;
    }
}
