package com.prize.lottery.value;

import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import com.cloud.arch.utils.CollectionUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TypeHandler(type = Type.JSON)
public class LottoIndex {

    private List<BallIndex> values;

    public LottoIndex sort() {
        if (CollectionUtils.isNotEmpty(values)) {
            values.sort(Comparator.reverseOrder());
        }
        return this;
    }
}
