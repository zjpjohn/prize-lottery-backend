package com.prize.lottery.infrast.repository.converter;

import com.prize.lottery.domain.app.model.*;
import com.prize.lottery.infrast.persist.po.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppInfoConverter {

    AppInfoPo toPo(AppInfoDo appInfoDo);

    AppInfoDo toDo(AppInfoPo appInfo);

    AppVersionDo toDo(AppVersionPo version);

    AppVersionPo toPo(AppVersionDo version);

    AppConfDo toDo(AppConfPo conf);

    AppConfPo toPo(AppConfDo conf);

    AppCommentDo toDo(AppCommentPo comment);

    AppCommentPo toPo(AppCommentDo comment);

    AppContactDo toDo(AppContactPo contact);

    AppContactPo toPo(AppContactDo contact);

}
