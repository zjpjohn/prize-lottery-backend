package com.prize.lottery.domain.app.model;

import com.cloud.arch.aggregate.AggregateRoot;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.dto.AssistantCreateCmd;
import com.prize.lottery.application.command.dto.AssistantModifyCmd;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.CommonState;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

@Data
@NoArgsConstructor
public class AppAssistantDo implements AggregateRoot<Long> {

    private static final long serialVersionUID = -1462993811117537650L;

    private Long        id;
    private String      appNo;
    private String      suitVer;
    private String      type;
    private String      title;
    private String      content;
    private CommonState state;
    private Integer     sort;
    private String      remark;

    public AppAssistantDo(AssistantCreateCmd command,
                          Integer sort,
                          BiConsumer<AssistantCreateCmd, AppAssistantDo> converter) {
        this.sort  = sort;
        this.state = CommonState.CREATED;
        converter.accept(command, this);
    }

    public void modify(AssistantModifyCmd command, BiConsumer<AssistantModifyCmd, AppAssistantDo> converter) {
        CommonState newState = command.getState();
        if (newState == null) {
            converter.accept(command, this);
            return;
        }
        Set<CommonState> transitions = state.transitions();
        Assert.state(transitions.contains(newState), ResponseHandler.DATA_STATE_ILLEGAL);
        this.state = newState;
    }

    public boolean isSuitableVersion(String suitVer) {
        return StringUtils.hasText(suitVer) && !this.suitVer.equals(suitVer);
    }

    public void sort(Integer position, Function<String, Integer> maxSortFunc) {
        Integer maxPosition = maxSortFunc.apply(this.appNo);
        Assert.state(position > 0 && position <= maxPosition, ResponseHandler.SORT_INDEX_ERROR);
        this.sort = position;
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }
}
