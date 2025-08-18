package com.prize.lottery.domain.app.model;

import com.cloud.arch.aggregate.AggregateRoot;
import com.cloud.arch.utils.IdWorker;
import com.prize.lottery.application.command.dto.AppInfoCreateCmd;
import com.prize.lottery.application.command.dto.AppInfoModifyCmd;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.function.BiConsumer;

@Data
@NoArgsConstructor
public class AppInfoDo implements AggregateRoot<Long> {

    private static final long serialVersionUID = -1641865982773226361L;

    private Long   id;
    private String seqNo;
    private String name;
    private String logo;
    private String shareUri;
    private String openInstall;
    private String copyright;
    private String corp;
    private String telephone;
    private String address;
    private String record;
    private String remark;

    public AppInfoDo(AppInfoCreateCmd command, BiConsumer<AppInfoCreateCmd, AppInfoDo> converter) {
        this.seqNo = IdWorker.uuid();
        converter.accept(command, this);
    }

    public void modify(AppInfoModifyCmd command, BiConsumer<AppInfoModifyCmd, AppInfoDo> converter) {
        converter.accept(command, this);
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }

}
