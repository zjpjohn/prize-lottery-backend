package com.prize.lottery.application.command.dto;

import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.infrast.persist.enums.CommonState;
import com.prize.lottery.infrast.persist.enums.NotifyNotice;
import com.prize.lottery.infrast.persist.enums.OpenType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Range;


@Data
public class NotifyInfoModifyCmd {

    @NotNull(message = "唯一标识为空")
    private Long         id;
    @Range(min = 0, max = 23, message = "推送时间错误")
    private Integer      hour;
    @NotBlank(message = "推送标题为空")
    private String       title;
    @NotBlank(message = "推送内容为空")
    private String       body;
    @Range(min = 1, max = 100, message = "通知栏样式错误")
    private Integer      barType;
    @Range(min = -2, max = 2, message = "通知排序错误")
    private Integer      barPriority;
    @Enumerable(ranges = {"1", "2"}, message = "推送在线标识错误")
    private Integer      online;
    private CommonState  state;
    private NotifyNotice notice;
    private String       openUrl;
    private String       openActivity;
    private String       channel;
    private String       extParams;
    private String       remark;

    /**
     * 打开方式校验
     */
    public void checkOpenType(OpenType openType) {
        if (StringUtils.isBlank(this.openUrl) && StringUtils.isBlank(this.openActivity)) {
            return;
        }
        if (openType.equals(OpenType.ACTIVITY)) {
            this.openUrl = "";
            return;
        }
        if (openType.equals(OpenType.URL)) {
            this.openActivity = "";
        }
    }
}
