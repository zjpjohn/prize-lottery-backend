package com.prize.lottery.value;

import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@TypeHandler(type = Type.JSON)
public class HoneyValue implements Serializable {

    private static final long serialVersionUID = 4226021980022345973L;

    private List<List<String>> values = Lists.newArrayList();

    public HoneyValue(List<String> balls) {
        values.add(Lists.newArrayList(balls.subList(0, 1)));
        values.add(Lists.newArrayList(balls.subList(1, 3)));
        values.add(Lists.newArrayList(balls.subList(3, 6)));
        values.add(Lists.newArrayList(balls.subList(6, 10)));
        values.add(Lists.newArrayList(balls.subList(10, 13)));
        values.add(Lists.newArrayList(balls.subList(13, 15)));
        values.add(Lists.newArrayList(balls.subList(15, 16)));
    }

    public static HoneyValue of(List<String> balls) {
        return new HoneyValue(balls);
    }

}
