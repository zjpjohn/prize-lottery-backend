package com.prize.lottery.application.query.impl;

import com.cloud.arch.cache.annotations.CacheResult;
import com.cloud.arch.cache.annotations.Local;
import com.cloud.arch.cache.annotations.Remote;
import com.cloud.arch.page.Page;
import com.prize.lottery.application.query.IAppConfQueryService;
import com.prize.lottery.application.query.dto.AppConfQuery;
import com.prize.lottery.application.vo.AppConfVo;
import com.prize.lottery.infrast.persist.enums.ConfState;
import com.prize.lottery.infrast.persist.mapper.AppInfoMapper;
import com.prize.lottery.infrast.persist.po.AppConfPo;
import com.prize.lottery.infrast.repository.impl.AppConfRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppConfQueryService implements IAppConfQueryService {

    private final AppInfoMapper appInfoMapper;

    @Override
    public Page<AppConfPo> getAppConfList(AppConfQuery query) {
        return query.from().count(appInfoMapper::countAppConfs).query(appInfoMapper::getAppConfList);
    }

    @Override
    public AppConfPo getAppConf(Long id) {
        return appInfoMapper.getAppConfById(id);
    }

    @Override
    @CacheResult(
            names = AppConfRepository.APP_CONF_CACHE_NAME,
            key = "#appNo",
            enableLocal = true,
            remote = @Remote(expire = 12 * 3600L, randomBound = 3600),
            local = @Local(expire = 8 * 3600L, initialSize = 16, maximumSize = 32))
    public List<AppConfVo> getReleasedConfList(String appNo) {
        List<AppConfPo> confList = appInfoMapper.getAppConfListByApp(appNo, ConfState.USING);
        if (CollectionUtils.isEmpty(confList)) {
            return Collections.emptyList();
        }
        return confList.stream().map(conf -> {
            AppConfVo vo = new AppConfVo();
            vo.setKey(conf.getConfKey());
            vo.setValue(conf.getConfVal());
            vo.setType(conf.getType().value());
            return vo;
        }).collect(Collectors.toList());
    }

}
