package com.prize.lottery.event;


import com.google.common.collect.Maps;
import com.prize.lottery.utils.MessageConstants;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;

@Data
@ToString
public class MessageEvent implements Serializable {

    private static final long serialVersionUID = 5087608521104750878L;

    //用户标识
    private Long                userId;
    //消息标题
    private String              title;
    //消息内容
    private Map<String, String> content  = Maps.newHashMap();
    //消息所属渠道
    private String              channel;
    //消息来源类型:0-公告;1-提醒
    private Integer             source;
    //消息类型
    private Integer             type;
    //消息操作动作
    private String              action   = "";
    //消息对象标识
    private String              objId    = "";
    //消息对象业务类型
    private String              objType  = "";
    //操作者标识:系统人员默认-"000"
    private String              fromId   = MessageConstants.DEFAULT_FROM_ID;
    //来源这名称:系统人员默认-"系统助手"
    private String              fromName = MessageConstants.DEFAULT_FROM_NAME;

    /**
     * 设置消息内容
     *
     * @param key   内容key
     * @param value 内容值
     */
    public void put(String key, String value) {
        this.content.put(key, value);
    }

    /**
     * 查询指定内容
     *
     * @param key 内容key
     */
    public String get(String key) {
        return this.content.get(key);
    }

}
