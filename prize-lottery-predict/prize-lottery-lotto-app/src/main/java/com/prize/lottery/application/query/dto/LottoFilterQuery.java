package com.prize.lottery.application.query.dto;

import com.cloud.arch.utils.CollectionUtils;
import com.cloud.arch.web.error.ApiBizException;
import com.prize.lottery.enums.LotteryEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class LottoFilterQuery {

    @NotNull(message = "彩种类型错误")
    private LotteryEnum   type;
    @Positive(message = "查询期数大于0")
    private Integer       limit;
    private List<String>  danList;
    private List<Integer> kuaList;
    private List<Integer> sumList;

    public void validate() {
        if (CollectionUtils.isEmpty(danList) && CollectionUtils.isEmpty(kuaList) && CollectionUtils.isEmpty(sumList)) {
            throw new ApiBizException(HttpStatus.BAD_REQUEST, 400, "胆码、跨度及和值不允许同时为空");
        }
    }

}
