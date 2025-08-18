package com.prize.lottery.application.command.dto;

import com.cloud.arch.web.validation.annotation.Enumerable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class VersionModifyCmd {

    /**
     * 应用版本信息
     */
    @NotNull(message = "版本唯一标识为空")
    private Long         id;
    /**
     * 版本是否强制升级
     */
    @Enumerable(ranges = {"0", "1"}, message = "升级标识错误")
    private Integer      force;
    /**
     * 应用下载地址
     */
    private String       appDir;
    /**
     * 全量包名称
     */
    private String       appUnity;
    /**
     * armv7包名称
     */
    private String       appV7a;
    /**
     * armv8包名称为空
     */
    private String       appV8a;
    /**
     * 应用描述信息
     */
    private String       depiction;
    /**
     * 应用升级描述信息
     */
    private List<String> upgrades;
    /**
     * 应用照片集合
     */
    private List<String> images;

}
