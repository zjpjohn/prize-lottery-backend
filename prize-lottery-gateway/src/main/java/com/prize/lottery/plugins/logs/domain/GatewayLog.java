package com.prize.lottery.plugins.logs.domain;

import lombok.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GatewayLog implements Serializable {

    private static final long serialVersionUID = -9129944465507308126L;

    /**
     * 访问实例
     */
    private String server;
    /**
     * 请求路径
     */
    private String path;
    /**
     * 请求方法
     */
    private String method;
    /**
     * 协议
     */
    private String schema;
    /**
     * 请求ip
     */
    private String ip;

    /**
     * 请求状态
     */
    private Integer status;

    /**
     * 请求mediaType
     */
    private MediaType mediaType;

    /**
     * 请求header信息
     */
    private HttpHeaders headers;
    /**
     * 请求参数
     */
    private String      query;

    /**
     * 请求body内容
     */
    private String body;

    /**
     * 请求时间
     */
    private Date reqTime;
    /**
     * 响应时间
     */
    private Date resTime;
    /**
     * 执行时间
     */
    private long execTime;

}
