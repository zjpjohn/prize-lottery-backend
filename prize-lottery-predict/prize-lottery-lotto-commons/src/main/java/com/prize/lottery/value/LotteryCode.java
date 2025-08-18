package com.prize.lottery.value;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LotteryCode {

    private static final String NUMBERS = "0123456789";

    private List<String> codes;

    private LotteryCode generate(LocalDateTime time, Integer delta) {
        long         milli  = time.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
        Random       random = new Random(milli + delta);
        List<String> result = Lists.newArrayList();
        for (int i = 0; i < 3; i++) {
            int  index   = random.nextInt(10);
            char charred = NUMBERS.charAt(index);
            result.add(String.valueOf(charred));
        }
        this.codes = result;
        this.codes.sort(Comparator.naturalOrder());
        return this;
    }

    public String join() {
        return String.join("", this.codes);
    }

    public static LotteryCode create(LocalDateTime time, Integer delta) {
        return new LotteryCode().generate(time, delta);
    }

}
