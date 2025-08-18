package com.prize.lottery.infrast.persist.vo;

import com.prize.lottery.infrast.persist.po.AppVersionPo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AppVersionVo extends AppVersionPo {

    /**
     * 应用名称
     */
    private String name;
    /**
     * 应用logo
     */
    private String logo;

}
