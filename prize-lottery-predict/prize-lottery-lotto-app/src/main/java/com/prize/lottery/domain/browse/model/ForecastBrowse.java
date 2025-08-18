package com.prize.lottery.domain.browse.model;

import com.cloud.arch.utils.IdWorker;
import com.prize.lottery.application.command.event.DeductBalanceEvent;
import com.prize.lottery.domain.master.model.MasterAccumulate;
import com.prize.lottery.enums.BrowseType;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.value.Period;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

@Data
@Slf4j
public class ForecastBrowse {

    private String                      seqNo;
    private Long                        userId;
    private Period                      period;
    private LotteryEnum                 type;
    private BrowseType                  source;
    private String                      sourceId;
    private Integer                     expend;
    private MasterAccumulate            master;
    private BiPredicate<String, String> vipPredicate;
    private boolean                     canBrowse = false;
    private boolean                     browsed   = false;

    public ForecastBrowse(Long userId, Period period, LotteryEnum type) {
        this.userId = userId;
        this.period = period;
        this.type   = type;
    }

    /**
     * 预测数据查看
     */
    public void lottoForecast(String masterId, Integer expend, BiPredicate<String, String> vipPredicate) {
        this.expend       = expend;
        this.sourceId     = masterId;
        this.source       = BrowseType.FORECAST;
        this.vipPredicate = vipPredicate;
        this.master       = new MasterAccumulate(masterId).browse();
    }

    /**
     * 统计计算图表查看
     */
    public void lottoChart(ChartType source, Integer expend) {
        this.expend   = expend;
        this.source   = source.browseType();
        this.sourceId = source.getSourceId();
    }

    /**
     * 批量预测查看
     */
    public void batchCompare(Integer expend) {
        this.expend   = expend;
        this.source   = BrowseType.COMPARE;
        this.sourceId = BrowseType.COMPARE.getSourceId();
    }

    /**
     * 查看智能选彩指数
     */
    public void lotteryIndex(Integer expend) {
        this.expend   = expend;
        this.source   = BrowseType.LOTTO_INDEX;
        this.sourceId = BrowseType.LOTTO_INDEX.getSourceId();
    }

    /**
     * 查看选三分类指数
     */
    public void num3Index(Integer expend) {
        this.expend   = expend;
        this.source   = BrowseType.NUM3_INDEX;
        this.sourceId = BrowseType.NUM3_INDEX.getSourceId();
    }

    /**
     * 选三号码预警分析
     */
    public void num3Warn() {
        this.source   = BrowseType.NUM3_WARN;
        this.sourceId = BrowseType.NUM3_WARN.getSourceId();
    }

    /**
     * 选三号码分层预警分析
     */
    public void num3Layer() {
        this.source   = BrowseType.NUM3_LAYER;
        this.sourceId = BrowseType.NUM3_LAYER.getSourceId();
    }

    /**
     * 查看选彩预警推荐
     */
    public void lottoWarn() {
        this.source   = BrowseType.LOTTO_WARN;
        this.sourceId = BrowseType.LOTTO_WARN.getSourceId();
    }

    /**
     * 查看今日要点
     */
    public void lottoPivot(String sourceId) {
        this.source   = BrowseType.TODAY_PIVOT;
        this.sourceId = sourceId;
    }

    /**
     * 构造扣减账户余额事件
     */
    public DeductBalanceEvent deductEvent() {
        return new DeductBalanceEvent(this.seqNo, this.userId, this.type.getNameZh(), this.source, this.sourceId, this.period.getPeriod(), this.expend);
    }

    public void calcBrowse(Predicate<ForecastBrowse> predicate) {
        //是否已经查看过预测
        if (predicate.test(this)) {
            this.setBrowsed(true);
            this.setCanBrowse(true);
            return;
        }
        //是否免费时段或者历史数据或者不是vip专家预测
        if (this.isFreeTimeOrOpened() || this.isFreeMaster()) {
            this.setCanBrowse(true);
        }
        //设置浏览流水号
        this.seqNo = String.valueOf(IdWorker.nextId());
    }

    private boolean isFreeTimeOrOpened() {
        return period.isCalculated() || type.freeTime(LocalDateTime.now());
    }

    private boolean isFreeMaster() {
        if (this.master == null) {
            return false;
        }
        String lastPeriod = type.lastPeriod(period.getPeriod());
        return !vipPredicate.test(this.master.getMasterId(), lastPeriod);
    }

}
