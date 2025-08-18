package com.prize.lottery.infrast.persist.value;

import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TypeHandler(type = Type.JSON)
public class RaffleCode {

    private static final String NUMBERS = "0123456789";
    private static final Random delta   = new Random(System.nanoTime());

    private List<String> codes;

    private RaffleCode generate() {
        LocalDateTime time         = LocalDateTime.now().withHour(21).withMinute(0);
        long          milli        = time.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
        long          deltaSeconds = delta.nextInt(200);
        Random        random       = new Random(milli + deltaSeconds);
        List<String>  result       = Lists.newArrayList();
        for (int i = 0; i < 3; i++) {
            int  index   = random.nextInt(10);
            char charred = NUMBERS.charAt(index);
            result.add(String.valueOf(charred));
        }
        this.codes = result;
        return this;
    }

    public List<String> shuffle() {
        List<String> source = Lists.newArrayList(this.codes);
        Collections.shuffle(source);
        return source;
    }

    public boolean isSame(RaffleCode code) {
        List<String> target    = code.getCodes();
        String       targetStr = target.stream().sorted().collect(Collectors.joining());
        String       sourceStr = this.codes.stream().sorted().collect(Collectors.joining());
        return targetStr.equals(sourceStr);
    }

    /**
     * 搜集生成抽奖码
     */
    public static RaffleCode create() {
        return new RaffleCode().generate();
    }

    /**
     * 生成指定的抽奖码
     */
    public static RaffleCode create(List<String> codes) {
        return new RaffleCode(codes);
    }

}
