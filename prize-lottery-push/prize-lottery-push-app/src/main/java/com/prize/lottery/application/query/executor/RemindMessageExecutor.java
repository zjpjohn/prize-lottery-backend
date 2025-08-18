package com.prize.lottery.application.query.executor;

import com.cloud.arch.page.Page;
import com.prize.lottery.application.assembler.ChannelAssembler;
import com.prize.lottery.application.query.dto.MessageAppQuery;
import com.prize.lottery.application.query.vo.MessageInfoVo;
import com.prize.lottery.domain.facade.IUserInfoFacade;
import com.prize.lottery.domain.message.model.RemindMailboxDo;
import com.prize.lottery.domain.message.repository.IRemindRepository;
import com.prize.lottery.infrast.persist.mapper.ChannelInfoMapper;
import com.prize.lottery.infrast.persist.mapper.RemindInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class RemindMessageExecutor extends AbsMessageExecutor<MessageInfoVo> {

    private final RemindInfoMapper  remindMapper;
    private final ChannelAssembler  channelAssembler;
    private final IRemindRepository remindRepository;

    public RemindMessageExecutor(RemindInfoMapper remindMapper,
                                 IRemindRepository remindRepository,
                                 ChannelInfoMapper channelMapper,
                                 IUserInfoFacade userInfoFacade,
                                 ChannelAssembler channelAssembler) {
        super(channelMapper, userInfoFacade);
        this.remindMapper     = remindMapper;
        this.remindRepository = remindRepository;
        this.channelAssembler = channelAssembler;
    }

    @Override
    protected Page<MessageInfoVo> doExecute(MessageAppQuery query) {
        Page<MessageInfoVo> pageResult = query.from()
                                              .count(remindMapper::countReminds)
                                              .query(remindMapper::getRemindList)
                                              .map(channelAssembler::fromRemind);
        if (query.getPage() > 1 || pageResult.getSize() == 0) {
            return pageResult;
        }
        RemindMailboxDo mailBox = remindRepository.ofMailBox(query.getUserId(), query.getChannel());
        MessageInfoVo   remind  = pageResult.getRecords().get(0);
        if (mailBox.offsetToLatest(remind.getId(), remind.getGmtCreate())) {
            remindRepository.save(mailBox);
        }
        return pageResult;
    }

}
