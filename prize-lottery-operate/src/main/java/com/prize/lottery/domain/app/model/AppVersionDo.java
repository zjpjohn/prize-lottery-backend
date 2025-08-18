package com.prize.lottery.domain.app.model;

import com.cloud.arch.aggregate.AggregateRoot;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.dto.VersionCreateCmd;
import com.prize.lottery.application.command.dto.VersionModifyCmd;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.VersionState;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;

@Data
@NoArgsConstructor
public class AppVersionDo implements AggregateRoot<Long> {

    private static final long serialVersionUID = -9103660357964073358L;

    private Long          id;
    private String        appNo;
    private String        appVer;
    private String        appDir;
    private String        appUnity;
    private String        appV7a;
    private String        appV8a;
    private Integer       force;
    private String        depiction;
    private List<String>  upgrades;
    private List<String>  images;
    private VersionState  state;
    private LocalDateTime online;

    public AppVersionDo(VersionCreateCmd command, BiConsumer<VersionCreateCmd, AppVersionDo> converter) {
        this.state = VersionState.PRE_LINE;
        converter.accept(command, this);
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }

    public void modify(VersionModifyCmd command, BiConsumer<VersionModifyCmd, AppVersionDo> converter) {
        converter.accept(command, this);
    }

    public void issueMainVersion() {
        Assert.state(this.state == VersionState.ON_LINE, ResponseHandler.APP_VERSION_STATE_ILLEGAL);
        this.state = VersionState.MAINTAIN;
    }

    /**
     * 版本上线
     */
    public void online() {
        Assert.state(this.state == VersionState.PRE_LINE, "版本上线状态错误.");
        this.state  = VersionState.ON_LINE;
        this.online = LocalDateTime.now();
    }

    /**
     * 版本下线
     */
    public void offline() {
        Assert.state(this.state == VersionState.ON_LINE, "版本下线状态错误.");
        this.state  = VersionState.OFF_LINE;
        this.online = LocalDateTime.now();
    }

}
