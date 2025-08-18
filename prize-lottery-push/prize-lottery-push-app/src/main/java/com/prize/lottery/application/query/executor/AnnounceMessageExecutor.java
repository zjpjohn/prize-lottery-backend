package com.prize.lottery.application.query.executor;

import com.cloud.arch.page.Page;
import com.prize.lottery.application.assembler.ChannelAssembler;
import com.prize.lottery.application.query.dto.MessageAppQuery;
import com.prize.lottery.application.query.vo.MessageInfoVo;
import com.prize.lottery.domain.facade.IUserInfoFacade;
import com.prize.lottery.domain.message.model.AnnounceMailboxDo;
import com.prize.lottery.domain.message.repository.IAnnounceRepository;
import com.prize.lottery.infrast.persist.mapper.AnnounceInfoMapper;
import com.prize.lottery.infrast.persist.mapper.ChannelInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class AnnounceMessageExecutor extends AbsMessageExecutor<MessageInfoVo> {

    private final ChannelAssembler    channelAssembler;
    private final AnnounceInfoMapper  announceMapper;
    private final IAnnounceRepository announceRepository;

    public AnnounceMessageExecutor(AnnounceInfoMapper announceInfoMapper,
                                   IAnnounceRepository announceRepository,
                                   ChannelInfoMapper channelMapper,
                                   IUserInfoFacade userInfoFacade,
                                   ChannelAssembler channelAssembler) {
        super(channelMapper, userInfoFacade);
        this.announceMapper     = announceInfoMapper;
        this.announceRepository = announceRepository;
        this.channelAssembler   = channelAssembler;
    }

    @Override
    protected Page<MessageInfoVo> doExecute(MessageAppQuery query) {
        Page<MessageInfoVo> pageResult = query.from()
                                              .count(announceMapper::countAnnounces)
                                              .query(announceMapper::getAnnounceList)
                                              .map(channelAssembler::fromAnnounce);
        if (query.getPage() > 1 || pageResult.getSize() == 0) {
            return pageResult;
        }
        //更新用户通知信箱偏移量
        AnnounceMailboxDo mailbox  = announceRepository.ofMailBox(query.getUserId(), query.getChannel());
        MessageInfoVo     announce = pageResult.getRecords().get(0);
        if (mailbox.offsetToLatest(announce.getId(), announce.getGmtCreate())) {
            announceRepository.save(mailbox);
        }
        return pageResult;
    }

}
