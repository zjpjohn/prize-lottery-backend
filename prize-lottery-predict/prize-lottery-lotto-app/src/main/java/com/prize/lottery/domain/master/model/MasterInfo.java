package com.prize.lottery.domain.master.model;

import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Lists;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MasterInfo {

    private Long                id;
    private String              seq;
    private String              name;
    private String              phone;
    private String              address;
    private String              avatar;
    private String              sourceId;
    private String              description;
    private Integer             browse;
    private Integer             subscribe;
    private Integer             searches;
    private Integer             source;
    private Integer             state;
    private Byte                enable;
    private List<MasterLottery> lotteries;

    public MasterInfo(Long id) {
        this.id = id;
    }

    /**
     * 开启专家指定预测频道
     *
     * @param lottery 预测渠道
     */
    public MasterInfo enableLotto(LotteryEnum lottery) {
        //判断是否已开启预测频道
        Assert.state((lottery.getChannel() & this.enable) != 0, ResponseHandler.MASTER_HAS_ENABLE);

        MasterInfo masterInfo = new MasterInfo(this.id);
        //计算开启频道值
        byte enable = (byte) (this.enable | lottery.getChannel());
        masterInfo.setEnable(enable);

        //开启频道专家记录
        List<MasterLottery> lotteries     = Lists.newArrayList();
        MasterLottery       masterLottery = MasterLottery.create(lottery, this.seq, this.seq, this.source);
        lotteries.add(masterLottery);

        masterInfo.setLotteries(lotteries);
        return masterInfo;
    }

}
