package com.prize.lottery.infrast.persist.valobj;

import com.prize.lottery.infrast.persist.po.AppResourcePo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppResource implements Serializable {

    private static final long serialVersionUID = -6938636061113465873L;

    private String        uri;
    private String        defUri;
    private ResourceSpecs specs;

    public AppResource(AppResourcePo resource) {
        this.uri    = resource.getUri();
        this.defUri = resource.getDefUri();
        this.specs  = resource.getSpecs();
    }
}
