package com.prize.lottery.infrast.persist.valobj;

import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Set;

@Data
public class AssistantApp {

    private String      appNo;
    private Set<String> versions;

    /**
     * 判断目标版本是否包含在应用版本集合中
     *
     * @param version 目标版本
     */
    public boolean checkVersion(String version) {
        if (!StringUtils.hasText(version)) {
            return true;
        }
        return versions.contains(version);
    }


}
