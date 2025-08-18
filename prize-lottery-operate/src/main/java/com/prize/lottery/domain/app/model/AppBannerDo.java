package com.prize.lottery.domain.app.model;

import com.cloud.arch.aggregate.AggregateRoot;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.dto.BannerCreateCmd;
import com.prize.lottery.application.command.dto.BannerModifyCmd;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.CommonState;
import com.prize.lottery.infrast.persist.valobj.AppBanner;
import com.prize.lottery.infrast.persist.valobj.BannerSpecs;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class AppBannerDo implements AggregateRoot<Long> {

    private static final long serialVersionUID = 707513605201562151L;

    private Long        id;
    private String      appNo;
    private String      page;
    private AppBanner   banner;
    private CommonState state;
    private String      remark;

    public AppBannerDo(BannerCreateCmd cmd) {
        appNo  = cmd.getAppNo();
        page   = cmd.getPage();
        banner = new AppBanner(cmd.getItems());
        remark = cmd.getRemark();
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }

    /**
     * 更新应用页面banner视图
     */
    public void modify(BannerModifyCmd command) {
        CommonState newState = command.getState();
        if (newState == null) {
            Assert.state(this.canModify(), ResponseHandler.DATA_STATE_ILLEGAL);
            List<BannerSpecs> bannerSpecs = command.getItems();
            if (!CollectionUtils.isEmpty(bannerSpecs)) {
                this.banner = new AppBanner(bannerSpecs);
            }
            if (StringUtils.hasText(command.getRemark())) {
                this.remark = command.getRemark();
            }
            return;
        }
        Set<CommonState> transitions = this.state.transitions();
        Assert.state(transitions.contains(newState), ResponseHandler.DATA_STATE_ILLEGAL);
    }

    public boolean canModify() {
        return !state.equals(CommonState.USING);
    }

}
