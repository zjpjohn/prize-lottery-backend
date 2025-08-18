package com.prize.lottery.domain.master.repository;


import com.prize.lottery.domain.master.model.MasterInfo;
import com.prize.lottery.domain.master.model.MasterLottery;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.value.MasterValue;

public interface IMasterInfoRepository {

    void saveMasterInfo(MasterInfo masterInfo);

    MasterInfo getMasterInfo(String masterId);

    MasterValue ofMaster(String masterId);

    MasterLottery getMasterLottery(LotteryEnum type, String masterId);

}
