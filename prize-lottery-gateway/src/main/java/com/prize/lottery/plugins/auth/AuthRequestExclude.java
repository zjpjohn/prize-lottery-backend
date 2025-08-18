package com.prize.lottery.plugins.auth;

import com.cloud.arch.web.IAuthRequestExclude;
import com.prize.lottery.plugins.access.service.IRequestAccessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthRequestExclude implements IAuthRequestExclude {

    private final IRequestAccessService requestAccessService;

    @Override
    public boolean isExclude(String uri, String method) {
        return requestAccessService.whiteAccess(uri, method);
    }

}
