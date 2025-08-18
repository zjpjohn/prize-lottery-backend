package com.prize.lottery.application.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class AppHtml5ShareVo extends AppInfoHtml5Vo {

    private List<AppCommentVo> comments;

    public AppHtml5ShareVo(AppInfoHtml5Vo appInfo, List<AppCommentVo> comments) {
        super(appInfo.getAppInfo(), appInfo.getVersion());
        this.comments = comments;
    }

}
