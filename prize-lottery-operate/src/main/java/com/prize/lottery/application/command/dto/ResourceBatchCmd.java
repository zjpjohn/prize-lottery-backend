package com.prize.lottery.application.command.dto;

import com.cloud.arch.enums.Value;
import com.cloud.arch.web.utils.Assert;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.domain.app.valobj.AppResourceVal;
import com.prize.lottery.infrast.persist.enums.ResourceType;
import com.prize.lottery.infrast.persist.valobj.ResourceSpecs;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Data
public class ResourceBatchCmd {

    @NotBlank(message = "应用标识为空")
    private String             appNo;
    @Valid
    @NotEmpty(message = "应用资源集合为空")
    private List<ResourceItem> resources;

    @Data
    public static class ResourceItem {

        //资源名称
        @NotBlank(message = "资源名称为空")
        private String        name;
        //资源标识
        @NotBlank(message = "资源标识为空")
        private String        feNo;
        //资源类型
        @NotNull(message = "资源类型为空")
        @Enumerable(enums = ResourceType.class, message = "资源类型错误")
        private Integer       type;
        //资源链接
        @NotBlank(message = "资源链接为空")
        private String        uri;
        //默认使用链接
        @NotBlank(message = "默认链接为空")
        private String        defUri;
        //资源规格
        @Valid
        @NotNull(message = "资源规格为空")
        private ResourceSpecs specs;
        //资源说明可选
        private String        remark;

        public AppResourceVal convert(String appNo) {
            Assert.state(StringUtils.isNotBlank(appNo), "应用标识为空");
            ResourceType   resourceType = Assert.notNull(Value.valueOf(type, ResourceType.class), "资源类型为空");
            AppResourceVal resource     = new AppResourceVal();
            resource.setAppNo(appNo);
            resource.setName(name);
            resource.setFeNo(feNo);
            resource.setUri(uri);
            resource.setDefUri(defUri);
            resource.setSpecs(specs);
            resource.setType(resourceType);
            return resource;
        }

    }

}
