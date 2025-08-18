package com.prize.lottery.application.command;


import com.prize.lottery.application.command.dto.ResourceBatchCmd;
import com.prize.lottery.application.command.dto.ResourceCreateCmd;
import com.prize.lottery.application.command.dto.ResourceEditCmd;

public interface IResourceCommandService {

    void batchAddResources(ResourceBatchCmd cmd);

    void createAppResource(ResourceCreateCmd cmd);

    void editAppResource(ResourceEditCmd cmd);

    void useAppResource(Long id);

    void unUseResource(Long id);

    void removeResource(Long id);

    void rollbackResource(Long id);

    void loadResources(String appNo);

}
