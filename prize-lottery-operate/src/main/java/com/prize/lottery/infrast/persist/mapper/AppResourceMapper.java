package com.prize.lottery.infrast.persist.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.infrast.persist.po.AppResourcePo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AppResourceMapper {

    int addResourceList(List<AppResourcePo> resources);

    int addAppResource(AppResourcePo resource);

    int editAppResource(AppResourcePo resource);

    AppResourcePo getAppResourceById(@Param("id") Long id, @Param("detail") Boolean detail);

    int existResourceFeNo(@Param("appNo") String appNo, @Param("feNo") String feNo);

    List<AppResourcePo> getAppResourceList(PageCondition condition);

    int countAppResources(PageCondition condition);

    List<AppResourcePo> getUsingAppResources(String appNo);

}
