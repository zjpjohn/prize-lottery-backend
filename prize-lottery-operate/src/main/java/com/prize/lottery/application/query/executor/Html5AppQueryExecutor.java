package com.prize.lottery.application.query.executor;

import com.cloud.arch.cache.annotations.CacheResult;
import com.cloud.arch.cache.annotations.Local;
import com.cloud.arch.cache.annotations.Remote;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.assembler.AppInfoAssembler;
import com.prize.lottery.application.vo.AppInfoHtml5Vo;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.mapper.AppInfoMapper;
import com.prize.lottery.infrast.persist.po.AppInfoPo;
import com.prize.lottery.infrast.persist.po.AppVersionPo;
import com.prize.lottery.infrast.repository.impl.AppInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Html5AppQueryExecutor {

    private final AppInfoMapper    appInfoMapper;
    private final AppInfoAssembler appInfoAssembler;

    @CacheResult(
            names = AppInfoRepository.H5_APP_CACHE_NAME,
            key = "#appNo",
            enableLocal = true,
            remote = @Remote(expire = 12 * 3600L, randomBound = 3600),
            local = @Local(expire = 8 * 3600L, initialSize = 8, maximumSize = 32))
    public AppInfoHtml5Vo execute(String appNo) {
        // 应用信息
        AppInfoPo appPo = Assert.notNull(appInfoMapper.getAppInfo(appNo), ResponseHandler.APP_NOT_EXIST);
        // 应用主推版本
        AppVersionPo versionPo = appInfoMapper.getMainAppVersion(appPo.getSeqNo());
        Assert.notNull(versionPo, ResponseHandler.APP_MAIN_VERSION_NONE);

        AppInfoHtml5Vo.AppInfo    appInfo = appInfoAssembler.toVo(appPo, versionPo.getDepiction());
        AppInfoHtml5Vo.AppVersion version = appInfoAssembler.toVo(versionPo);
        String                    apkUri  = versionPo.v8aDownloadUrl();
        version.setApkUri(apkUri);
        return new AppInfoHtml5Vo(appInfo, version);
    }

}
