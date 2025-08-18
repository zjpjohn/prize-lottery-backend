package com.prize.lottery.domain.app.model;

import com.cloud.arch.aggregate.AggregateRoot;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.app.valobj.AppResourceVal;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.ResourceState;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@NoArgsConstructor
public class AppResourceDo implements AggregateRoot<Long> {

    private static final long serialVersionUID = -5851200619326690523L;

    private Long           id;
    private AppResourceVal resource;
    private String         lastUri;
    private ResourceState  state;

    public AppResourceDo(AppResourceVal resource) {
        this.resource = resource;
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }

    /**
     * 设置新的uri
     */
    public void newResource(AppResourceVal source) {
        this.resource.newResource(source);
        if (StringUtils.isNotBlank(source.getUri())) {
            this.lastUri = source.getUri();
        }
    }

    /**
     * 回滚正在使用的资源
     */
    public void rollbackResource() {
        Assert.state(this.state == ResourceState.INVALID, ResponseHandler.DATA_STATE_ILLEGAL);
        this.resource.setUri(this.lastUri);
        this.lastUri = "";
    }

    public void useResource() {
        Assert.state(this.state == ResourceState.CREATED, ResponseHandler.DATA_STATE_ILLEGAL);
        this.state = ResourceState.USING;
    }

    public void underResource() {
        Assert.state(this.state == ResourceState.USING, ResponseHandler.DATA_STATE_ILLEGAL);
        this.state = ResourceState.CREATED;
    }

    public void invalidResource() {
        Assert.state(this.state == ResourceState.CREATED, ResponseHandler.DATA_STATE_ILLEGAL);
        this.state = ResourceState.INVALID;
    }

}
