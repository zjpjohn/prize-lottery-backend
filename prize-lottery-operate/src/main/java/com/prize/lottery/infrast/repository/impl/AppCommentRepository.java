package com.prize.lottery.infrast.repository.impl;

import com.alibaba.fastjson2.JSON;
import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.cache.support.CacheEvictEvent;
import com.cloud.arch.cache.support.CacheEvictPublisher;
import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Lists;
import com.prize.lottery.domain.app.model.AppCommentDo;
import com.prize.lottery.domain.app.repository.IAppCommentRepository;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.mapper.AppInfoMapper;
import com.prize.lottery.infrast.persist.po.AppCommentPo;
import com.prize.lottery.infrast.repository.converter.AppInfoConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AppCommentRepository implements IAppCommentRepository {

    public static final String COMMENT_CACHE_NAME = "h5:comment:app";

    private final AppInfoMapper    mapper;
    private final AppInfoConverter converter;

    @Override
    public void save(Aggregate<Long, AppCommentDo> aggregate) {
        AppCommentDo root = aggregate.getRoot();
        if (root.isNew()) {
            AppCommentPo comment = converter.toPo(root);
            mapper.addAppComments(Lists.newArrayList(comment));
            CacheEvictPublisher.publish(new CacheEvictEvent(root.getAppNo(), COMMENT_CACHE_NAME));
            return;
        }
        AppCommentDo changed = aggregate.changed();
        if (changed != null) {
            AppCommentPo commentPo = converter.toPo(changed);
            mapper.editAppComment(commentPo);
            CacheEvictPublisher.publish(new CacheEvictEvent(root.getAppNo(), COMMENT_CACHE_NAME));
        }
    }

    @Override
    public Aggregate<Long, AppCommentDo> ofId(Long id) {
        return Optional.ofNullable(mapper.getAppComment(id))
                       .map(converter::toDo)
                       .map(AggregateFactory::create)
                       .orElseThrow(ResponseHandler.APP_COMMENT_NONE);
    }

    @Override
    public void saveJsonList(String appNo, String json) {
        List<AppCommentPo> comments = null;
        try {
            comments = JSON.parseArray(json, AppCommentPo.class);
        } catch (Exception error) {
            log.error(error.getMessage(), error);
            throw Assert.cast(ResponseHandler.COMMENT_JSON_MALFORMED);
        }
        Assert.state(!CollectionUtils.isEmpty(comments), ResponseHandler.COMMENT_LIST_EMPTY);
        comments.forEach(cmt -> cmt.setAppNo(appNo));
        mapper.addAppComments(comments);
        //淘汰应用评论缓存
        CacheEvictPublisher.publish(new CacheEvictEvent(appNo, COMMENT_CACHE_NAME));
    }

}
