package com.prize.lottery.application.query.executor;

import com.cloud.arch.cache.annotations.CacheResult;
import com.cloud.arch.cache.annotations.Local;
import com.cloud.arch.cache.annotations.Remote;
import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Maps;
import com.prize.lottery.application.assembler.AppInfoAssembler;
import com.prize.lottery.application.vo.AppInfoMobileVo;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.VersionState;
import com.prize.lottery.infrast.persist.mapper.AppInfoMapper;
import com.prize.lottery.infrast.persist.po.AppInfoPo;
import com.prize.lottery.infrast.persist.po.AppVersionPo;
import com.prize.lottery.infrast.repository.impl.AppInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class MobileAppQueryExecutor {

    public static final String ARM_V7A_KEY = "armeabi-v7a";
    public static final String ARM_V8A_KEY = "arm64-v8a";

    private final AppInfoMapper    appInfoMapper;
    private final AppInfoAssembler appInfoAssembler;

    @CacheResult(
            names = AppInfoRepository.MOBILE_APP_CACHE_NAME,
            key = "#appNo+':'+#version",
            enableLocal = true,
            remote = @Remote(expire = 12 * 3600L, randomBound = 3600),
            local = @Local(expire = 8 * 3600L, initialSize = 16, maximumSize = 32))
    public AppInfoMobileVo execute(String appNo, String version) {
        // 应用信息
        AppInfoPo appInfo = Assert.notNull(appInfoMapper.getAppInfo(appNo), ResponseHandler.APP_NOT_EXIST);

        // 应用版本信息
        AppVersionPo appVersion = appInfoMapper.getAppVersionByUk(appInfo.getSeqNo(), version);
        Assert.notNull(appVersion, ResponseHandler.APP_VERSION_NOT_EXIST);

        // 应用主推版本
        AppVersionPo mainVersion = appVersion;
        if (appVersion.getState() != VersionState.MAINTAIN) {
            mainVersion = appInfoMapper.getMainAppVersion(appInfo.getSeqNo());
        }
        Assert.notNull(mainVersion, ResponseHandler.APP_MAIN_VERSION_NONE);

        AppInfoMobileVo.AppInfo toApp = appInfoAssembler.toApp(appInfo);
        AppInfoMobileVo.AppVersion toVersion = appInfoAssembler.toVersion(appVersion, appVersion.getState()
                == VersionState.OFF_LINE);
        AppInfoMobileVo.MainVersion toMainVersion = appInfoAssembler.toMainVersion(mainVersion);

        // 全量版本apk地址
        toMainVersion.setApkUri(mainVersion.unityDownloadUrl());
        // abi分包apk地址
        Map<String, String> abiApks = Maps.newHashMap();
        abiApks.put(ARM_V7A_KEY, mainVersion.v7aDownloadUrl());
        abiApks.put(ARM_V8A_KEY, mainVersion.v8aDownloadUrl());
        toMainVersion.setAbiApks(abiApks);

        return new AppInfoMobileVo(toApp, toVersion, toMainVersion);
    }
}
