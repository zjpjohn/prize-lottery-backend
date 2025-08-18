package com.prize.lottery.domain.withdraw.model;

import com.cloud.arch.aggregate.AggregateRoot;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.TransferScene;
import com.prize.lottery.infrast.persist.enums.WithLevelSate;
import com.prize.lottery.infrast.persist.value.LevelValue;
import com.prize.lottery.infrast.persist.value.WithdrawLevels;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Data
@NoArgsConstructor
public class WithdrawLevelDo implements AggregateRoot<Long> {

    private static final long serialVersionUID = 5494643145098502199L;

    private Long           id;
    private TransferScene  scene;
    private WithdrawLevels levels;
    private WithLevelSate  state;
    private String         remark;

    public WithdrawLevelDo(TransferScene scene, List<LevelValue> levels, String remark) {
        this.scene  = scene;
        this.remark = remark;
        this.levels = new WithdrawLevels(levels);
        this.state  = WithLevelSate.CREATED;
    }

    public void modify(List<LevelValue> levels, WithLevelSate state, String remark) {
        if (state != null) {
            boolean transitioned = this.state.transitions().contains(state);
            Assert.state(transitioned, ResponseHandler.DATA_STATE_ILLEGAL);
            this.state = state;
            return;
        }
        Assert.state(this.state == WithLevelSate.CREATED, ResponseHandler.DATA_STATE_ILLEGAL);
        if (!CollectionUtils.isEmpty(levels)) {
            this.levels = new WithdrawLevels(levels);
        }
        if (StringUtils.isNotBlank(remark)) {
            this.remark = remark;
        }
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }

}
