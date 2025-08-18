package com.prize.lottery.application.service.impl;

import com.cloud.arch.oss.props.OssCloudProperties;
import com.cloud.arch.utils.IdWorker;
import com.cloud.arch.web.error.ApiBizException;
import com.cloud.arch.web.utils.Assert;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.prize.lottery.application.service.IMasterInfoService;
import com.prize.lottery.domain.feeds.ability.MasterFeedsDomainAbility;
import com.prize.lottery.domain.glad.ability.MasterGladDomainAbility;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.enums.MasterChannel;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.infrast.spider.open.OpenMasterFetcher;
import com.prize.lottery.mapper.AvatarInfoMapper;
import com.prize.lottery.mapper.MasterInfoMapper;
import com.prize.lottery.po.master.AvatarInfoPo;
import com.prize.lottery.po.master.MasterInfoPo;
import com.prize.lottery.po.master.MasterLotteryPo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
public class MasterInfoService implements IMasterInfoService {

    public static final String NAMES_DATA_PATH = "classpath:lottery/names/qq_name.db";

    @Resource
    private OssCloudProperties       ossProperties;
    @Resource
    private MasterInfoMapper         masterInfoMapper;
    @Resource
    private AvatarInfoMapper         avatarInfoMapper;
    @Resource
    private OpenMasterFetcher        openMasterFetcher;
    @Resource
    private MasterGladDomainAbility  masterGladDomainAbility;
    @Resource
    private MasterFeedsDomainAbility masterFeedsDomainAbility;

    /**
     * 提取专家预测喜讯
     */
    @Override
    public void extractGlads() {
        masterGladDomainAbility.extract();
    }

    /**
     * 删除指定天前的喜讯
     */
    @Override
    public void removeGlads(Integer day) {
        masterGladDomainAbility.removeGlads(day);
    }

    /**
     * 提取专家信息流
     */
    @Override
    public void extractMasterFeeds() {
        masterFeedsDomainAbility.extract();
    }

    /**
     * 清除专家信息流
     *
     * @param type 彩种类型
     * @param rate 清除低于的命中率
     */
    @Override
    public void removeMasterFeed(LotteryEnum type, Double rate) {
        masterFeedsDomainAbility.clearFeeds(type, rate);
    }

    /**
     * 导入系统头像
     */
    @Override
    public void importAvatars() {
        List<AvatarInfoPo> avatars = IntStream.range(1, 3592).mapToObj(index -> {
            String key    = "avatar/face" + index + ".png";
            String avatar = ossProperties.getDomainUri() + "/" + key;
            return new AvatarInfoPo(key, avatar);
        }).collect(Collectors.toList());
        avatarInfoMapper.addAvatarList(avatars);
    }

    @Override
    public void removeAvatar(Long id) {
        avatarInfoMapper.removeAvatarInfo(id);
    }

