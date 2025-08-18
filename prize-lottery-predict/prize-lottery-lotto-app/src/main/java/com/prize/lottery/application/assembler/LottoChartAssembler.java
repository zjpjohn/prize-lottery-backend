package com.prize.lottery.application.assembler;

import com.prize.lottery.enums.*;
import com.prize.lottery.po.dlt.DltLottoCensusPo;
import com.prize.lottery.po.fc3d.Fc3dLottoCensusPo;
import com.prize.lottery.po.kl8.Kl8LottoCensusPo;
import com.prize.lottery.po.pl3.Pl3LottoCensusPo;
import com.prize.lottery.po.qlc.QlcLottoCensusPo;
import com.prize.lottery.po.share.BaseLottoCensus;
import com.prize.lottery.po.ssq.SsqLottoCensusPo;
import com.prize.lottery.vo.NumberThreeCensusVo;
import com.prize.lottery.vo.SyntheticFullCensusVo;
import com.prize.lottery.vo.SyntheticItemCensusVo;
import com.prize.lottery.vo.SyntheticVipCensusVo;
import com.prize.lottery.vo.dlt.DltChartCensusVo;
import com.prize.lottery.vo.kl8.Kl8FullCensusVo;
import com.prize.lottery.vo.qlc.QlcChartCensusVo;
import com.prize.lottery.vo.ssq.SsqChartCensusVo;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@UtilityClass
public class LottoChartAssembler {

    /**
     * 装配分类统计数据
     */
    public static SyntheticItemCensusVo assembleItem(List<BaseLottoCensus> pos) {
        SyntheticItemCensusVo census = new SyntheticItemCensusVo();
        census.setPeriod(pos.get(0).getPeriod());
        Map<Integer, BaseLottoCensus> censusMap = pos.stream()
                                                     .collect(Collectors.toMap(BaseLottoCensus::getLevel, Function.identity()));
        for (ItemChartEnums assembler : ItemChartEnums.values()) {
            BaseLottoCensus po = censusMap.get(assembler.getLimit());
            assembler.assemble(census, po);
        }
        return census;
    }

    /**
     * 装配全量统计数据
     *
     * @param pos 统计数据
     */
    public static SyntheticFullCensusVo assembleFull(List<BaseLottoCensus> pos) {
        SyntheticFullCensusVo census = new SyntheticFullCensusVo();
        census.setPeriod(pos.get(0).getPeriod());
        Map<Integer, BaseLottoCensus> censusMap = pos.stream()
                                                     .collect(Collectors.toMap(BaseLottoCensus::getLevel, Function.identity()));
        for (FullChartEnums assembler : FullChartEnums.values()) {
            BaseLottoCensus po = censusMap.get(assembler.getLimit());
            assembler.assemble(census, po);
        }
        return census;
    }

    /**
     * 装配vip统计数据
     *
     * @param pos
     */
    public static SyntheticVipCensusVo assembleVip(List<BaseLottoCensus> pos) {
        SyntheticVipCensusVo census = new SyntheticVipCensusVo();
        census.setPeriod(pos.get(0).getPeriod());
        Map<Integer, BaseLottoCensus> poMap = pos.stream()
                                                 .collect(Collectors.toMap(BaseLottoCensus::getLevel, Function.identity()));
        for (VipChartEnums assembler : VipChartEnums.values()) {
            BaseLottoCensus po = poMap.get(assembler.getLimit());
            assembler.assemble(census, po);
        }
        return census;
    }

    /**
     * 装配福彩3D热门或高命中率统计数据
     *
     * @param pos
     */
    public static NumberThreeCensusVo assembleFsdChart(List<Fc3dLottoCensusPo> pos) {
        NumberThreeCensusVo census = new NumberThreeCensusVo();
        census.setPeriod(pos.get(0).getPeriod());
        Map<Fc3dChannel, Fc3dLottoCensusPo> poMap = pos.stream()
                                                       .collect(Collectors.toMap(Fc3dLottoCensusPo::getChannel, Function.identity()));
        for (Fc3dChannel channel : Fc3dChannel.values()) {
            Fc3dLottoCensusPo po = poMap.get(channel);
            channel.assemble(census, po);
        }
        return census;
    }

    /**
     * 装配排列三热门或高命中率统计数据
     *
     * @param pos
     */
    public static NumberThreeCensusVo assemblePlsChart(List<Pl3LottoCensusPo> pos) {
        NumberThreeCensusVo census = new NumberThreeCensusVo();
        census.setPeriod(pos.get(0).getPeriod());
        Map<Pl3Channel, Pl3LottoCensusPo> poMap = pos.stream()
                                                     .collect(Collectors.toMap(Pl3LottoCensusPo::getChannel, Function.identity()));
        for (Pl3Channel channel : Pl3Channel.values()) {
            Pl3LottoCensusPo po = poMap.get(channel);
            channel.assemble(census, po);
        }
        return census;
    }

    /**
     * 装配双色球热门或高命中统计数数据
     *
     * @param pos
     */
    public static SsqChartCensusVo assembleSsqChart(List<SsqLottoCensusPo> pos) {
        SsqChartCensusVo census = new SsqChartCensusVo();
        census.setPeriod(pos.get(0).getPeriod());
        Map<SsqChannel, SsqLottoCensusPo> poMap = pos.stream()
                                                     .collect(Collectors.toMap(SsqLottoCensusPo::getChannel, Function.identity()));
        for (SsqChannel channel : SsqChannel.values()) {
            SsqLottoCensusPo po = poMap.get(channel);
            channel.assemble(census, po);
        }
        return census;
    }

    /**
     * 装配大乐透热门或高命中统计数据
     *
     * @param pos
     */
    public static DltChartCensusVo assembleDltChart(List<DltLottoCensusPo> pos) {
        DltChartCensusVo census = new DltChartCensusVo();
        census.setPeriod(pos.get(0).getPeriod());
        Map<DltChannel, DltLottoCensusPo> poMap = pos.stream()
                                                     .collect(Collectors.toMap(DltLottoCensusPo::getChannel, Function.identity()));
        for (DltChannel channel : DltChannel.values()) {
            DltLottoCensusPo po = poMap.get(channel);
            channel.assemble(census, po);
        }
        return census;
    }

    /**
     * 装配七乐彩热门或高命中统计数据
     *
     * @param pos
     */
    public static QlcChartCensusVo assembleQlcChart(List<QlcLottoCensusPo> pos) {
        QlcChartCensusVo census = new QlcChartCensusVo();
        census.setPeriod(pos.get(0).getPeriod());
        Map<QlcChannel, QlcLottoCensusPo> poMap = pos.stream()
                                                     .collect(Collectors.toMap(QlcLottoCensusPo::getChannel, Function.identity()));
        for (QlcChannel channel : QlcChannel.values()) {
            QlcLottoCensusPo po = poMap.get(channel);
            channel.assemble(census, po);
        }
        return census;
    }

    public static Kl8FullCensusVo assembleKl8Chart(List<Kl8LottoCensusPo> pos) {
        Kl8FullCensusVo census = new Kl8FullCensusVo();
        census.setPeriod(pos.get(0).getPeriod());
        Map<Integer, Kl8LottoCensusPo> censusMap = pos.stream()
                                                      .collect(Collectors.toMap(Kl8LottoCensusPo::getLevel, Function.identity()));
        for (Kl8FullChart assembler : Kl8FullChart.values()) {
            BaseLottoCensus po = censusMap.get(assembler.getLimit());
            assembler.assemble(census, po);
        }
        return census;
    }

}
