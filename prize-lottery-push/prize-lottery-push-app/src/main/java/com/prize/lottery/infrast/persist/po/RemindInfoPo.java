package com.prize.lottery.infrast.persist.po;

import com.prize.lottery.event.MessageType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class RemindInfoPo {

    private Long                id;
    private Long                receiverId;
    private String              fromId;
    private String              fromName;
    private String              objId;
    private String              objType;
    private String              objAction;
    private String              title;
    private Map<String, String> content;
    private MessageType         type;
    private String              channel;
    private LocalDateTime       gmtCreate;

}
