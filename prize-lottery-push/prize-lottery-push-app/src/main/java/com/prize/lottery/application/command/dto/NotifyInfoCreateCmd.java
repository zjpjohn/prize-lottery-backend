package com.prize.lottery.application.command.dto;

import com.cloud.arch.web.utils.Assert;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.infrast.error.ResponseErrorHandler;
import com.prize.lottery.infrast.persist.enums.NotifyNotice;
import com.prize.lottery.infrast.persist.enums.OpenType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Range;


@Data
public class NotifyInfoCreateCmd {

    @NotNull(message = "分组标识为空")
    private Long         groupId;
    @NotNull(message = "推送类型为空")
    @Enumerable(ranges = {"1", "2"}, message = "推送类型错误")
    private Integer      type;
    @NotNull(message = "推送时间为空")
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
    @NotNull(message = "提醒方式为空")
    private NotifyNotice notice;
    @NotNull(message = "打开方式为空")
    private OpenType     openType;
    private String       openUrl;
    private String       openActivity;
    private String       channel;
    private String       extParams;
    private String       remark;

    /**
     * 参数校验
     */
    public void checkValidate() {
        if (OpenType.ACTIVITY.equals(this.openType)) {
            Assert.state(StringUtils.isNotBlank(openActivity), ResponseErrorHandler.OPEN_ACTIVITY_NONE);
            openUrl = "";
            return;
        }
        if (OpenType.URL.equals(this.openType)) {
            Assert.state(StringUtils.isNotBlank(openUrl), ResponseErrorHandler.OPEN_URL_NONE);
            openActivity = "";
        }
    }
}
