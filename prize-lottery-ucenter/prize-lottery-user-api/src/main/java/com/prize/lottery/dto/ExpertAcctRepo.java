package com.prize.lottery.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ExpertAcctRepo implements Serializable {
    private static final long serialVersionUID = 8154622731953652871L;

    private Long    userId;
    private String  masterId;
    private Byte    enable;
    private Integer state;
    private String  aliName;
    private String  wxName;

}
