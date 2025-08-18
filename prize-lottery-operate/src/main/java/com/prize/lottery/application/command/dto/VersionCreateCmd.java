package com.prize.lottery.application.command.dto;

import com.cloud.arch.web.validation.annotation.Enumerable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class VersionCreateCmd {

    /**
     * 应用标识
     */
    @NotNull(message = "应用标识为空")
    private String       appNo;
    /**
     * 版本标识
     */
    @NotBlank(message = "版本信息为空")
    private String       appVer;
    /**
     * 应用下载地址
     */
    @NotBlank(message = "应用存放文件夹为空")
    private String       appDir;
    /**
     * 全量包名称
     */
    @NotBlank(message = "全量包名称为空")
    private String       appUnity;
    /**
     * armv7包名称
     */
    @NotBlank(message = "armv7包名称为空")
    private String       appV7a;
    /**
     * armv8包名称为空
     */
    @NotBlank(message = "armv8包名称为空")
    private String       appV8a;
    /**
     * 版本是否强制升级
     */
    @NotNull(message = "升级标识为空")
    @Enumerable(ranges = {"0", "1"}, message = "升级标识错误")
    private Integer      force;
    /**
     * 应用描述信息
     */
    @NotBlank(message = "应用描述信息")
    private String       depiction;
    /**
     * 版本升级描述信息
     */
    @NotEmpty(message = "升级描述为空")
    @Size(min = 1, message = "至少要有一项说明")
    private List<String> upgrades;
    /**
     * 应用照片集合
     */
    @NotEmpty(message = "应用图片为空")
    @Size(min = 1, message = "至少要有一张图片")
    private List<String> images;

}
