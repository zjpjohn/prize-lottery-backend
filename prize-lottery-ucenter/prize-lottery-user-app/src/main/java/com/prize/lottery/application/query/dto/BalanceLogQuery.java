package com.prize.lottery.application.query.dto;

import com.cloud.arch.enums.Value;
import com.cloud.arch.page.PageCondition;
import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.infrast.persist.enums.ActionDirection;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class BalanceLogQuery extends PageQuery {

    private static final long serialVersionUID = 8589685638630089249L;

    @NotNull(message = "用户表示为空")
    private Long      userId;
    @Enumerable(enums = ActionDirection.class, message = "余额变动错误")
    private Integer   direct;
    @NotNull(message = "日志类型为空")
    private QueryEnum type;

    @Override
    public PageCondition from() {
        return type.query(super.from());
    }

    public enum QueryEnum implements Value<Integer> {

        //奖励金日志
        BALANCE_LOG(1) {
            @Override
            public PageCondition query(PageCondition condition) {
                return condition.setParam("balance", 1);
            }
        },
        //金币日志
        SURPLUS_LOG(2) {
            @Override
            public PageCondition query(PageCondition condition) {
                return condition.setParam("surplus", 1);
            }
        };

        private final Integer type;

        QueryEnum(Integer type) {
            this.type = type;
        }

        /**
         * 获取枚举变量唯一值
         */
        @Override
        public Integer value() {
            return this.type;
        }

        /**
         * 枚举值描述
         */
        @Override
        public String label() {
            return null;
        }

        public abstract PageCondition query(PageCondition query);
    }
}
