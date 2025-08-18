package com.prize.lottery.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.po.master.MasterGladPo;
import com.prize.lottery.vo.MasterGladVo;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MasterGladMapper {

    int addMasterGladList(List<MasterGladPo> glads);

    int removeBeforeTime(LocalDateTime time);

    MasterGladPo getMasterGlad(Long id);

    List<MasterGladVo> getMasterGladList(PageCondition condition);

    int countMasterGlads(PageCondition condition);
}
