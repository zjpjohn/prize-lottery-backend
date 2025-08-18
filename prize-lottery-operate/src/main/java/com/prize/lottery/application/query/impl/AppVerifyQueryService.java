package com.prize.lottery.application.query.impl;

import com.cloud.arch.cache.annotations.CacheResult;
import com.cloud.arch.cache.annotations.Local;
import com.cloud.arch.cache.annotations.Remote;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.assembler.AppInfoAssembler;
import com.prize.lottery.application.query.IAppVerifyQueryService;
import com.prize.lottery.application.vo.AppVerifyVo;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.CommonState;
import com.prize.lottery.infrast.persist.mapper.AppVerifyMapper;
import com.prize.lottery.infrast.persist.po.AppVerifyPo;
import com.prize.lottery.infrast.repository.impl.AppVerifyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppVerifyQueryService implements IAppVerifyQueryService {

    private final AppVerifyMapper  appVerifyMapper;
    private final AppInfoAssembler appInfoAssembler;

    @Override
    public AppVerifyPo getAppVerifyInfo(String appNo) {
        return appVerifyMapper.getVerifyByNo(appNo);
    }

    @Override
    @CacheResult(
            names = AppVerifyRepository.APP_VERIFY_CACHE_NAME,
            key = "#appNo",
            enableLocal = true,
            remote = @Remote(expire = 12 * 3600L, randomBound = 3600),
            local = @Local(expire = 8 * 3600L, initialSize = 16, maximumSize = 32))
    public AppVerifyVo releasedAppVerify(String appNo) {
        AppVerifyPo verify = appVerifyMapper.getVerifyByNo(appNo);
        //存在且必须为已上线
        boolean existed = verify != null && CommonState.USING.equals(verify.getState());
        Assert.state(existed, ResponseHandler.APP_VERIFY_NONE);
        //返回前端
        return appInfoAssembler.toVo(verify);
    }

}
