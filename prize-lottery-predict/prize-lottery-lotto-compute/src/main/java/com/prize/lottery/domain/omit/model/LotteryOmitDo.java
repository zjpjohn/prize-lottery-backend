package com.prize.lottery.domain.omit.model;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.value.Omit;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LotteryOmitDo extends BaseOmitDo {

    private Long   id;
    private String period;
    private Omit   red;
    private Omit   blue;
    private Omit   cb1;
    private Omit   cb2;
    private Omit   cb3;
    private Omit   cb4;
    private Omit   cb5;
    private Omit   extra;


    public LottoP5OmitDo toP5Omit() {
        LotteryEnum type = this.getType();
        Assert.state(type == LotteryEnum.PL5, ResponseHandler.LOTTO_TYPE_ERROR);
        LottoP5OmitDo omit = new LottoP5OmitDo();
        omit.setType(type);
        omit.setPeriod(period);
        omit.setRed(red);
        omit.setCb1(cb1);
        omit.setCb2(cb2);
        omit.setCb3(cb3);
        omit.setCb4(cb4);
        omit.setCb5(cb5);
        return omit;
    }

    public LottoN3OmitDo toN3Omit() {
        LotteryEnum type = this.getType();
        Assert.state(type == LotteryEnum.FC3D || type == LotteryEnum.PL3, ResponseHandler.LOTTO_TYPE_ERROR);
        LottoN3OmitDo omit = new LottoN3OmitDo();
        omit.setType(type);
        omit.setPeriod(period);
        omit.setRed(red);
        omit.setCb1(cb1);
        omit.setCb2(cb2);
        omit.setCb3(cb3);
        omit.setExtra(extra);
        return omit;
    }

    public LottoSsqOmitDo toSsqOmit() {
        LotteryEnum type = this.getType();
        Assert.state(type == LotteryEnum.SSQ, ResponseHandler.LOTTO_TYPE_ERROR);
        LottoSsqOmitDo omit = new LottoSsqOmitDo();
        omit.setType(type);
        omit.setPeriod(period);
        omit.setRed(red);
        omit.setBlue(blue);
        return omit;
    }

    public LottoDltOmitDo toDltOmit() {
        LotteryEnum type = this.getType();
        Assert.state(type == LotteryEnum.DLT, ResponseHandler.LOTTO_TYPE_ERROR);
        LottoDltOmitDo omit = new LottoDltOmitDo();
        omit.setType(type);
        omit.setPeriod(period);
        omit.setRed(red);
        omit.setBlue(blue);
        return omit;
    }

    public LottoQlcOmitDo toQlcOmit() {
        LotteryEnum type = this.getType();
        Assert.state(type == LotteryEnum.QLC, ResponseHandler.LOTTO_TYPE_ERROR);
        LottoQlcOmitDo omit = new LottoQlcOmitDo();
        omit.setType(type);
        omit.setPeriod(period);
        omit.setRed(red);
        return omit;
    }

    @Override
    public LotteryOmitDo toOmit() {
        return this;
    }

}
