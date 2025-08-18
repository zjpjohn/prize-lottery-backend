package com.prize.lottery.infrast.persist.po;

import com.prize.lottery.infrast.persist.enums.ResourceState;
import com.prize.lottery.infrast.persist.enums.ResourceType;
import com.prize.lottery.infrast.persist.valobj.ResourceSpecs;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppResourcePo {

    private Long          id;
    /**
     * 应用标识
     */
    private String        appNo;
    /**
     * 资源前端标识
     */
    private String        feNo;
    /**
     * 资源名称
     */
    private String       name;
    /**
     * 资源类型
     */
    private ResourceType type;
    /**
     * 当前使用uri
     */
    private String       uri;
    /**
     * 上一次使用uri(回滚使用)
     */
    private String        lastUri;
    /**
     * 默认图片资源
     */
    private String        defUri;
    /**
     * 资源规格
     */
    private ResourceSpecs specs;
    /**
     * 资源状态
     */
    private ResourceState state;
    /**
     * 资源备注名称
     */
    private String        remark;
    /**
     * 操作时间
     */
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

}
