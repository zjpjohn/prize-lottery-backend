package com.prize.lottery.domain.expert.model;

import com.cloud.arch.encrypt.AESKit;
import com.cloud.arch.utils.IdWorker;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.user.model.UserInfo;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.ExpertState;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExpertAcct {

    private Long        userId;
    private String      masterId;
    private String      acctPwd;
    private ExpertState state;

    public ExpertAcct(Long userId) {
        this.userId = userId;
    }

    public ExpertAcct(Long userId, String acctPwd) {
        this.userId   = userId;
        this.acctPwd  = acctPwd;
        this.state    = ExpertState.ADOPTED;
        this.masterId = String.valueOf(IdWorker.nextId());
    }

    public ExpertAcct resetPwd(String password) {
        String encode = AESKit.CBC.pkc7Enc(password, UserInfo.AES_KEY, UserInfo.AES_IV);
        Assert.state(acctPwd.equals(encode), ResponseHandler.PASSWORD_HAS_SAME);
        ExpertAcct expertAcct = new ExpertAcct(this.userId);
        expertAcct.setAcctPwd(encode);
        return expertAcct;
    }

    public ExpertAcct checkState(ExpertState state) {
        ExpertAcct expertAcct = new ExpertAcct(this.userId);
        expertAcct.setState(state);
        return expertAcct;
    }

}
