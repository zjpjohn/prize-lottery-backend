package com.prize.lottery.domain.message.model;

import com.cloud.arch.aggregate.AggregateRoot;
import com.cloud.arch.web.utils.Assert;
import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import com.prize.lottery.application.command.dto.AnnounceCreateCmd;
import com.prize.lottery.application.command.dto.AnnounceModifyCmd;
import com.prize.lottery.event.MessageType;
import com.prize.lottery.infrast.error.ResponseErrorHandler;
import com.prize.lottery.utils.MessageConstants;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

@Data
@NoArgsConstructor
public class AnnounceInfoDo implements AggregateRoot<Long> {

    private static final long serialVersionUID = 7581278103168250724L;

    private Long                id;
    private String              title;
    private Map<String, String> content;
    private String              objId;
    private String              objType;
    private String              objAction;
    private MessageType         type;
    private String              channel;
    private Integer             mode;

    public AnnounceInfoDo(AnnounceCreateCmd command) {
        MessageType messageType = MessageType.of(command.getType());
        Assert.notNull(messageType, ResponseErrorHandler.MESSAGE_TYPE_NULL);
        this.title     = command.getTitle();
        this.channel   = command.getChannel();
        this.objId     = "";
        this.objType   = "";
        this.objAction = "";
        this.mode      = 2;
        this.type      = messageType;
        this.content   = Maps.newHashMap();
        this.content.put(MessageConstants.KEY_CONTENT_TEXT, command.getText());
        if (messageType == MessageType.LINK) {
            this.content.put(MessageConstants.KEY_CONTENT_LINK, command.getLink());
            this.content.put(MessageConstants.KEY_CONTENT_LINK_TEXT, MessageConstants.DEFAULT_LINK_TEXT);
        }
    }

    public void modify(AnnounceModifyCmd command) {
        Integer editForce = command.getForce();
        if (editForce == null || editForce == 0) {
            //非强制修改只允许修改手动添加的公告
            Assert.state(this.mode == 2, ResponseErrorHandler.ANNOUNCE_NOT_MODIFIED);
        }
        //修改消息标题
        if (StringUtils.isNotBlank(command.getTitle()) && !Objects.equal(command.getTitle(), this.title)) {
            this.title = command.getTitle();
        }
        //修改消息内容
        String text    = this.content.get(MessageConstants.KEY_CONTENT_TEXT);
        String newText = command.getText();
        if (StringUtils.isNotBlank(newText) && !Objects.equal(text, newText)) {
            this.content.put(MessageConstants.KEY_CONTENT_TEXT, newText);
        }
        //链接类型消息，允许修改链接
        if (this.type == MessageType.LINK) {
            String link    = this.content.get(MessageConstants.KEY_CONTENT_LINK);
            String newLink = command.getLink();
            if (StringUtils.isNotBlank(newLink) && !Objects.equal(link, newLink)) {
                this.content.put(MessageConstants.KEY_CONTENT_LINK, newLink);
            }
        }
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }

}
