package com.prize.lottery.application.vo;

import com.cloud.arch.utils.CollectionUtils;
import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.prize.lottery.enums.Fc3dChannel;
import com.prize.lottery.enums.Pl3Channel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;

@Data
public class N3ItemBestTableVo {

    private BestTableRow zo;
    private BestTableRow k1;
    private BestTableRow k2;
    private BestTableRow c7;
    private BestTableRow c6;
    private BestTableRow c5;
    private BestTableRow d3;
    private BestTableRow d2;
    private BestTableRow d1;

    public static N3ItemBestTableVo pl3(Map<Pl3Channel, N3ItemCensusVo.ItemCensus> channelMap,
                                        N3ItemCensusVo.ItemCensus zong) {
        N3ItemBestTableVo table = new N3ItemBestTableVo();
        table.setZo(new BestTableRow(zong));
        table.setC7(new BestTableRow(channelMap.get(Pl3Channel.COM7)));
        table.setC6(new BestTableRow(channelMap.get(Pl3Channel.COM6)));
        table.setC5(new BestTableRow(channelMap.get(Pl3Channel.COM5)));
        table.setK1(new BestTableRow(channelMap.get(Pl3Channel.KILL1)));
        table.setK2(new BestTableRow(channelMap.get(Pl3Channel.KILL2)));
        table.setD3(new BestTableRow(channelMap.get(Pl3Channel.DAN3)));
        table.setD2(new BestTableRow(channelMap.get(Pl3Channel.DAN2)));
        table.setD1(new BestTableRow(channelMap.get(Pl3Channel.DAN1)));
        return table;
    }

    public static N3ItemBestTableVo fc3d(Map<Fc3dChannel, N3ItemCensusVo.ItemCensus> channelMap,
                                         N3ItemCensusVo.ItemCensus zong) {
        N3ItemBestTableVo table = new N3ItemBestTableVo();
        table.setZo(new BestTableRow(zong));
        table.setC7(new BestTableRow(channelMap.get(Fc3dChannel.COM7)));
        table.setC6(new BestTableRow(channelMap.get(Fc3dChannel.COM6)));
        table.setC5(new BestTableRow(channelMap.get(Fc3dChannel.COM5)));
        table.setK1(new BestTableRow(channelMap.get(Fc3dChannel.KILL1)));
        table.setK2(new BestTableRow(channelMap.get(Fc3dChannel.KILL2)));
        table.setD3(new BestTableRow(channelMap.get(Fc3dChannel.DAN3)));
        table.setD2(new BestTableRow(channelMap.get(Fc3dChannel.DAN2)));
        table.setD1(new BestTableRow(channelMap.get(Fc3dChannel.DAN1)));
        return table;
    }

    @Data
    @AllArgsConstructor
    public static class BestTableCell {

        public static final BestTableCell EMPTY = new BestTableCell(0L, Lists.newArrayList());

        private Long         count;
        private List<String> values;

    }

    @Data
    public static class BestTableRow {

        private BestTableCell k1 = BestTableCell.EMPTY;
        private BestTableCell k2 = BestTableCell.EMPTY;
        private BestTableCell d2 = BestTableCell.EMPTY;
        private BestTableCell d3 = BestTableCell.EMPTY;
        private BestTableCell c7 = BestTableCell.EMPTY;

        public BestTableRow(N3ItemCensusVo.ItemCensus census) {
            if (census != null) {
                k1 = getMin(census.getK1());
                k2 = getMin(census.getK2());
                d2 = getMax(census.getD2());
                d3 = getMax(census.getD3());
                c7 = getMax(census.getC7());
            }
        }

        private BestTableCell getMax(Multimap<Long, String> data) {
            Long               max    = max(data.keySet());
            Collection<String> values = data.get(max);
            return new BestTableCell(max, Lists.newArrayList(values));
        }

        private BestTableCell getMin(Multimap<Long, String> data) {
            Long               min    = min(data.keySet());
            Collection<String> values = data.get(min);
            return new BestTableCell(min, Lists.newArrayList(values));
        }

        private Long min(Set<Long> values) {
            Assert.state(CollectionUtils.isNotEmpty(values), "集合不允许为空");
            return values.stream().min(Comparator.naturalOrder()).orElse(0L);
        }

        private Long max(Set<Long> values) {
            Assert.state(CollectionUtils.isNotEmpty(values), "集合不允许为空");
            return values.stream().max(Comparator.naturalOrder()).orElse(0L);
        }
    }
}
