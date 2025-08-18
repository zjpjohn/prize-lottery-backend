package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import com.prize.lottery.infrast.persist.enums.TransferScene;
import com.prize.lottery.infrast.persist.enums.WithLevelSate;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LevelListQuery extends PageQuery {

    private static final long serialVersionUID = 1172973628058747884L;

    private TransferScene scene;
    private WithLevelSate state;

}
