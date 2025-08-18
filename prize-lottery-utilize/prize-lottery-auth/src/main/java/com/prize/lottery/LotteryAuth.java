package com.prize.lottery;

import java.util.Collections;
import java.util.List;

public abstract class LotteryAuth {

    private LotteryAuth() {
        throw new UnsupportedOperationException("not support invoke.");
    }

    /**
     * 管理端渠道值
     */
    public static final String       MANAGER                = "manager";
    /**
     * 用户端渠道值
     */
    public static final String       USER                   = "user";
    /**
     * 站点端渠道值
     */
    public static final String       STATION                = "station";
    /**
     * 销售端渠道值
     */
    public static final String       SALES                  = "salesman";
    /**
     * 代理商端渠道值
     */
    public static final String       AGENT                  = "agent";
    /**
     * 渠道名称
     */
    public static final String       CHANNEL_KEY            = "channel";
    /**
     * 角色字段标识
     */
    public static final String       ROLE_KEY               = "role";
    /**
     * 代理商标识字段名称
     */
    public static final String       AGENT_ID               = "agentId";
    /**
     * 用户标识字段名称
     */
    public static final String       USER_ID                = "userId";
    /**
     * 销售人员标识字段名称
     */
    public static final String       SALESMAN_ID            = "salesmanId";
    /**
     * 管理员标识字段名称
     */
    public static final String       MANAGER_ID             = "managerId";
    /**
     * 站点标识字段名称
     */
    public static final String       STATION_ID             = "stationId";
    /**
     * 生成token对应id，标识本次token标识
     */
    public static final String       TOKEN_ID               = "tokenId";
    /**
     * 黑名单缓存名称
     */
    public static final String       REDIS_BLACK_LIST_CACHE = "auth:black:list";
    /**
     * 黑名单缓存标志值
     */
    public static final Integer      TOKEN_INDEX_VALUE      = 1;
    /**
     * 请求参数保留字段
     */
    public static final List<String> USER_RETAINS           = Collections.singletonList(USER_ID);
    /**
     * 管理端请求参数保留字段
     */
    public static final List<String> ADMIN_RETAINS          = Collections.singletonList(MANAGER_ID);
    /**
     * 代理商端请求参数保留字段
     */
    public static final List<String> AGENT_RETAINS          = Collections.singletonList(AGENT_ID);

}