    @Override
    @Transactional(rollbackFor = ApiBizException.class)
    public void importMasters(LotteryEnum type) {
        try {
            //加载平台专家
            List<String> masterIds = openMasterFetcher.loadMasters(type);
            //查询全部已存在专家
            List<MasterInfoPo>        existMasters = masterInfoMapper.getExistMasters(masterIds);
            Map<String, MasterInfoPo> groupMasters = Maps.uniqueIndex(existMasters, MasterInfoPo::getSeq);
            //计算平台增量专家
            List<String> existTypeMasters = masterInfoMapper.getExistTypeMasters(type, masterIds);
            List<String> incrMasterIds    = masterIds.stream().filter(id -> !existTypeMasters.contains(id)).toList();
            if (CollectionUtils.isEmpty(incrMasterIds)) {
                return;
            }

            File         file     = ResourceUtils.getFile(NAMES_DATA_PATH);
            List<String> qqNames  = Files.readLines(file, Charsets.UTF_8);
            List<String> images   = avatarInfoMapper.getAvatarImages();
            int          nameSize = qqNames.size(), imageSize = images.size();

            List<MasterInfoPo>    newMasterList  = Lists.newArrayList();
            List<MasterInfoPo>    editMasterList = Lists.newArrayList();
            List<MasterLotteryPo> typeMasters    = Lists.newArrayList();
            for (String masterId : incrMasterIds) {
                MasterInfoPo master = groupMasters.get(masterId);
                if (master == null) {
                    String seq = String.valueOf(IdWorker.nextId());
                    master = new MasterInfoPo();
                    master.setSeq(seq);
                    master.setSourceId(masterId);
                    master.setSource(MasterChannel.ICAI.getChannel());
                    int hashCode = Integer.parseInt(masterId);
                    master.setName(qqNames.get(hashCode % nameSize));
                    master.setAvatar(images.get(hashCode % imageSize));
                    master.setState(2);
                    master.setEnable(type.getChannel());
                    newMasterList.add(master);
                } else {
                    MasterInfoPo editMaster = new MasterInfoPo();
                    editMaster.setId(master.getId());
                    editMaster.setEnable((byte) (master.getEnable() | type.getChannel()));
                    editMasterList.add(editMaster);
                }
                MasterLotteryPo masterLottery = new MasterLotteryPo();
                masterLottery.setType(type.getType());
                masterLottery.setSource(MasterChannel.ICAI.getChannel());
                masterLottery.setMasterId(master.getSeq());
                masterLottery.setSourceId(masterId);
                masterLottery.setLevel(1);
                typeMasters.add(masterLottery);
            }
            masterInfoMapper.addMasterLotteries(typeMasters);
            if (!CollectionUtils.isEmpty(newMasterList)) {
                masterInfoMapper.addMasterList(newMasterList);
            }
            if (!CollectionUtils.isEmpty(editMasterList)) {
                masterInfoMapper.editMasterInfoList(editMasterList);
            }
        } catch (Exception e) {
            log.error("导入平台[{}]专家异常:", type.getNameZh(), e);
            throw Assert.cast(ResponseHandler.IMPORT_NAME_ERROR);
        }
    }

    /**
     * 重置专家名称
     */
    @Override
    public void resetMasterNames() {
        try {
            File         nameFile = ResourceUtils.getFile(NAMES_DATA_PATH);
            List<String> qqNames  = Files.readLines(nameFile, Charsets.UTF_8);
            qqNames = qqNames.stream().map(String::trim).distinct().collect(Collectors.toList());
            int                nameSize = qqNames.size();
            List<MasterInfoPo> masters  = masterInfoMapper.getAllICaiMasters();
            for (MasterInfoPo master : masters) {
                MasterInfoPo masterInfo = new MasterInfoPo();
                masterInfo.setId(master.getId());
                int sourceId = Integer.parseInt(master.getSourceId());
                masterInfo.setName(qqNames.get(sourceId % nameSize));
                masterInfoMapper.editMasterInfo(master);
            }
        } catch (Exception error) {
            log.error(error.getMessage(), error);
            throw Assert.cast(ResponseHandler.RESET_NAME_ERROR);
        }
    }

    /**
     * 重置专家头像
     */
    @Override
    public void resetMasterAvatars() {
        try {
            List<String> images = avatarInfoMapper.getAvatarImages();
            Assert.state(!CollectionUtils.isEmpty(images), ResponseHandler.NO_AVATAR_LIST);
            List<MasterInfoPo> masters   = masterInfoMapper.getAllICaiMasters();
            int                imageSize = images.size();
            for (MasterInfoPo master : masters) {
                MasterInfoPo masterInfo = new MasterInfoPo();
                masterInfo.setId(master.getId());
                int sourceId = Integer.parseInt(master.getSourceId());
                masterInfo.setAvatar(images.get(sourceId % imageSize));
                masterInfoMapper.editMasterInfo(masterInfo);
            }
        } catch (Exception error) {
            log.error(error.getMessage(), error);
            throw Assert.cast(ResponseHandler.RESET_AVATAR_ERROR);
        }
    }

}
