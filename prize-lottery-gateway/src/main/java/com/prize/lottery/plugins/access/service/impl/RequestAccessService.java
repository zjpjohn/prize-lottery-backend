package com.prize.lottery.plugins.access.service.impl;

import com.prize.lottery.plugins.access.repository.IReqAccessRepository;
import com.prize.lottery.plugins.access.service.IRequestAccessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class RequestAccessService implements IRequestAccessService {

    private final IReqAccessRepository reqAccessRepository;

    public RequestAccessService(IReqAccessRepository reqAccessRepository) {
        this.reqAccessRepository = reqAccessRepository;
    }

    /**
     * 请求白名单验证
     *
     * @param request 请求uri
     * @param method  请求方法
     */
    @Override
    public boolean whiteAccess(String request, String method) {
        return reqAccessRepository.getWhitePatterns().stream().anyMatch(pattern -> pattern.match(request, method));
    }

    /**
     * ip地址黑名单限制
     *
     * @param ipAddress ip地址
     */
    @Override
    public boolean blackLimit(String ipAddress) {
        return reqAccessRepository.getBlackList().stream().anyMatch(ipAddress::matches);
    }
}
