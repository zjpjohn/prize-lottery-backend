package com.prize.lottery.domain.feeds.extractor;


import com.cloud.arch.executor.Executor;
import com.prize.lottery.enums.FeedsType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.po.master.MasterFeedsPo;
import com.prize.lottery.vo.MasterFeedRateVo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FeedsExtractor extends Executor<LotteryEnum> {

    List<MasterFeedsPo> extract();

    MasterFeedsPo extract(String channel, String period, String masterId);

    LocalDateTime modifyTime(String masterId, String period);

    default MasterFeedsPo modify(String masterId, String period) {
        return Optional.ofNullable(this.modifyTime(masterId, period)).map(time -> {
            MasterFeedsPo feed = new MasterFeedsPo();
            //设置信息流更新
            feed.setRenew(1);
            //设置更新期号
            feed.setRenewPeriod(period);
            feed.setType(this.bizIndex());
            feed.setMasterId(masterId);
            feed.setGmtModify(time);
            return feed;
        }).orElse(null);
    }

    default MasterFeedsPo build(MasterFeedRateVo rate, FeedsType feedsType) {
        MasterFeedsPo feeds = new MasterFeedsPo();
        feeds.setRenew(0);
        feeds.setRenewPeriod("");
        feeds.setType(this.bizIndex());
        feeds.setFeedType(feedsType);
        feeds.setField(rate.getField());
        feeds.setFieldHit(rate.getHit());
        feeds.setPeriod(rate.getPeriod());
        feeds.setFieldRate(rate.getRate());
        feeds.setMasterId(rate.getMasterId());
        return feeds;
    }
}
