package com.prize.lottery.value;

import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TypeHandler(type = Type.JSON)
public class RedBall {

    private List<List<String>> balls;

    public static RedBall from(List<String> balls) {
        List<List<String>> list = Lists.newArrayList();
        list.add(balls);
        return new RedBall(list);
    }
}
