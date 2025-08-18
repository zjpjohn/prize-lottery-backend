package com.prize.lottery.infrast.persist.valobj;

import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import com.cloud.arch.web.utils.Assert;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@TypeHandler(type = Type.JSON)
public class ResourceSpecs {

    @NotNull(message = "资源宽度为空")
    @DecimalMin(value = "0.0", message = "宽度应大于0")
    private double width;
    @NotNull(message = "资源高度为空")
    @DecimalMin(value = "0.0", message = "高度应大于0")
    private double height;

    public ResourceSpecs(double width, double height) {
        Assert.state(this.width > 0, "宽度应大于0");
        Assert.state(this.height > 0, "高度应大于0");
        this.width  = width;
        this.height = height;
    }

    public static ResourceSpecs of(double width, double height) {
        return new ResourceSpecs(width, height);
    }

}
