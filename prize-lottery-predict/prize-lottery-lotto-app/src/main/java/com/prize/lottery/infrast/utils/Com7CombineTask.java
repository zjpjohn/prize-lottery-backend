package com.prize.lottery.infrast.utils;

import com.cloud.arch.utils.CollectionUtils;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class Com7CombineTask {

    private final String       com7;
    private final List<String> dans;
    private final List<String> kills;

    public Com7CombineTask(String com7, List<String> dans, List<String> kills) {
        this.com7  = com7;
        this.dans  = dans;
        this.kills = kills;
    }

    public List<String> execute() {
        List<String> list = Splitter.on(Pattern.compile("\\s+")).splitToList(com7);
        if (CollectionUtils.isNotEmpty(dans) && dans.stream().noneMatch(list::contains)) {
            return Collections.emptyList();
        }
        list = Lists.newArrayList(list);
        if (CollectionUtils.isNotEmpty(kills)) {
            list.removeAll(kills);
        }
        long         combine = CombinationUtils.getCombine(list.size(), 3);
        List<String> result  = Lists.newArrayList();
        for (int i = 0; i < combine; i++) {
            List<String> value = CombinationUtils.getCombineValue(list, 3, i);
            if (CollectionUtils.isEmpty(dans) || dans.stream().anyMatch(value::contains)) {
                String selected = value.stream().sorted().collect(Collectors.joining(" "));
                result.add(selected);
            }
        }
        return result;
    }
}
