package com.prize.lottery.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.po.master.AvatarInfoPo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvatarInfoMapper {

    int addAvatarList(List<AvatarInfoPo> avatars);

    int editAvatarInfo(AvatarInfoPo avatar);

    int removeAvatarInfo(Long id);

    AvatarInfoPo getAvatarInfoById(Long id);

    List<String> getAvatarImages();

    List<String> getRandomAvatars(Integer limit);

    List<AvatarInfoPo> getAvatarList(PageCondition condition);

    int countAvatarList(PageCondition condition);
}
