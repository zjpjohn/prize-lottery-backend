package com.prize.lottery.domain.app.model;

import com.cloud.arch.aggregate.AggregateRoot;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;
import java.util.function.Function;

import static com.prize.lottery.infrast.persist.po.FeedbackTypePo.DEFAULT_VERSION;


@Data
@NoArgsConstructor
public class FeedbackTypeDo implements AggregateRoot<Long> {

    private static final long serialVersionUID = 8294924099239609804L;

    private Long    id;
    private String  appNo;
    private String  type;
    private String  suitVer;
    private String  remark;
    private Integer sort;

    public FeedbackTypeDo(String appNo, String type, String suitVer, String remark, Integer sort) {
        this.appNo   = appNo;
        this.type    = type;
        this.remark  = remark;
        this.sort    = sort;
        this.suitVer = Optional.ofNullable(suitVer).filter(StringUtils::isNotBlank).orElse(DEFAULT_VERSION);
    }

    public void modify(String type, String suitVer, String remark) {
        if (StringUtils.isNotBlank(type)) {
            this.type = type;
        }
        if (StringUtils.isNotBlank(suitVer)) {
            this.suitVer = suitVer;
        }
        if (StringUtils.isNotBlank(remark)) {
            this.remark = remark;
        }
    }

    public void sort(Integer position, Function<String, Integer> maxSortFunc) {
        Integer maxSort = maxSortFunc.apply(this.appNo);
        Assert.state(position > 0 && position <= maxSort, ResponseHandler.SORT_INDEX_ERROR);
        this.sort = position;
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }

}
