package com.prize.lottery.vo;

import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.po.master.MasterInfoPo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class MasterInfoVo extends MasterInfoPo {

    //彩票类型
    private String            type;
    //专家级别
    private Integer           level;
    //专家最新预测期号
    private String            latest;
    //开通的彩种集合
    private List<LotteryEnum> lotteries;

}
