package com.prize.lottery.infrast.persist.po;

import com.prize.lottery.infrast.persist.enums.VersionState;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class AppVersionPo {

    private Long          id;
    /* 应用标识 */
    private String        appNo;
    /* 应用版本 */
    private String        appVer;
    /* 应用文件存放文件夹 */
    private String        appDir;
    /* 全量包名 */
    private String        appUnity;
    /* abi_v7a包名集合 */
    private String        appV7a;
    /* abi_v8a包名集合 */
    private String        appV8a;
    /* 版本是否强制升级 */
    private Integer       force;
    /* 应用描述信息 */
    private String        depiction;
    /* 版本升级内容列表 */
    private List<String>  upgrades;
    /* 应用版本图片集合 */
    private List<String>  images;
    /* 版本状态:0-下线,1-预发，2-上线,3-主推 */
    private VersionState  state;
    /* 应用上线时间 */
    private LocalDateTime online;
    /* 创建时间 */
    private LocalDateTime gmtCreate;
    /* 更新时间 */
    private LocalDateTime gmtModify;

    public String v8aDownloadUrl() {
        return this.appDir.trim() + this.appV8a.trim();
    }

    public String v7aDownloadUrl() {
        return this.appDir.trim() + this.appV7a.trim();
    }

    public String unityDownloadUrl() {
        return this.appDir.trim() + this.appUnity.trim();
    }

}
