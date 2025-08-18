package com.prize.lottery.application.assembler;

import com.prize.lottery.application.command.dto.*;
import com.prize.lottery.application.vo.AppCommentVo;
import com.prize.lottery.application.vo.AppInfoHtml5Vo;
import com.prize.lottery.application.vo.AppInfoMobileVo;
import com.prize.lottery.application.vo.AppVerifyVo;
import com.prize.lottery.domain.app.model.*;
import com.prize.lottery.infrast.persist.po.AppCommentPo;
import com.prize.lottery.infrast.persist.po.AppInfoPo;
import com.prize.lottery.infrast.persist.po.AppVerifyPo;
import com.prize.lottery.infrast.persist.po.AppVersionPo;
import com.prize.lottery.infrast.persist.vo.AppVersionVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppInfoAssembler {

    void toDo(AppInfoCreateCmd command, @MappingTarget AppInfoDo appInfoDo);

    void toDo(AppInfoModifyCmd command, @MappingTarget AppInfoDo appInfoDo);

    void toDo(VersionCreateCmd command, @MappingTarget AppVersionDo version);

    void toDo(VersionModifyCmd command, @MappingTarget AppVersionDo version);

    void toDo(ConfCreateCmd command, @MappingTarget AppConfDo conf);

    void toDo(ConfModifyCmd command, @MappingTarget AppConfDo conf);

    void toDo(AppVerifyCreateCmd command, @MappingTarget AppVerifyDo verify);

    void toDo(AppVerifyModifyCmd command, @MappingTarget AppVerifyDo verify);

    AppVersionVo toVo(AppVersionPo version, String name, String logo);

    @Mapping(source = "remark", target = "remark")
    AppInfoHtml5Vo.AppInfo toVo(AppInfoPo appInfo, String remark);

    @Mapping(source = "appVer", target = "version")
    AppInfoHtml5Vo.AppVersion toVo(AppVersionPo version);

    AppInfoMobileVo.AppInfo toApp(AppInfoPo appInfo);

    @Mapping(source = "version.appVer", target = "version")
    AppInfoMobileVo.AppVersion toVersion(AppVersionPo version, boolean offline);

    @Mapping(source = "appVer", target = "version")
    AppInfoMobileVo.MainVersion toMainVersion(AppVersionPo version);

    AppVerifyVo toVo(AppVerifyPo verify);

    void toDo(AppCommentCreateCmd command, @MappingTarget AppCommentDo comment);

    void toDo(AppCommentEditCmd command, @MappingTarget AppCommentDo comment);

    @Mapping(source = "cmtTime", target = "time")
    AppCommentVo toVo(AppCommentPo comment);

    List<AppCommentVo> toVoList(List<AppCommentPo> comments);

    void toDo(ContactCreateCmd command, @MappingTarget AppContactDo contact);

    void toDo(ContactEditCmd command, @MappingTarget AppContactDo contact);

}
