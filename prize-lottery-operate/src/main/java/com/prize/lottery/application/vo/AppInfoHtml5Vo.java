package com.prize.lottery.application.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppInfoHtml5Vo {

    /**
     * 应用信息
     */
    private AppInfo    appInfo;
    /**
     * 应用主推版本
     */
    private AppVersion version;

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
         * openInstall分享key
         */
        private String openInstall;
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
        /**
         * 应用描述信息
         */
        private String remark;
    }

    @Data
    public static class AppVersion {
        /**
         * 应用版本
         */
        private String        version;
        /**
         * 应用下载地址
         */
        private String        apkUri;
        /**
         * 上线时间
         */
        @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
        private LocalDateTime online;
        /**
         * 应用升级信息
         */
        private List<String>  upgrades;
        /**
         * 应用描述图片
         */
        private List<String>  images;
    }

}
