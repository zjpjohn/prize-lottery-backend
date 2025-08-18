package com.prize.lottery.application.vo;

import com.prize.lottery.enums.LotteryEnum;
import lombok.Data;

import java.util.List;

@Data
public class MasterInfoDetailVo {

    //专家唯一标识
    private String                masterId;
    //专家头像
    private String                name;
    //专家地址
    private String                address     = "";
    //专家头像
    private String                avatar;
    //用户是否已订阅
    private Integer               focused  = 0;
    //浏览量
    private Integer               browse;
    //搜索量
    private Integer               searches;
    //订阅量
    private Integer               subscribe;
    //开启预测渠道集合
    private Byte                  enable;
    //专家来源
    private Integer               source;
    //专家描述信息
    private String                description = "";
    //专家分彩种条目信息
    private List<MasterLotteryVo> lotteries;

    @Data
    public static class MasterLotteryVo {

        private LotteryEnum lottery;
        private Integer     level;
        private String      latest;

    }
}
