package com.prize.lottery.domain.user.model;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.dto.SubscribeMasterCmd;
import com.prize.lottery.domain.master.model.MasterAccumulate;
import com.prize.lottery.enums.*;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

@Data
@NoArgsConstructor
public class UserSubscribe {

    private Long             id;
    private Long             userId;
    private String           masterId;
    private LotteryEnum      type;
    private String           trace;
    private String           traceZh;
    private Integer          special;
    private MasterAccumulate masterAccumulate;

    public UserSubscribe(SubscribeMasterCmd command) {
        this.userId   = command.getUserId();
        this.masterId = command.getMasterId();
        this.type     = command.getType();
        if (StringUtils.isNotBlank(command.getTrace())) {
            Pair<String, String> pair = this.checkAndSetTrace(command.getTrace());
            this.trace   = pair.getKey();
            this.traceZh = pair.getValue();
        }
    }

    private Pair<String, String> checkAndSetTrace(String field) {
        if (type == LotteryEnum.DLT) {
            DltChannel channel = DltChannel.findOf(field);
            return Pair.of(channel.getChannel(), channel.getNameZh());
        }
        if (type == LotteryEnum.FC3D) {
            Fc3dChannel channel = Fc3dChannel.findOf(field);
            return Pair.of(channel.getChannel(), channel.getNameZh());
        }
        if (type == LotteryEnum.PL3) {
            Pl3Channel channel = Pl3Channel.findOf(field);
            return Pair.of(channel.getChannel(), channel.getNameZh());
        }
        if (type == LotteryEnum.SSQ) {
            SsqChannel channel = SsqChannel.findOf(field);
            return Pair.of(channel.getChannel(), channel.getNameZh());
        }
        if (type == LotteryEnum.QLC) {
            QlcChannel channel = QlcChannel.findOf(field);
            return Pair.of(channel.getChannel(), channel.getNameZh());
        }
        throw ResponseHandler.TRACE_MASTER_ERROR.get();
    }

    /**
     * 设置重点关注或取消
     */
    public UserSubscribe specialOrCancel() {
        UserSubscribe subscribe = new UserSubscribe();
        subscribe.setId(this.id);
        subscribe.setSpecial(this.special == 1 ? 0 : 1);
        return subscribe;
    }

    /**
     * 设置关注转件追踪字段
     */
    public UserSubscribe traceMaster(String trace) {
        Assert.state(StringUtils.isNotBlank(trace), ResponseHandler.TRACE_MASTER_ERROR);
        Pair<String, String> pair = this.checkAndSetTrace(trace);
        Assert.state(!pair.getKey().equals(this.trace), ResponseHandler.HAS_TRACED_MASTER);

        UserSubscribe subscribe = new UserSubscribe();
        subscribe.setId(this.id);
        subscribe.setTrace(pair.getKey());
        subscribe.setTraceZh(pair.getValue());
        return subscribe;
    }

    public UserSubscribe subscribe() {
        this.masterAccumulate = new MasterAccumulate(masterId).subscribe();
        return this;
    }

    public UserSubscribe unsubscribe() {
        this.masterAccumulate = new MasterAccumulate(masterId).unSubscribe();
        return this;
    }
}
