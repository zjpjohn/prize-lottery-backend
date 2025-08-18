package com.prize.lottery.domain.user.model;

import com.cloud.arch.aggregate.AggregateRoot;
import com.cloud.arch.encrypt.AESKit;
import com.cloud.arch.utils.IdWorker;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.PayChannel;
import com.prize.lottery.infrast.persist.enums.RegisterChannel;
import com.prize.lottery.infrast.persist.enums.UserState;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserInfo implements AggregateRoot<Long> {

    private static final long   serialVersionUID = 3084873084137510518L;
    public static final  String AES_KEY          = "a0i7n1r9yf2ge6c#";
    public static final  String AES_IV           = "cloud.lotto.icai";

    private Long            id;
    private String          nickname;
    private String          phone;
    private String          password;
    private String          wxId;
    private String          aliId;
    private String          email;
    private String          avatar;
    private Integer         expert;
    private UserState       state;
    private RegisterChannel channel;
    private Integer         version;
    private LocalDateTime   gmtCreate;

    public UserInfo(String phone, RegisterChannel channel, Integer expert) {
        this.id      = IdWorker.nextId();
        this.phone   = phone;
        this.channel = channel;
        this.expert  = expert;
        this.state   = UserState.NORMAL;
        this.version = 0;
    }

    /**
     * 判断用户是否为新用户
     */
    public boolean isNewUser() {
        return this.gmtCreate.isBefore(LocalDateTime.now().minusDays(7));
    }

    public void enableExpert() {
        this.expert = 1;
    }

    public void resetPwd(String password) {
        String encode = AESKit.CBC.pkc7Enc(password, AES_KEY, AES_IV);
        Assert.state(!encode.equals(this.password), ResponseHandler.PASSWORD_HAS_SAME);
        this.password = encode;
    }

    public void locked() {
        this.state = UserState.LOCKED;
    }

    public boolean isEnable() {
        return this.state == UserState.NORMAL;
    }

    public Pair<Boolean, String> canWithdraw(PayChannel channel) {
        switch (channel) {
            case ALI_PAY:
                return Pair.of(StringUtils.isNotBlank(this.aliId), this.aliId);
            case WX_PAY:
                return Pair.of(StringUtils.isNotBlank(this.wxId), this.wxId);
            case SYS_PAY:
                return Pair.of(true, "");
            default:
                return Pair.of(false, null);
        }
    }

    public boolean isAliBind() {
        return StringUtils.isNotBlank(this.aliId);
    }

    public boolean isWxBind() {
        return StringUtils.isNotBlank(this.wxId);
    }

    /**
     * 账户登录密码校验
     */
    public boolean pwdValidate(String password) {
        return AESKit.CBC.pkc7Enc(password, AES_KEY, AES_IV).equals(this.password);
    }

}
