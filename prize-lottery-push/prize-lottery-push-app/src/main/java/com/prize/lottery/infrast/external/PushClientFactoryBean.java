package com.prize.lottery.infrast.external;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.prize.lottery.infrast.props.NotificationApiProps;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class PushClientFactoryBean implements FactoryBean<IAcsClient>, DisposableBean {

    private final IAcsClient client;

    public PushClientFactoryBean(NotificationApiProps props) {
        DefaultProfile profile = DefaultProfile.getProfile(props.getRegionId(), props.getAccessKey(), props.getAccessSecret());
        this.client = new DefaultAcsClient(profile);
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public IAcsClient getObject() throws Exception {
        return this.client;
    }

    @Override
    public Class<?> getObjectType() {
        return client.getClass();
    }

    @Override
    public void destroy() throws Exception {
        client.shutdown();
    }

}
