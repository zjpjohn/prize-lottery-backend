package com.prize.lottery.infrast.repository.impl;

import com.prize.lottery.domain.user.model.UserLogin;
import com.prize.lottery.domain.user.repository.IUserLoginRepository;
import com.prize.lottery.infrast.persist.mapper.AppLaunchMapper;
import com.prize.lottery.infrast.persist.mapper.UserInfoMapper;
import com.prize.lottery.infrast.persist.po.UserDevicePo;
import com.prize.lottery.infrast.persist.po.UserLoginLogPo;
import com.prize.lottery.infrast.persist.po.UserLoginPo;
import com.prize.lottery.infrast.repository.converter.UserLoginConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserLoginRepository implements IUserLoginRepository {

    private final UserInfoMapper     userInfoMapper;
    private final AppLaunchMapper    appLaunchMapper;
    private final UserLoginConverter converter;

    @Override
    public void save(UserLogin login) {
        //保存本次登录信息
        userInfoMapper.addUserLogin(converter.to(login));
        if (login.getState() == 1) {
            //只保存登录日志，退出登录日志不保存
            UserLoginLogPo loginLog = converter.toLog(login);
            userInfoMapper.addUserLoginLog(loginLog);
            //登录时绑定用户设备关系
            UserDevicePo userDevice = converter.toBind(login);
            appLaunchMapper.addUserDevice(userDevice);
        }
    }

    @Override
    public UserLogin ofId(Long userId) {
        UserLoginPo userLogin = userInfoMapper.getUserLogin(userId);
        return Optional.ofNullable(userLogin).map(converter::from).orElseGet(() -> new UserLogin(userId));
    }

    @Override
    public boolean existDevice(String deviceId) {
        return appLaunchMapper.existAppDevice(deviceId) == 1;
    }

    @Override
    public boolean hasBindDevice(String deviceId) {
        return appLaunchMapper.hasBindDevice(deviceId) == 1;
    }
}
