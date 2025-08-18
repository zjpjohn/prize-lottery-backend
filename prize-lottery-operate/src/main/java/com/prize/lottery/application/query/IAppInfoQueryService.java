package com.prize.lottery.application.query;


import com.cloud.arch.page.Page;
import com.prize.lottery.application.query.dto.AppVersionQuery;
import com.prize.lottery.application.query.dto.ContactListQuery;
import com.prize.lottery.application.vo.AppHtml5ShareVo;
import com.prize.lottery.application.vo.AppInfoMobileVo;
import com.prize.lottery.infrast.persist.po.AppContactPo;
import com.prize.lottery.infrast.persist.po.AppInfoPo;
import com.prize.lottery.infrast.persist.po.AppVersionPo;
import com.prize.lottery.infrast.persist.vo.AppVersionVo;

import java.util.List;

public interface IAppInfoQueryService {

    List<AppInfoPo> getAppInfoList();

    AppInfoPo getAppInfo(Long appId);

    AppInfoPo getAppInfo(String appNo);

    Page<AppVersionVo> getAppVersionList(AppVersionQuery query);

    AppHtml5ShareVo getHtml5AppInfo(String seqNo);

    AppInfoMobileVo getMobileAppInfo(String appNo, String version);

    AppVersionPo getAppVersionDetail(Long id);

    String getAppDownloadUrl(String appNo);

    Page<AppContactPo> appContactList(ContactListQuery query);

    List<AppContactPo> usingContacts(String appNo);

    AppContactPo appContact(Long id);

}
