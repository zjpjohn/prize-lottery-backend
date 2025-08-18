package com.prize.lottery.infrast.persist.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.prize.lottery.infrast.persist.enums.AdminLevel;
import com.prize.lottery.infrast.persist.enums.AdminState;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AdministratorPo {

    private Long          id;
    private String        name;
    @JsonIgnore
    private String        password;
    private String        phone;
    private AdminLevel    level;
    private AdminState    state;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

}
