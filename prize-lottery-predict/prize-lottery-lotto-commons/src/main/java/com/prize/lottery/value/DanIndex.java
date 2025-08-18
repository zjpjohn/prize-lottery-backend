package com.prize.lottery.value;

import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TypeHandler(type = Type.JSON)
public class DanIndex {

    private Integer key;
    private Integer calc;
    private Integer hit;
    private Integer omit;

    public void calc(List<Integer> balls) {
        this.calc = 1;
        this.hit  = balls.contains(key) ? 1 : 0;
        this.omit = this.hit == 0 ? this.omit + 1 : 0;
    }

    public static DanIndex of(Integer key, Integer omit) {
        return new DanIndex(key, 0, 0, omit);
    }

}
