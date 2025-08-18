package com.prize.lottery.domain.withdraw.model;


import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.builder.MessageBuilder;
import com.prize.lottery.event.MessageType;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.TransferScene;
import com.prize.lottery.infrast.persist.enums.WithdrawRuleState;

import java.time.format.DateTimeFormatter;

public class WithdrawRuleHint {

    private final WithdrawRuleDo rule;

    private WithdrawRuleHint(WithdrawRuleDo rule) {
        this.rule = rule;
    }

    public static WithdrawRuleHint of(WithdrawRuleDo rule) {
        return new WithdrawRuleHint(rule);
    }

    /**
     * 提现规则启用发布变更通知
     */
    public void pushUseMessage() {
        Assert.state(this.rule.getState() == WithdrawRuleState.ISSUED, ResponseHandler.DATA_STATE_ILLEGAL);
        TransferScene scene   = TransferScene.of(this.rule.getScene());
        String        title   = scene.getRemark() + "最新提现规则启用公告";
        String        content = this.useMessageContent();
        this.pushMessage(title, content, "规则启用");
    }

    /**
     * 提现规则预发布推送变更通知
     */
    public void pushPreMessage() {
        Assert.state(this.rule.getState() == WithdrawRuleState.PRE_ISSUED, ResponseHandler.DATA_STATE_ILLEGAL);
        TransferScene scene   = TransferScene.of(this.rule.getScene());
        String        title   = scene.getRemark() + "提现规则即将调整公告";
        String        content = this.preMessageContent();
        this.pushMessage(title, content, "规则预发布");
    }

    private String useMessageContent() {
        return "尊敬的用户，新的提现规则已经启用，单次提现门槛调整为'"
                + this.rule.getThrottle()
                + "分'，提现上限值调整为'"
                + this.rule.getMaximum()
                + "分'，提现间隔时间调整为'"
                + this.rule.getInterval()
                + "天'。感谢您对本应用的大力支持🎁🎁🎁";
    }

    private String preMessageContent() {
        String time = this.rule.getStartTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        return "尊敬的用户，新的提现规则将于"
                + time
                + "启用，届时单次提现门槛调整为'"
                + this.rule.getThrottle()
                + "分'，提现上限值调整为'"
                + this.rule.getMaximum()
                + "分'，提现间隔时间调整为'"
                + this.rule.getInterval()
                + "天'。"
                + "感谢您对本应用的大力支持🎁🎁🎁";
    }

    /**
     * 发布消息到消息中心
     *
     * @param title   消息标题
     * @param content 消息内容
     * @param action  消息动作
     */
    private void pushMessage(String title, String content, String action) {
        MessageBuilder.announce(MessageType.LINK)
                      .title(title)
                      .channel("account_assistant")//消息渠道
                      .content(content)
                      .link("/account")//app跳转链接
                      .linkText("我的账户")
                      .objId(this.rule.getId().toString())
                      .objType("withdraw_rule")
                      .action(action)//消息动作
                      .publish("cloud-lottery-user-topic", "message-center");
    }

}
