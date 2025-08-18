package com.prize.lottery.application.query.executor;

import com.cloud.arch.cache.annotations.CacheResult;
import com.cloud.arch.cache.annotations.Local;
import com.cloud.arch.cache.annotations.Remote;
import com.prize.lottery.application.assembler.AppInfoAssembler;
import com.prize.lottery.application.vo.AppCommentVo;
import com.prize.lottery.infrast.persist.mapper.AppInfoMapper;
import com.prize.lottery.infrast.persist.po.AppCommentPo;
import com.prize.lottery.infrast.repository.impl.AppCommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class Html5CommentQueryExecutor {

    private final AppInfoMapper    mapper;
    private final AppInfoAssembler assembler;

    @CacheResult(
            names = AppCommentRepository.COMMENT_CACHE_NAME,
            key = "#appNo",
            enableLocal = true,
            remote = @Remote(expire = 12 * 3600L, randomBound = 3600),
            local = @Local(expire = 8 * 3600L, initialSize = 8, maximumSize = 32))
    public List<AppCommentVo> execute(String appNo) {
        List<AppCommentPo> comments = mapper.getLatestAppComments(appNo);
        if (CollectionUtils.isEmpty(comments)) {
            return Collections.emptyList();
        }
        return assembler.toVoList(comments);
    }

}
