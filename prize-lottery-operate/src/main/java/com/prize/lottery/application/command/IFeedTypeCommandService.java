package com.prize.lottery.application.command;


import com.prize.lottery.application.command.dto.FeedTypeCreateCmd;
import com.prize.lottery.application.command.dto.FeedTypeEditCmd;

public interface IFeedTypeCommandService {

    void createFeedType(FeedTypeCreateCmd command);

    void editFeedType(FeedTypeEditCmd command);

    void sortFeedType(Long id, Integer position);

    void removeFeedType(Long id);

}
