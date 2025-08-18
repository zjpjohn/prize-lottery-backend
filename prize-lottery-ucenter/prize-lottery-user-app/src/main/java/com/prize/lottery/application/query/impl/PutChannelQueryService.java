package com.prize.lottery.application.query.impl;

import com.cloud.arch.page.Page;
import com.cloud.arch.utils.CollectionUtils;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.assembler.PutChannelAssembler;
import com.prize.lottery.application.query.IPutChannelQueryService;
import com.prize.lottery.application.query.dto.PutChannelQuery;
import com.prize.lottery.application.query.vo.PutChannelVo;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.mapper.PutChannelMapper;
import com.prize.lottery.infrast.persist.po.PutChannelPo;
import com.prize.lottery.infrast.persist.po.PutRecordPo;
import com.prize.lottery.infrast.persist.vo.PutChannelStatsVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PutChannelQueryService implements IPutChannelQueryService {

    private final PutChannelMapper    mapper;
    private final PutChannelAssembler assembler;

    @Override
    public Page<PutChannelVo> getChannelList(PutChannelQuery query) {
        return query.from().count(mapper::countPutChannels).query(mapper::getPutChannelList).flatMap(list -> {
            List<String>                   channels  = CollectionUtils.toList(list, PutChannelPo::getBizNo);
            List<PutChannelStatsVo>        statsList = mapper.getChannelStatsList(channels);
            Map<String, PutChannelStatsVo> statsMap  = CollectionUtils.toMap(statsList, PutChannelStatsVo::getCode);

            return list.stream().map(channel -> {
                PutChannelVo putChannel = assembler.toVo(channel);
                Optional.ofNullable(statsMap.get(channel.getBizNo()))
                        .ifPresent(stats -> assembler.toVo(stats, putChannel));
                return putChannel;
            }).collect(Collectors.toList());
        });
    }

    @Override
    public PutChannelVo getPutChannel(String code) {
        PutChannelPo channel = mapper.getPutChannelByNo(code);
        Assert.notNull(channel, ResponseHandler.PUT_CHANNEL_NONE);
        PutChannelVo putChannel = assembler.toVo(channel);

        //推广渠道统计信息
        PutChannelStatsVo stats = mapper.getChannelStats(code);
        assembler.toVo(stats, putChannel);
        return putChannel;
    }

    @Override
    public List<PutRecordPo> getPutRecords(String channel) {
        return mapper.getPutRecordsByChannel(channel);
    }

}
