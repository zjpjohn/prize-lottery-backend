package com.prize.lottery.domain.admin.model;

import com.cloud.arch.aggregate.AggregateRoot;
import com.cloud.arch.encrypt.AESKit;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.AdminLevel;
import com.prize.lottery.infrast.persist.enums.AdminState;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Administrator implements AggregateRoot<Long> {

    public static final String DEFAULT_KEY = "a0i7n1r9yf2ge6c#";
    public static final String DEFAULT_IV  = "cloud.lotto.icai";

    private Long       id;
    private String     name;
    private String     password;
    private String     phone;
    private String     tokenId;
    private AdminLevel level;
    private AdminState state;

    public Administrator(String name, String password, String phone, AdminLevel level) {
        this.name     = name;
        this.phone    = phone;
        this.level    = level;
        this.state    = AdminState.NORMAL;
        this.password = encodePwd(password);
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }

    public void authCheck(String password) {
        Assert.state(this.state == AdminState.NORMAL, ResponseHandler.ADMIN_ACCT_INVALID);
        boolean checked = encodePwd(password).equals(this.password);
        Assert.state(checked, ResponseHandler.AUTH_PASSWORD_ERROR);
    }

    public void resetPassword(String password) {
        String encode = encodePwd(password);
        Assert.state(!encode.equals(this.password), ResponseHandler.RESET_PWD_NOT_SAME);
        this.password = encode;
    }

    public void frozen() {
        Assert.state(this.state == AdminState.NORMAL, ResponseHandler.ADMIN_STATE_ILLEGAL);
        this.state = AdminState.LOCKED;
    }

    public void unfrozen() {
        Assert.state(this.state == AdminState.LOCKED, ResponseHandler.ADMIN_STATE_ILLEGAL);
        this.state = AdminState.NORMAL;
    }

    public void invalid() {
        Assert.state(this.state != AdminState.INVALID, ResponseHandler.ADMIN_STATE_ILLEGAL);
        this.state = AdminState.INVALID;
    }

    public static String encodePwd(String password) {
        return AESKit.CBC.pkc7Enc(password, DEFAULT_KEY, DEFAULT_IV);
    }

    public static String decodePwd(String password) {
        return AESKit.CBC.pkc7Dec(password, DEFAULT_KEY, DEFAULT_IV);
    }
}
