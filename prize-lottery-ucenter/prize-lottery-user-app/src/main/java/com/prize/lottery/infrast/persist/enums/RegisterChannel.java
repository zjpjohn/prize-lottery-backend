package com.prize.lottery.infrast.persist.enums;

import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
@TypeHandler(type = Type.ENUM)
public enum RegisterChannel implements Value<Integer> {
    STATE_INVITE(1, "彩票站"),
    DIRECT_DOWNLOAD(3, "直接下载"),
    USER_INVITE(2, "用户邀请") {
        @Override
        public boolean activatedInv() {
            return true;
        }
    },
    CHANNEL_INVITE(4, "渠道邀请") {
        @Override
        public boolean activatedInv() {
            return true;
        }
    },
    ;

    private final Integer channel;
    private final String  name;

    public boolean activatedInv() {
        return false;
    }

    /**
     * 获取枚举变量唯一值
     */
    @Override
    public Integer value() {
        return this.channel;
    }

    /**
     * 枚举值描述
     */
    @Override
    public String label() {
        return name;
    }

    public static RegisterChannel findOf(Integer channel) {
        return Arrays.stream(values())
                     .filter(v -> v.channel.equals(channel))
                     .findFirst()
                     .orElseThrow(ResponseHandler.REGISTER_CHANNEL_NONE);
    }

}
