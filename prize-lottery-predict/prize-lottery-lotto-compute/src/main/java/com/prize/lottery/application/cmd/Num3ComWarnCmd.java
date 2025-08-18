package com.prize.lottery.application.cmd;

import com.cloud.arch.utils.CollectionUtils;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.share.valobj.ComWarnValue;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class Num3ComWarnCmd {

    @NotBlank(message = "期号为空")
    private String        period;
    @NotNull(message = "彩票类型为空")
    private LotteryEnum   type;
    @NotEmpty(message = "跨度为空")
    private List<Integer> kuaList;
    private List<String>  danList;
    private List<String>  twoMaList;
    private List<Integer> sumList;
    private List<String>  killList;
    private List<String>  zu6List;
    private List<String>  zu3List;

    public ComWarnValue checkAndBuild() {
        Assert.state(CollectionUtils.isNotEmpty(zu3List)
                             || CollectionUtils.isNotEmpty(zu6List), ResponseHandler.COM_WARN_ERROR);
        return new ComWarnValue(danList, killList, twoMaList, zu6List, zu3List, kuaList, sumList);
    }

}
