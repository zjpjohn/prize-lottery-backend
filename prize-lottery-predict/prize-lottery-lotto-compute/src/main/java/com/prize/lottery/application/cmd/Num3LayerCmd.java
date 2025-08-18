package com.prize.lottery.application.cmd;

import com.cloud.arch.utils.CollectionUtils;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.value.LayerValue;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class Num3LayerCmd {

    @NotBlank(message = "过滤期号为空")
    private String      period;
    @NotNull(message = "过滤类型为空")
    private LotteryEnum type;
    @Valid
    @NotNull(message = "过滤层1为空")
    private LayerInfo   layer1;
    @Valid
    @NotNull(message = "过滤层2为空")
    private LayerInfo   layer2;
    @Valid
    @NotNull(message = "过滤层2为空")
    private LayerInfo   layer3;
    @Valid
    private LayerInfo   layer4;
    @Valid
    private LayerInfo   layer5;

    @Data
    public static class LayerInfo {

        @NotBlank(message = "过滤层名称为空")
        private String        name;
        //过滤条件
        @NotEmpty(message = "过滤条件为空")
        private List<Integer> condition;
        //组三过滤结果
        private List<String>  zu3 = Collections.emptyList();
        //组六过滤结果
        private List<String>  zu6 = Collections.emptyList();

        public LayerValue toValue() {
            return new LayerValue(this.name, this.condition, this.zu6, this.zu3);
        }

        public boolean isEmpty() {
            return CollectionUtils.isEmpty(this.zu6) && CollectionUtils.isEmpty(this.zu6);
        }

        public boolean isNotEmpty() {
            return CollectionUtils.isNotEmpty(this.zu6) || CollectionUtils.isNotEmpty(this.zu6);
        }
    }

}
