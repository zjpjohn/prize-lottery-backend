package com.prize.lottery.infrast.persist.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.infrast.persist.po.AppAssistantPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AppAssistantMapper {

    int addAppAssistant(AppAssistantPo assistant);

    int editAppAssistant(AppAssistantPo assistant);

    int sortAppAssistant(@Param("id") Long id, @Param("sort") Integer sort, @Param("position") Integer position);

    AppAssistantPo getAppAssistant(Long id);

    Integer getAssistantMaxSort(String appNo);

    List<AppAssistantPo> getUsingAppAssistants(@Param("appNo") String appNo,
                                               @Param("version") String version,
                                               @Param("type") String type);

    List<AppAssistantPo> getAppAssistants(PageCondition condition);

    int countAppAssistants(PageCondition condition);

}
