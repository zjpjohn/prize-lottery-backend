package com.prize.lottery.application.query.impl;

import com.cloud.arch.page.Page;
import com.prize.lottery.application.query.IAppCommentQueryService;
import com.prize.lottery.application.query.dto.CommentListQuery;
import com.prize.lottery.infrast.persist.mapper.AppInfoMapper;
import com.prize.lottery.infrast.persist.po.AppCommentPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppCommentQueryService implements IAppCommentQueryService {

    private final AppInfoMapper appInfoMapper;

    @Override
    public AppCommentPo appComment(Long id) {
        return appInfoMapper.getAppComment(id);
    }

    @Override
    public Page<AppCommentPo> appCommentList(CommentListQuery query) {
        return query.from().count(appInfoMapper::countAppComments).query(appInfoMapper::getAppCommentList);
    }

}
