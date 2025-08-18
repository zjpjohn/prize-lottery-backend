package com.prize.lottery.domain.message.repository;


import com.prize.lottery.domain.message.model.RemindMailboxDo;

public interface IRemindRepository {

    void save(RemindMailboxDo mailbox);

    RemindMailboxDo ofMailBox(Long receiverId, String channel);


}
