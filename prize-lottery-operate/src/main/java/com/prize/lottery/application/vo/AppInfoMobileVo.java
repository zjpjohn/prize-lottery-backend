package com.prize.lottery.application.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppInfoMobileVo {

    /**
     * 应用信息
     */
    private AppInfo     appInfo;
    /**
     * 当前版本信息
     */
    private AppVersion  current;
    /**
     * 主推版本信息
     */
    private MainVersion main;

    @Data
    public static class AppInfo {
        /**
         * 应用标识
         */
        private String seqNo;
        /**
         * 应用名称
         */
        private String name;
        /**
         * 应用logo
         */
        private String logo;
        /**
         * 应用分享链接
         */
        private String shareUri;
        /**
         * 应用版权信息
         */
        private String copyright;
        /**
         * 应用公司地址
         */
        private String corp;
        /**
         * 联系方式
         */
        private String telephone;
        /**
         * 公司地址
         */
        private String address;
        /**
         * 公司备案信息
         */
        private String record;
    }

    @Data
    public static class AppVersion {

        /**
         * 应用版本信息
         */
        private String  version;
        /**
         * 应用版本对应的描述信息
         */
        private String  depiction;
        /**
         * 当前版本是否下线
         */
        private boolean offline = false;
    }

    @Data
    public static class MainVersion {
        /**
         * 版本信息
         */
        private String              version;
        /**
         * 主推版本是否强制升级
         */
        private Integer             force;
        /**
         * 全量包下载地址
         */
        private String              apkUri;
        /**
         * abi分架构下载地址
         */
        private Map<String, String> abiApks;
        /**
         * 应用升级信息
         */
        private List<String>        upgrades;
    }

}
