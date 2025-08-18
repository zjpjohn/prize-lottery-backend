package com.prize.lottery.domain.app.valobj;

import com.prize.lottery.infrast.persist.enums.ResourceType;
import com.prize.lottery.infrast.persist.valobj.AppResource;
import com.prize.lottery.infrast.persist.valobj.ResourceSpecs;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.function.Consumer;

@Data
@NoArgsConstructor
public class AppResourceVal {

    private String        appNo;
    private String        feNo;
    private String        name;
    private ResourceType  type;
    private String        uri;
    private String        defUri;
    private ResourceSpecs specs;
    private String        remark;

    public void newResource(AppResourceVal val) {
        this.nonNullSet(val.getAppNo(), this::setAppNo);
        this.nonNullSet(val.getFeNo(), this::setFeNo);
        this.nonNullSet(val.getName(), this::setName);
        this.nonNullSet(val.getType(), this::setType);
        this.nonNullSet(val.getUri(), this::setUri);
        this.nonNullSet(val.getDefUri(), this::setDefUri);
        this.nonNullSet(val.getSpecs(), this::setSpecs);
        this.nonNullSet(val.getRemark(), this::setRemark);
    }

    public <T> void nonNullSet(T source, Consumer<T> setter) {
        if (source != null) {
            setter.accept(source);
        }
    }

    public AppResource toResource() {
        return new AppResource(this.uri, this.defUri, this.specs);
    }
}
