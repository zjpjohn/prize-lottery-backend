package com.prize.lottery.value;

import com.google.common.base.Splitter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecValueItem {

    //预测值
    private String  value;
    //计算后预测值
    private String  hitValue;
    //是否命中:0-否,1-是
    private Integer hit;

    public RecValueItem(String value) {
        this.value = value;
        this.hit   = 0;
    }

    public RecValueItem valueHit(Map<String, Integer> judgeLottery) {
        int                             hits    = 0;
        String                          data    = value;
        Set<Map.Entry<String, Integer>> entries = judgeLottery.entrySet();
        for (Map.Entry<String, Integer> entry : entries) {
            if (data.contains(entry.getKey())) {
                hits = hits + entry.getValue();
                data = data.replaceAll(entry.getKey(), "[" + entry.getKey() + "]");
            }
        }
        int hitResult = hits >= 3 ? 1 : 0;
        return new RecValueItem(this.value, data, hitResult);
    }

    public List<String> split() {
        return Splitter.on(Pattern.compile("\\s+")).trimResults().omitEmptyStrings().splitToList(value);
    }

}
