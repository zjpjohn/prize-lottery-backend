package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageCondition;
import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.infrast.persist.enums.RegisterChannel;
import com.prize.lottery.infrast.persist.enums.UserState;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserInfoQuery extends PageQuery {
    private static final long serialVersionUID = -674600874945856737L;

    /**
     * 手机号
     */
    private String  phone;
    /**
     * 是否为专家
     */
    @Enumerable(ranges = {"0", "1"}, message = "可选值为0或1")
    private Integer expert;
    /**
     * 来源渠道
     */
    @Enumerable(enums = RegisterChannel.class, message = "来源渠道错误")
    private Integer channel;
    /**
     * 用户状态
     */
    @Enumerable(enums = UserState.class, message = "用户状态值错误.")
    private Integer state;
    /**
     * 用户注册时间天内
     */
    @Enumerable(ranges = {"7", "14", "21", "28"}, message = "允许查询的天数错误")
    private Integer day;

    @Override
    public PageCondition from() {
        PageCondition condition = super.from();
        if (this.day != null) {
            LocalDateTime endTime   = LocalDateTime.now();
            LocalDateTime startTime = endTime.minusDays(this.day).with(LocalTime.MIN);
            condition.setParam("startTime", startTime);
            condition.setParam("endTime", endTime);
        }
        return condition;
    }
}
