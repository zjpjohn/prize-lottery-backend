package com.prize.lottery.infrast.persist.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PackPrivilegePo {

    private Long          id;
    private String        packNo;
    private String        name;
    private String        icon;
    private String        content;
    private Integer       sorted;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;


}
