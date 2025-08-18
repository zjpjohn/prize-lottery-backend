package com.prize.lottery.infrast.persist.valobj;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Objects;

@Data
public class BannerSpecs {

    @NotBlank(message = "banner图片标识不允许为空")
    private String image;
    @NotBlank(message = "banner跳转路由不允许为空")
    private String route;
    //banner描述信息
    private String remark;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BannerSpecs that = (BannerSpecs) o;
        return Objects.equals(image, that.image) && Objects.equals(route, that.route);
    }

    @Override
    public int hashCode() {
        return Objects.hash(image, route);
    }

}
