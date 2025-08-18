package com.prize.lottery.application.command.executor.lotto;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.browse.model.ForecastBrowse;
import com.prize.lottery.domain.browse.repository.IUserBrowseRepository;
import com.prize.lottery.domain.browse.valobj.ForecastExpend;
import com.prize.lottery.dto.AcctVerifyRepo;
import com.prize.lottery.enums.BrowseType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.facade.ICloudUserAccountFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Slf4j
@Component
@RequiredArgsConstructor
public class ForecastLottoBrowseExecutor {

    private final ICloudUserAccountFacade accountFacade;
    private final IUserBrowseRepository   browseRepository;

    /**
     * 查询指定分类的浏览次数
     *
     * @param type   彩票种类
     * @param source 浏览类型
     */
    public Integer browses(LotteryEnum type, BrowseType source) {
        return browseRepository.browses(type.getType(), source.getType());
    }

    /**
     * 校验仅允许会员用户浏览
     *
     * @param browse   浏览数据模型
     * @param supplier 返回数据提供器
     */
    public <T> Pair<Boolean, T> executeOnlyMember(ForecastBrowse browse, Supplier<T> supplier) {
        //计算是否允许浏览数据
        browse.calcBrowse(browseRepository::isBrowsed);
        //非会员不允许浏览
        if (!browse.isCanBrowse() && !accountFacade.isUserMember(browse.getUserId())) {
            return Pair.of(false, null);
        }
        if (!browse.isBrowsed()) {
            //没有浏览数据,保存浏览记录
            browseRepository.save(browse);
        }
        return Pair.of(true, supplier.get());
    }

    /**
     * 校验查看预测数据
     *
     * @param browse   查看浏览领域模型
     * @param supplier 返回数据提供器
     */
    public <T> T execute(ForecastBrowse browse, Supplier<T> supplier) {
        //计算是否允许浏览数据
        browse.calcBrowse(browseRepository::isBrowsed);
        if (!browse.isCanBrowse()) {
            //校验用户账户信息<会员(是/否),账户余额(是/否)>
            AcctVerifyRepo result = accountFacade.verifyBalance(browse.getUserId(), browse.getExpend());
            ForecastExpend expend = ForecastExpend.of(browse.getExpend());
            Assert.state(result.isMember(), expend, ResponseHandler.FORECAST_PRIVILEGE);
        }
        if (!browse.isBrowsed()) {
            //没有浏览数据,保存浏览记录
            browseRepository.save(browse);
        }
        return supplier.get();
    }

    /**
     * 校验查看付费数据
     *
     * @param browse   浏览数据领域模型
     * @param supplier 付费数据提供器
     */
    public <T> Pair<Boolean, T> executeNull(ForecastBrowse browse, Supplier<T> supplier) {
        //计算是否允许浏览数据
        browse.calcBrowse(browseRepository::isBrowsed);
        if (!browse.isCanBrowse()) {
            //不允许浏览时，校验账户信息
            AcctVerifyRepo result = accountFacade.verifyBalance(browse.getUserId(), browse.getExpend());
            //会员账户直接浏览，非会员账户校验账户余额
            if (!result.isMember()) {
                return Pair.of(false, null);
            }
        }
        if (!browse.isBrowsed()) {
            //没有浏览数据,保存浏览记录
            browseRepository.save(browse);
        }
        return Pair.of(true, supplier.get());
    }
}
