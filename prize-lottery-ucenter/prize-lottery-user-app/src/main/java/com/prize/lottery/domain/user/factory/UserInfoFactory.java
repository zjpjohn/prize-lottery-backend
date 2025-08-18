package com.prize.lottery.domain.user.factory;

import com.prize.lottery.application.command.dto.BaseUserAuthCmd;
import com.prize.lottery.domain.user.model.UserInfo;
import com.prize.lottery.domain.user.model.UserInvite;
import com.prize.lottery.infrast.persist.enums.RegisterChannel;
import com.prize.lottery.infrast.props.CloudLotteryProperties;
import com.prize.lottery.infrast.utils.InvCodeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Random;

@Slf4j
@Component
public class UserInfoFactory {

    //字符串集合
    public static final String CHAR_CONTAINER = "2AaRr3BbKkSs4CcLTt5DdMmUu6EeNnVv7FPWw8GQXx9HhZz";
    public static final String AVATAR_PATTERN = "https://image.icaiwa.com/avatar/face%d.png";

    @Value("${cloud.user.name-prefix:'幸运星'}")
    private String                 namePrefix;
    @Resource
    private CloudLotteryProperties properties;

    public UserInfo create(BaseUserAuthCmd cmd) {
        UserInfo userInfo = new UserInfo(cmd.getPhone(), cmd.getChannel(), 0);
        Random   random   = new Random();
        int      index    = random.nextInt(3590) + 1;
        userInfo.setAvatar(String.format(AVATAR_PATTERN, index));
        userInfo.setNickname(genNickname(namePrefix));
        return userInfo;
    }

    /**
     * 创建邀请账户
     *
     * @param userId 用户标识
     */
    public UserInvite createInvite(Long userId) {
        String code          = InvCodeGenerator.generate();
        String userInviteUri = String.format(properties.getUri(), RegisterChannel.USER_INVITE.getChannel(), code);
        return new UserInvite(userId, code, userInviteUri);
    }

    /**
     * 前缀名生成用户昵称
     *
     * @param prefix 昵称前缀
     */
    String genNickname(String prefix) {
        Random        random  = new Random();
        int           length  = CHAR_CONTAINER.length();
        StringBuilder builder = new StringBuilder(prefix);
        for (int i = 0; i < 5; i++) {
            builder.append(CHAR_CONTAINER.charAt(random.nextInt(length)));
        }
        return builder.toString();
    }

}
