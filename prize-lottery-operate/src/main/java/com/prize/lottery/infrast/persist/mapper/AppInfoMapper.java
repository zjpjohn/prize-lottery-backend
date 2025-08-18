package com.prize.lottery.infrast.persist.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.infrast.persist.enums.ConfState;
import com.prize.lottery.infrast.persist.po.*;
import com.prize.lottery.infrast.persist.valobj.AssistantApp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface AppInfoMapper {

    int addAppInfo(AppInfoPo appInfo);

    int editAppInfo(AppInfoPo appInfo);

    AppInfoPo getAppInfoById(Long id);

    int hasExistAppName(String name);

    AppInfoPo getAppInfo(String seqNo);

    List<AppInfoPo> getAppListByNos(List<String> appNos);

    List<AppInfoPo> getAllAppInfoList();

    int addAppVersion(AppVersionPo appVersion);

    int editAppVersion(AppVersionPo appVersion);

    int issueAppMainVersion(@Param("appNo") String appNo, @Param("appVer") String appVer);

    AppVersionPo getAppVersionById(@Param("id") Long id, @Param("detail") Boolean detail);

    int hasExistAppVersion(@Param("appNo") String appNo, @Param("appVer") String appVer);

    AppVersionPo getAppVersionByUk(@Param("appNo") String appNo, @Param("appVer") String appVer);

    AppVersionPo getMainAppVersion(String appNo);

    Optional<AppVersionPo> getAppAvailableVersion(String appNo);

    AssistantApp getAssistantApp(String appNo);

    List<AppVersionPo> getAppVersionsByApp(String appNo);

    List<AppVersionPo> getAppVersionList(PageCondition condition);

    int countAppVersions(PageCondition condition);

    int addAppConf(AppConfPo appConf);

    int editAppConf(AppConfPo appConf);

    AppConfPo getAppConfById(Long id);

    List<AppConfPo> getAppConfListByApp(@Param("appNo") String appNo, @Param("state") ConfState state);

    List<AppConfPo> getAppConfList(PageCondition condition);

    int countAppConfs(PageCondition condition);

    int addAppComments(List<AppCommentPo> comments);

    int editAppComment(AppCommentPo comment);

    AppCommentPo getAppComment(Long id);

    List<AppCommentPo> getLatestAppComments(String appNo);

    List<AppCommentPo> getAppCommentList(PageCondition condition);

    int countAppComments(PageCondition condition);

    int addAppContact(AppContactPo contact);

    int editAppContact(AppContactPo contact);

    Optional<AppContactPo> getAppContact(Long id);

    int delAppContacts(String appNo);

    List<AppContactPo> getUsingAppContacts(String appNo);

    List<AppContactPo> getAppContactList(PageCondition condition);

    int countAppContacts(PageCondition condition);


}
