package com.prize.lottery.value;

import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import com.cloud.arch.utils.CollectionUtils;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@TypeHandler(type = Type.JSON)
public class WarningInt {

    private List<Integer> values;

    public WarningInt(List<Integer> values) {
        if (CollectionUtils.isEmpty(values)) {
            this.values = Lists.newArrayList();
            return;
        }
        this.values = values;
    }

}
