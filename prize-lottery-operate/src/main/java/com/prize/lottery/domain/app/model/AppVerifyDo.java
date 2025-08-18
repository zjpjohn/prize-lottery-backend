package com.prize.lottery.domain.app.model;

import com.cloud.arch.aggregate.AggregateRoot;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.dto.AppVerifyCreateCmd;
import com.prize.lottery.application.command.dto.AppVerifyModifyCmd;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.CommonState;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.function.BiConsumer;

@Data
@NoArgsConstructor
public class AppVerifyDo implements AggregateRoot<Long> {

    private static final long serialVersionUID = 5904888730493616540L;

    private Long        id;
    //应用标识
    private String      appNo;
    //应用包名
    private String      appPack;
    //应用包签名
    private String      signature;
    //api密钥
    private String      authKey;
    //配置状态
    private CommonState state;
    //授权成功响应码
    private String      success;
    //取消授权响应码
    private String      cancel;
    //降级授权响应码集合，使用","分隔
    private String      downgrades;
    //备注说明
    private String      remark;

    public AppVerifyDo(AppVerifyCreateCmd command, BiConsumer<AppVerifyCreateCmd, AppVerifyDo> converter) {
        converter.accept(command, this);
        this.state = CommonState.CREATED;
    }

    public void modify(AppVerifyModifyCmd command, BiConsumer<AppVerifyModifyCmd, AppVerifyDo> converter) {
        CommonState state = command.getState();
        if (state == null) {
            converter.accept(command, this);
            return;
        }
        Set<CommonState> transitions = this.state.transitions();
        Assert.state(transitions.contains(state), ResponseHandler.DATA_STATE_ILLEGAL);
        this.state = state;
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }

}
