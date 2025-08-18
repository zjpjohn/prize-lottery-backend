package com.prize.lottery.application.vo;

import com.prize.lottery.infrast.persist.po.FeedbackTypePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FeedbackTypeVo extends FeedbackTypePo {

    private String appName;

}
