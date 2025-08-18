package com.prize.lottery.plugins.access.service;

public interface IRequestAccessService {

    /**
     * 请求白名单验证
     *
     * @param request 请求uri
     * @param method  请求方法
     */
    boolean whiteAccess(String request, String method);

    /**
     * ip地址黑名单限制
     *
     * @param ipAddress ip地址
     */
    boolean blackLimit(String ipAddress);
}
