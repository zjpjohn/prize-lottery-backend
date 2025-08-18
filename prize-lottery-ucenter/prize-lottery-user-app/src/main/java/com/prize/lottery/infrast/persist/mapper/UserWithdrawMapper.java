package com.prize.lottery.infrast.persist.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.infrast.persist.po.UserWithdrawPo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserWithdrawMapper {

    int addUserWithdraw(UserWithdrawPo withdraw);

    int editUserWithdraw(UserWithdrawPo withdraw);

    UserWithdrawPo getWithdrawById(Long id);

    UserWithdrawPo getWithdrawBySeqNo(String seqNo);

    UserWithdrawPo latestWithdraw(Long userId);

    List<UserWithdrawPo> getUserWithdrawList(PageCondition condition);

    int countUserWithdraws(PageCondition condition);

}
