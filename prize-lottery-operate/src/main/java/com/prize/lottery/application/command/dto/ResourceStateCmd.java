package com.prize.lottery.application.command.dto;

import com.cloud.arch.enums.Value;
import com.prize.lottery.application.command.IResourceCommandService;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class ResourceStateCmd {

    @NotNull(message = "唯一标识为空")
    private Long        id;
    @NotNull(message = "动作标识为空")
    private StateAction action;

    public enum StateAction implements Value<Integer> {
        USE("使用资源", 1) {
            @Override
            public void action(IResourceCommandService executor, ResourceStateCmd cmd) {
                executor.useAppResource(cmd.id);
            }
        },
        UN_USE("下架资源", 2) {
            @Override
            public void action(IResourceCommandService executor, ResourceStateCmd cmd) {
                executor.unUseResource(cmd.id);
            }
        },
        REMOVE("删除资源", 3) {
            @Override
            public void action(IResourceCommandService executor, ResourceStateCmd cmd) {
                executor.removeResource(cmd.id);
            }
        },
        ROLLBACK("回滚资源", 4) {
            @Override
            public void action(IResourceCommandService executor, ResourceStateCmd cmd) {
                executor.rollbackResource(cmd.getId());
            }
        };

        private final String  name;
        private final Integer value;

        StateAction(String name, Integer value) {
            this.name  = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public Integer getValue() {
            return value;
        }

        /**
         * 获取枚举变量唯一值
         */
        @Override
        public Integer value() {
            return value;
        }

        /**
         * 枚举值描述
         */
        @Override
        public String label() {
            return this.name;
        }

        public abstract void action(IResourceCommandService executor, ResourceStateCmd cmd);
    }
}
