package com.prize.lottery.application.command;


import com.prize.lottery.application.command.dto.BannerCreateCmd;
import com.prize.lottery.application.command.dto.BannerModifyCmd;

public interface IAppBannerCommandService {

    void createBanner(BannerCreateCmd cmd);

    void editBanner(BannerModifyCmd cmd);

}
