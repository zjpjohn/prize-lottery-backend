package com.prize.lottery.domain.agent.model;


import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.builder.MessageBuilder;
import com.prize.lottery.event.MessageType;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.AgentLevel;
import com.prize.lottery.infrast.persist.enums.AgentRuleState;

import java.time.format.DateTimeFormatter;

public class AgentRuleHint {

    private final AgentRuleDo rule;

    private AgentRuleHint(AgentRuleDo rule) {
        this.rule = rule;
    }

    public static AgentRuleHint of(AgentRuleDo rule) {
        return new AgentRuleHint(rule);
    }

    public void pushUseMessage() {
        Assert.state(this.rule.getState() == AgentRuleState.USING, ResponseHandler.DATA_STATE_ILLEGAL);
        String title   = this.rule.getAgent().label() + "最新奖励规则启用公告";
        String content = this.useMessageContent();
        this.pushMessage(title, content, "规则启用");
    }

    public void pushPreMessage() {
        Assert.state(this.rule.getState() == AgentRuleState.PRE_START, ResponseHandler.DATA_STATE_ILLEGAL);
        String title   = this.rule.getAgent().label() + "奖励规则即将调整通知";
        String content = this.preMessageContent();
        this.pushMessage(title, content, "规则预发布");
    }

    private String useMessageContent() {
        if (this.rule.getAgent() == AgentLevel.NORMAL) {
            return "尊敬的用户，新的邀请奖励规则已经启用，邀请奖励金币调整为'"
                    + this.rule.getReward()
                    + "金币'。感谢您对本应用的大力支持🎁🎁🎁";
        }
        int intRatio = Double.valueOf(this.rule.getRatio() * 100).intValue();
        return "尊敬的用户，新的收益规则已经启用，收益分成比例调整为'" + intRatio + "%'。感谢您对本应用的大力支持🎁🎁🎁";
    }

    private String preMessageContent() {
        String time = this.rule.getStartTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        if (this.rule.getAgent() == AgentLevel.NORMAL) {
            return "尊敬的用户，新的邀请奖励规则将于"
                    + time
                    + "启用，届时邀请奖励金币调整为'"
                    + this.rule.getReward()
                    + "金币'。"
                    + "感谢您对本应用的大力支持🎁🎁🎁";
        }
        int intRatio = Double.valueOf(this.rule.getRatio() * 100).intValue();
        return "尊敬的用户，新的收益规则将于"
                + time
                + "启用，届时收益分成比例调整为'"
                + intRatio
                + "%'。"
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
                      .link("/invite")//app跳转链接
                      .linkText("我的邀请")
                      .objId(this.rule.getId().toString())
                      .objType("agent_rule")
                      .action(action)//消息动作
                      .publish("cloud-lottery-user-topic", "message-center");
    }

}
