package com.prize.lottery.application.vo;

import com.google.common.collect.Lists;
import com.prize.lottery.vo.BrowseRecordVo;
import lombok.Data;

import java.util.List;

@Data
public class RecentBrowseRecordVo {

    //本周浏览总次数
    private Integer              count;
    //最近浏览记录，最多8条
    private List<BrowseRecordVo> records = Lists.newArrayList();

}
