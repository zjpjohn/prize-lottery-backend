package com.prize.lottery.domain.channel.model;

import com.cloud.arch.aggregate.AggregateRoot;
import com.cloud.arch.aggregate.Ignore;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.channel.valobj.PutIncrOperation;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.PutState;
import com.prize.lottery.infrast.persist.enums.RegisterChannel;
import com.prize.lottery.infrast.utils.InvCodeGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
public class PutRecordDo implements AggregateRoot<Long> {

    private static final long serialVersionUID = -5506590697973014360L;

    private Long          id;
    private String        appNo;
    private String        channel;
    private String        code;
    private String        invUri;
    private Long          expectAmt;
    private Integer       userCnt;
    private PutState      state;
    private String        remark;
    private LocalDateTime putTime;

    //分享邀请计数
    @Ignore
    private PutIncrOperation operation;

    public PutRecordDo(String appNo, String channel, Long expectAmt, PutState state, String remark, String uriPattern) {
        this.appNo     = appNo;
        this.channel   = channel;
        this.expectAmt = expectAmt;
        this.state     = state;
        this.remark    = remark;
        this.createCodeAndUri(uriPattern);
        if (state == PutState.PUTTING) {
            this.putTime = LocalDateTime.now();
        }
    }

    /**
     * 投放渠道获客计数
     */
    public void putIncrCount() {
        if (this.state == PutState.PUTTING || this.state == PutState.OFFLINE) {
            operation = PutIncrOperation.count();
        }
    }

    /**
     * 生成渠道邀请码和邀请链接
     */
    private void createCodeAndUri(String uriPattern) {
        this.code = InvCodeGenerator.channelCode();
        //分享邀请连接-baseUri/:channel/:code/:appNo
        String format = String.format(uriPattern, RegisterChannel.CHANNEL_INVITE.getChannel(), this.code);
        this.invUri = format + "/" + this.appNo;
    }

    public void modify(String remark, PutState newState) {
        if (newState == null) {
            Assert.state(this.state == PutState.CREATED, ResponseHandler.DATA_STATE_ILLEGAL);
            this.remark = remark;
            return;
        }
        Set<PutState> transitions = this.state.transitions();
        Assert.state(transitions.contains(newState), ResponseHandler.DATA_STATE_ILLEGAL);
        this.state = newState;
        if (this.state == PutState.PUTTING) {
            this.putTime = LocalDateTime.now();
        }
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }

}
