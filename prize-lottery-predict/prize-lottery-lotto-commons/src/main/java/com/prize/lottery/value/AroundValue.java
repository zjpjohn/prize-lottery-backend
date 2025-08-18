package com.prize.lottery.value;

import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TypeHandler(type = Type.JSON)
public class AroundValue {

    private List<AroundCell> cells;

    public AroundResult calc(List<String> balls) {
        List<AroundCell> collected = cells.stream().filter(cell -> balls.contains(cell.value)).toList();
        long             lotto     = collected.stream().filter(cell -> cell.getType() == 1).count();
        long             level1    = collected.stream().filter(cell -> cell.getType() == 2).count();
        long             level2    = collected.stream().filter(cell -> cell.getType() == 3).count();
        long             tuo       = collected.stream().filter(cell -> cell.getType() == 4).count();
        AroundResult     result    = new AroundResult();
        result.setTuo((int) tuo);
        result.setLotto((int) lotto);
        result.setLevel1((int) level1);
        result.setLevel2((int) level2);
        return result;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AroundCell {
        //绕胆图值
        @NotBlank(message = "数据不允许为空")
        private String  value;
        //值类型:1-开奖号,2-一级胆码,3-二级胆码,4-拖码
        @NotNull(message = "类型不允许为空")
        private Integer type;
    }

}
