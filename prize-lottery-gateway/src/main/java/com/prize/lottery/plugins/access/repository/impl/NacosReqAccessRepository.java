package com.prize.lottery.plugins.access.repository.impl;

import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import com.prize.lottery.nacos.INacosConfigParser;
import com.prize.lottery.nacos.NacosConfigSource;
import com.prize.lottery.plugins.access.domain.WhiteBlackInfo;
import com.prize.lottery.plugins.access.domain.WhitePattern;
import com.prize.lottery.plugins.access.repository.IReqAccessRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
public class NacosReqAccessRepository implements IReqAccessRepository, SmartInitializingSingleton, INacosConfigParser {

    private List<WhitePattern> whitePatterns = Lists.newArrayList();
    private List<String>       blackList     = Lists.newArrayList();

    private final String            dataId;
    private final String            group;
    private final NacosConfigSource configSource;

    public NacosReqAccessRepository(String dataId, String group, NacosConfigSource configSource) {
        this.dataId       = dataId;
        this.group        = group;
        this.configSource = configSource;
    }

    @Override
    public List<WhitePattern> getWhitePatterns() {
        return this.whitePatterns;
    }

    @Override
    public List<String> getBlackList() {
        return this.blackList;
    }

    @Override
    @SneakyThrows
    public void afterSingletonsInstantiated() {
        configSource.parseAndListen(this.dataId, this.group, this);
    }

    public synchronized void parse(String config) {
        if (StringUtils.hasText(config)) {
            WhiteBlackInfo whiteBlackInfo = JSON.parseObject(config, WhiteBlackInfo.class);
            this.whitePatterns = whiteBlackInfo.whitList();
            this.blackList     = whiteBlackInfo.getBlackList();
        }
    }

}
