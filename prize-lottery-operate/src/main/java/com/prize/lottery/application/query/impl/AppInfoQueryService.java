package com.prize.lottery.application.query.impl;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.assembler.AppInfoAssembler;
import com.prize.lottery.application.query.IAppInfoQueryService;
import com.prize.lottery.application.query.dto.AppVersionQuery;
import com.prize.lottery.application.query.dto.ContactListQuery;
import com.prize.lottery.application.query.executor.Html5AppQueryExecutor;
import com.prize.lottery.application.query.executor.Html5CommentQueryExecutor;
import com.prize.lottery.application.query.executor.MobileAppQueryExecutor;
import com.prize.lottery.application.vo.AppCommentVo;
import com.prize.lottery.application.vo.AppHtml5ShareVo;
import com.prize.lottery.application.vo.AppInfoHtml5Vo;
import com.prize.lottery.application.vo.AppInfoMobileVo;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.mapper.AppInfoMapper;
import com.prize.lottery.infrast.persist.po.AppContactPo;
import com.prize.lottery.infrast.persist.po.AppInfoPo;
import com.prize.lottery.infrast.persist.po.AppVersionPo;
import com.prize.lottery.infrast.persist.vo.AppVersionVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppInfoQueryService implements IAppInfoQueryService {

    private final AppInfoMapper             appInfoMapper;
    private final AppInfoAssembler          appInfoAssembler;
    private final MobileAppQueryExecutor    mobileAppQueryExecutor;
    private final Html5AppQueryExecutor     html5AppQueryExecutor;
    private final Html5CommentQueryExecutor commentsQueryExecutor;

    @Override
    public List<AppInfoPo> getAppInfoList() {
        return appInfoMapper.getAllAppInfoList();
    }

    @Override
    public AppInfoPo getAppInfo(Long appId) {
        return appInfoMapper.getAppInfoById(appId);
    }

    @Override
    public AppInfoPo getAppInfo(String appNo) {
        return appInfoMapper.getAppInfo(appNo);
    }

    @Override
    public Page<AppVersionVo> getAppVersionList(AppVersionQuery query) {
        AppInfoPo appInfo = appInfoMapper.getAppInfo(query.getAppNo());
        Assert.notNull(appInfo, ResponseHandler.APP_NOT_EXIST);
        return query.from()
                    .count(appInfoMapper::countAppVersions)
                    .query(appInfoMapper::getAppVersionList)
                    .map(version -> appInfoAssembler.toVo(version, appInfo.getName(), appInfo.getLogo()));
    }

    @Override
    public AppHtml5ShareVo getHtml5AppInfo(String seqNo) {
        AppInfoHtml5Vo appInfo = html5AppQueryExecutor.execute(seqNo);
        Assert.notNull(appInfo, ResponseHandler.APP_NOT_EXIST);
        //应用评论信息
        List<AppCommentVo> comments = commentsQueryExecutor.execute(appInfo.getAppInfo().getSeqNo());
        return new AppHtml5ShareVo(appInfo, comments);
    }

    @Override
    public AppInfoMobileVo getMobileAppInfo(String appNo, String version) {
        return mobileAppQueryExecutor.execute(appNo, version);
    }

    @Override
    public AppVersionPo getAppVersionDetail(Long id) {
        return appInfoMapper.getAppVersionById(id, true);
    }

    @Override
    public String getAppDownloadUrl(String appNo) {
        String downloadUri = null;
        try {
            downloadUri = appInfoMapper.getAppAvailableVersion(appNo).map(AppVersionPo::v8aDownloadUrl).orElse("");
        } catch (Exception error) {
            log.error(error.getMessage(), error);
        }
        if (StringUtils.isBlank(downloadUri)) {
            downloadUri = "https://cdn.icaiwa.com/app/app-" + appNo + "/-release.apk";
        }
        return downloadUri;
    }

    @Override
    public Page<AppContactPo> appContactList(ContactListQuery query) {
        return query.from().count(appInfoMapper::countAppContacts).query(appInfoMapper::getAppContactList);
    }

    @Override
    public List<AppContactPo> usingContacts(String appNo) {
        return appInfoMapper.getUsingAppContacts(appNo);
    }

    @Override
    public AppContactPo appContact(Long id) {
        return appInfoMapper.getAppContact(id).orElseThrow(ResponseHandler.CONTACT_NOT_EXIST);
    }

}
