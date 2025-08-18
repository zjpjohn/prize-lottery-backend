package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageCondition;
import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.infrast.persist.enums.MemberState;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserMemberQuery extends PageQuery {

    @Pattern(regexp = "^(1([38][0-9]|4[579]|5[0-3,5-9]|66|7[0135678]|9[89])\\d{8})$", message = "手机格式错误")
    private String      phone;
    private Long        userId;
    private MemberState state;
    @Enumerable(ranges = {"1", "2", "3", "7", "14"}, message = "允许查询的天数错误")
    private Integer     expireDays;
    @Enumerable(ranges = {"1", "2", "3", "7", "14"}, message = "允许查询的天数错误")
    private Integer     renewDays;

    @Override
    public PageCondition from() {
        PageCondition condition = super.from();
        LocalDateTime endTime   = LocalDateTime.now();
        if (expireDays != null) {
            LocalDateTime expireStart = endTime.minusDays(expireDays).with(LocalTime.MIN);
            condition.setParam("expireStart", expireStart);
            condition.setParam("expireEnd", endTime);
        }
        if (this.renewDays != null) {
            LocalDateTime renewStart = endTime.minusDays(renewDays).with(LocalTime.MIN);
            condition.setParam("renewStart", renewStart);
            condition.setParam("renewEnd", endTime);
        }
        return condition;
    }
}
