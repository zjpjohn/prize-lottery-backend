package com.prize.lottery.infrast.external.push;

import com.alibaba.fastjson2.JSON;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.push.model.v20160801.BindTagRequest;
import com.prize.lottery.infrast.external.push.request.TagBasedBindDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TagBasedBindFacade {

    private final IAcsClient client;

    /**
     * 设备绑定标签
     *
     * @param data 绑定信息
     */
    public Boolean execute(TagBasedBindDto data) {
        try {
            log.info("设备绑定标签信息:{}", JSON.toJSONString(data));
            BindTagRequest request = new BindTagRequest();
            request.setAppKey(data.getAppKey());
            request.setKeyType("DEVICE");
            request.setTagName(data.getTagName());
            request.setClientKey(String.join(",", data.getDevices()));
            client.getAcsResponse(request);
            return Boolean.TRUE;
        } catch (ClientException error) {
            log.error(error.getMessage(), error);
        }
        return Boolean.FALSE;
    }

}
