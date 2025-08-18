package com.prize.lottery.builder;


import com.cloud.arch.event.core.publish.PublishGenericEvent;
import com.cloud.arch.event.publisher.DomainEventPublisher;
import com.prize.lottery.event.MessageEvent;
import com.prize.lottery.event.MessageType;
import com.prize.lottery.utils.MessageConstants;
import com.prize.lottery.validator.Validators;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

@Getter
public class MessageBuilder {

    private final MessageEvent event;
    private final MessageType  type;

    protected MessageBuilder(MessageType type, Integer source) {
        this.event = new MessageEvent();
        this.event.setType(type.getType());
        this.type = type;
        this.event.setSource(source);
    }

    /**
     * 提醒消息
     *
     * @param type 消息类型
     */
    public static MessageBuilder remind(MessageType type) {
        return new MessageBuilder(type, 1);
    }

    /**
     * 公告消息
     *
     * @param type 消息类型
     */
    public static MessageBuilder announce(MessageType type) {
        return new MessageBuilder(type, 0);
    }

    /**
     * 消息标题-必填
     *
     * @param title 消息标题
     */
    public MessageBuilder title(String title) {
        this.event.setTitle(title);
        return this;
    }

    /**
     * 消息渠道-必填
     *
     * @param channel 消息渠道
     */
    public MessageBuilder channel(String channel) {
        this.event.setChannel(channel);
        return this;
    }

    /**
     * 用户标识-提醒消息必填
     *
     * @param userId 提醒用户标识
     */
    public MessageBuilder userId(Long userId) {
        this.event.setUserId(userId);
        return this;
    }

    /**
     * 消息发布者标识-选填
     *
     * @param fromId 发布者标识
     */
    public MessageBuilder fromId(String fromId) {
        if (StringUtils.isNotBlank(fromId)) {
            this.event.setFromId(fromId);
        }
        return this;
    }

    /**
     * 消息发布者名称-选填
     *
     * @param fromName 发布者名称
     */
    public MessageBuilder fromName(String fromName) {
        if (StringUtils.isNotBlank(fromName)) {
            this.event.setFromName(fromName);
        }
        return this;
    }

    /**
     * 消息内容-必填
     *
     * @param content 消息文本内容
     */
    public MessageBuilder content(String content) {
        this.event.put(MessageConstants.KEY_CONTENT_TEXT, content);
        return this;
    }

    /**
     * 消息链接-link或card类型消息必填
     *
     * @param link 消息链接
     */
    public MessageBuilder link(String link) {
        if (type != MessageType.TEXT) {
            this.event.put(MessageConstants.KEY_CONTENT_LINK, link);
        }
        return this;
    }

    /**
     * 链接文字信息-选填
     * 默认值-查看详情
     *
     * @param linkText 链接文字
     */
    public MessageBuilder linkText(String linkText) {
        if (type != MessageType.TEXT) {
            this.event.put(MessageConstants.KEY_CONTENT_LINK_TEXT, linkText);
        }
        return this;
    }

    /**
     * card片背景图片-card类型消息必填
     *
     * @param cover 背景图片
     */
    public MessageBuilder cover(String cover) {
        if (type == MessageType.CARD) {
            this.event.put(MessageConstants.KEY_CONTENT_COVER, cover);
        }
        return this;
    }

    /**
     * 发送内容对象标识-选填
     * 默认为空
     *
     * @param objId 对象标识
     */
    public MessageBuilder objId(String objId) {
        this.event.setObjId(objId);
        return this;
    }

    /**
     * 发送对象类型-选填
     * 默认为空
     *
     * @param objType 对象类型
     */
    public MessageBuilder objType(String objType) {
        this.event.setObjType(objType);
        return this;
    }

    /**
     * 发送消息操作类型-选填
     * 默认空
     *
     * @param action 操作动作
     */
    public MessageBuilder action(String action) {
        this.event.setAction(action);
        return this;
    }

    /**
     * 设置连接默认值
     */
    private void setDefaultLinkText() {
        String linkText = this.event.get(MessageConstants.KEY_CONTENT_LINK_TEXT);
        if (StringUtils.isBlank(linkText)) {
            event.put(MessageConstants.KEY_CONTENT_LINK_TEXT, MessageConstants.DEFAULT_LINK_TEXT);
        }
    }

    /**
     * 发布消息到消息队列
     */
    public void publish(String topic, String filter) {
        //消息队列主题不允许为空
        Assert.state(StringUtils.isNotBlank(topic), "消息队列topic为空.");
        //站内信息消息校验
        if (Validators.isNotValid(this.getEvent(), this.getType())) {
            throw new IllegalArgumentException("消息完整性不合规，请根据业务确保消息完整性。");
        }
        //设置连接文本默认值
        if (this.type == MessageType.LINK || this.type == MessageType.CARD) {
            this.setDefaultLinkText();
        }
        //发送站内信消息
        PublishGenericEvent genericEvent = new PublishGenericEvent(this.event, topic, filter);
        DomainEventPublisher.publish(genericEvent);
    }

}
