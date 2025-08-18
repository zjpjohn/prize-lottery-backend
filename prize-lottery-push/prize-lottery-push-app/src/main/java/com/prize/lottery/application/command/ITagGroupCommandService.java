package com.prize.lottery.application.command;


import com.prize.lottery.application.command.dto.TagGroupCreateCmd;
import com.prize.lottery.application.command.dto.TagGroupModifyCmd;

public interface ITagGroupCommandService {

    void createTagGroup(TagGroupCreateCmd command);

    void modifyTagGroup(TagGroupModifyCmd command);

    void dilatateTagGroup(Long groupId);


}
