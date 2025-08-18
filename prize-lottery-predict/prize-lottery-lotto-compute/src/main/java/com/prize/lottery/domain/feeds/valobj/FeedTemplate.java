package com.prize.lottery.domain.feeds.valobj;


import com.prize.lottery.enums.LotteryEnum;

public class FeedTemplate {

    public static String niuTitleFormat      = "真牛人%s预测命中率高达%d%%";
    public static String superTitleFormat    = "超级牛%s预测命中率高达%d%%";
    public static String hitContentFormat   = "第%s期%s%s预测%s";
    public static String modifyContentFormat = "第%s期%s推荐预测方案已更新";


    public static String niuTitle(String field, int rate) {
        return String.format(niuTitleFormat, field, rate);
    }

    public static String superTitle(String field, int rate) {
        return String.format(superTitleFormat, field, rate);
    }

    public static String hitContent(String period, LotteryEnum type, String field, String hitContent) {
        return String.format(hitContentFormat, period, type.getNameZh(), field, hitContent);
    }

    public static String modifyContent(String period, LotteryEnum type) {
        return String.format(modifyContentFormat, period, type.getNameZh());
    }

}
