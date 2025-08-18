package com.prize.lottery.infrast.persist.vo;

import com.prize.lottery.infrast.persist.enums.TimeUnit;
import lombok.Data;

import java.util.List;

@Data
public class PackInfoVo {

    private String              packNo;
    private String              name;
    private String              remark;
    private Long                price;
    private Long                discount;
    private Integer             duration;
    private TimeUnit            timeUnit;
    private Integer             priority;
    private List<PackPrivilege> privileges;

    @Data
    public static class PackPrivilege {

        private Long   id;
        private String name;
        private String icon;
        private String content;

    }
}
