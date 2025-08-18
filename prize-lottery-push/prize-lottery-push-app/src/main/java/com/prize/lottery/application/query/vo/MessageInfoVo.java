package com.prize.lottery.application.query.vo;

import com.prize.lottery.event.MessageType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class MessageInfoVo {

    private Long                id;
    private String              title;
    private Map<String, String> content;
    private MessageType         type;
    private String              channel;
    private String              objId;
    private String              objType;
    private String              objAction;
    private LocalDateTime       gmtCreate;

}
