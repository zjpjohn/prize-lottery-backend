package com.prize.lottery.infrast.persist.mapper;

import com.prize.lottery.infrast.persist.po.AppVerifyPo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AppVerifyMapper {

    int addAppVerify(AppVerifyPo verify);

    int editAppVerify(AppVerifyPo verify);

    AppVerifyPo getVerifyById(Long id);

    AppVerifyPo getVerifyByNo(String appNo);

}
