package com.prize.lottery.application.query;


import com.cloud.arch.page.Page;
import com.prize.lottery.application.query.dto.CommentListQuery;
import com.prize.lottery.infrast.persist.po.AppCommentPo;

public interface IAppCommentQueryService {

    AppCommentPo appComment(Long id);

    Page<AppCommentPo> appCommentList(CommentListQuery query);
}
