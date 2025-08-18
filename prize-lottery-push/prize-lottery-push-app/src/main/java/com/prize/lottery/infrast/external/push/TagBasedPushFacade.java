package com.prize.lottery.infrast.external.push;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.push.model.v20160801.PushRequest;
import com.aliyuncs.push.model.v20160801.PushResponse;
import com.prize.lottery.infrast.external.push.request.TagBasedPushDto;
import com.prize.lottery.infrast.persist.enums.OpenType;
import com.prize.lottery.infrast.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TagBasedPushFacade {

    private final IAcsClient client;

    /**
     * 返回推送的消息标识
     *
     * @param data 推送内容
     */
    public String execute(TagBasedPushDto data) {
        try {
            PushRequest request = new PushRequest();
            request.setAppKey(data.getAppKey());
            //基于标签推送
            request.setTarget("TAG");
            //推送全部设备类型
            request.setDeviceType("ALL");
            //推送标签集合
            request.setTargetValue(data.getTags());
            //推送类型
            request.setPushType(data.getPushType());
            //消息标题
            request.setTitle(data.getTitle());
            //消息内容
            request.setBody(data.getBody());
            //是否离线存储，下次上线时提醒
            request.setStoreOffline(data.getOnline());
            //通知提醒方式
            request.setAndroidNotifyType(data.getNotifyType());
            //通知扩展参数(必须为json map格式)
            request.setAndroidExtParameters(data.getExtParams());
            //通知栏样式，取值1-100
            request.setAndroidNotificationBarType(data.getBarType());
            //通知栏展示排序优先级：-2、-1、0、1、2
            request.setAndroidNotificationBarPriority(data.getBarPriority());
            //延后推送指定推送时间
            request.setPushTime(DateUtils.formatUtc(data.getPushTime()));
            //通知/消息打开类型
            OpenType openType = data.getOpenType();
            request.setAndroidOpenType(openType.toString());
            if (openType.equals(OpenType.URL)) {
                //打开url时配置跳转url
                request.setAndroidOpenUrl(data.getUrl());
            } else if (openType.equals(OpenType.ACTIVITY)) {
                //打开activity时配置跳转activity
                request.setAndroidActivity(data.getActivity());
            }
            //配置通知渠道，解决Android 8.0以上需设置，否则不会跳出显示
            Optional.ofNullable(data.getNotifyChannel())
                    .filter(StringUtils::isNotBlank)
                    .ifPresent(request::setAndroidNotificationChannel);
            PushResponse response = client.getAcsResponse(request);
            return response.getMessageId();
        } catch (Exception error) {
            log.error(error.getMessage(), error);
        }
        return null;
    }
}
