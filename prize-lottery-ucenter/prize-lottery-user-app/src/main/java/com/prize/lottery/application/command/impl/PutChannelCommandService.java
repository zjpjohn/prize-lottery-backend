package com.prize.lottery.application.command.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.enums.Value;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.assembler.PutChannelAssembler;
import com.prize.lottery.application.command.IPutChannelCommandService;
import com.prize.lottery.application.command.dto.PutChannelCreateCmd;
import com.prize.lottery.application.command.dto.PutChannelEditCmd;
import com.prize.lottery.application.command.dto.PutRecordCreateCmd;
import com.prize.lottery.application.command.dto.PutRecordEditCmd;
import com.prize.lottery.domain.channel.model.PutChannelDo;
import com.prize.lottery.domain.channel.model.PutRecordDo;
import com.prize.lottery.domain.channel.repository.IPutChannelRepository;
import com.prize.lottery.domain.channel.repository.IPutRecordRepository;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.PutState;
import com.prize.lottery.infrast.persist.mapper.PutChannelMapper;
import com.prize.lottery.infrast.persist.po.PutChannelPo;
import com.prize.lottery.infrast.props.CloudLotteryProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PutChannelCommandService implements IPutChannelCommandService {

    private final PutChannelAssembler    assembler;
    private final PutChannelMapper       mapper;
    private final IPutChannelRepository  channelRepository;
    private final IPutRecordRepository   recordRepository;
    private final CloudLotteryProperties properties;

    @Override
    public void createChannel(PutChannelCreateCmd cmd) {
        PutChannelDo putChannel = new PutChannelDo(cmd, assembler::toDo);
        AggregateFactory.create(putChannel).save(channelRepository::save);
    }

    @Override
    public void editChannel(PutChannelEditCmd command) {
        channelRepository.ofId(command.getId())
                         .peek(channel -> channel.modify(command, assembler::toDo))
                         .save(channelRepository::save);
    }

    @Override
    public void createRecord(PutRecordCreateCmd command) {
        PutChannelPo channel = mapper.getPutChannelByNo(command.getChannel());
        Assert.notNull(channel, ResponseHandler.PUT_CHANNEL_NONE);
        PutState state = Value.ofNullable(command.getState(), PutState.class)
                              .orElseThrow(ResponseHandler.RECORD_STATE_ERROR);
        PutRecordDo putRecord = new PutRecordDo(channel.getAppNo(), channel.getBizNo(), command.getExpectAmt(), state, command.getRemark(), properties.getUri());
        AggregateFactory.create(putRecord).save(recordRepository::save);
    }

    @Override
    public void editRecord(PutRecordEditCmd command) {
        Aggregate<Long, PutRecordDo> aggregate = recordRepository.ofId(command.getId())
                                                                 .orElseThrow(ResponseHandler.PUT_RECORD_NONE);
        aggregate.peek(record -> record.modify(command.getRemark(), command.getState())).save(recordRepository::save);
    }

}
