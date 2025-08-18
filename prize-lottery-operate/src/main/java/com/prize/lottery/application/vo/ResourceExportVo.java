package com.prize.lottery.application.vo;

import com.prize.lottery.infrast.persist.po.AppResourcePo;
import com.prize.lottery.infrast.persist.valobj.ResourceSpecs;
import lombok.Data;

@Data
public class ResourceExportVo {
    //资源名称
    private String        name;
    //资源标识
    private String        feNo;
    //资源类型
    private Integer       type;
    //资源链接
    private String        uri;
    //默认使用链接
    private String        defUri;
    //资源规格
    private ResourceSpecs specs;
    //资源说明可选
    private String        remark;

    public ResourceExportVo(AppResourcePo resource) {
        this.name   = resource.getName();
        this.feNo   = resource.getFeNo();
        this.type   = resource.getType().value();
        this.uri    = resource.getUri();
        this.defUri = resource.getDefUri();
        this.specs  = resource.getSpecs();
        this.remark = resource.getRemark();
    }

}
