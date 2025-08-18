package com.prize.lottery.application.query;


import com.cloud.arch.page.Page;
import com.prize.lottery.application.query.dto.AdmVoucherLogQuery;
import com.prize.lottery.application.query.dto.AdmVoucherQuery;
import com.prize.lottery.application.query.dto.AppVoucherLogQuery;
import com.prize.lottery.application.query.vo.AppVoucherInfoVo;
import com.prize.lottery.application.query.vo.VoucherItemVo;
import com.prize.lottery.infrast.persist.po.UserVoucherLogPo;
import com.prize.lottery.infrast.persist.po.UserVoucherPo;
import com.prize.lottery.infrast.persist.po.VoucherInfoPo;
import com.prize.lottery.infrast.persist.vo.UserDrawVo;

import java.util.List;

public interface IVoucherQueryService {

    VoucherInfoPo getVoucherInfo(String seqNo);

    Page<VoucherInfoPo> getVoucherList(AdmVoucherQuery query);

    Page<UserVoucherLogPo> getVoucherLogList(AdmVoucherLogQuery query);

    Page<UserVoucherLogPo> getUserVoucherLogList(AppVoucherLogQuery query);

    List<AppVoucherInfoVo> appVoucherList(Long userId);

    List<VoucherItemVo> canDrawVoucherList(Long userId);

    UserVoucherPo getUserVoucher(Long userId);

    List<UserDrawVo> getLatestUserDraw();

}
