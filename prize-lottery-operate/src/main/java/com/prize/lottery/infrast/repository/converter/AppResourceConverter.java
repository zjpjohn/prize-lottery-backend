package com.prize.lottery.infrast.repository.converter;

import com.prize.lottery.domain.app.model.AppResourceDo;
import com.prize.lottery.domain.app.valobj.AppResourceVal;
import com.prize.lottery.infrast.persist.enums.ResourceState;
import com.prize.lottery.infrast.persist.po.AppResourcePo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppResourceConverter {

    AppResourcePo toPo(AppResourceDo appResource, AppResourceVal resourceVal);

    AppResourcePo toPo(AppResourceVal resource);

    List<AppResourcePo> toList(List<AppResourceVal> resources);

    AppResourceVal toVal(AppResourcePo resource);

    @Mapping(source = "state", target = "state")
    AppResourceDo toDo(Long id, ResourceState state, String lastUri, AppResourceVal resource);

}
