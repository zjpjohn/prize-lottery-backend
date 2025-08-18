package com.prize.lottery.application.assembler;

import com.prize.lottery.application.command.dto.SubscribeMasterCmd;
import com.prize.lottery.application.vo.LottoBrowseVo;
import com.prize.lottery.application.vo.MasterInfoDetailVo;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.po.master.MasterBrowsePo;
import com.prize.lottery.po.master.MasterInfoPo;
import com.prize.lottery.po.master.MasterLotteryPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MasterInfoAssembler {

    @Mappings({@Mapping(source = "master.seq", target = "masterId")})
    MasterInfoDetailVo toVo(MasterInfoPo master, List<MasterLotteryPo> lotteries, Integer focused);

    List<MasterInfoDetailVo.MasterLotteryVo> toLotteryVos(List<MasterLotteryPo> lotteries);

    @Mappings({@Mapping(source = "type", target = "lottery")})
    MasterInfoDetailVo.MasterLotteryVo toItem(MasterLotteryPo masterLottery);

    default LotteryEnum toLotteryEnum(String type) {
        return LotteryEnum.findOf(type);
    }

    SubscribeMasterCmd from(String masterId, Long userId);

    LottoBrowseVo toVo(MasterBrowsePo browse);

    List<LottoBrowseVo> toBrowseList(List<MasterBrowsePo> poList);

}
