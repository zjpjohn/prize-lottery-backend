package com.prize.lottery.application.service.impl;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.cmd.Num3LayerCmd;
import com.prize.lottery.application.service.INum3LayerService;
import com.prize.lottery.domain.share.model.Num3LayerFilterDo;
import com.prize.lottery.domain.share.repository.INum3LayerRepository;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.infrast.spider.open.OpenLayerFetcher;
import com.prize.lottery.infrast.spider.open.OpenNum3Layer;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.po.lottery.LotteryInfoPo;
import com.prize.lottery.utils.ICaiConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class Num3LayerService implements INum3LayerService {

    private final LotteryInfoMapper    mapper;
    private final INum3LayerRepository repository;
    private final OpenLayerFetcher     openLayerFetcher;

    @Override
    @Transactional
    public void createNum3Layer(Num3LayerCmd command) {
        Num3LayerFilterDo layerDo = repository.ofUk(command.getPeriod(), command.getType())
                                              .map(v -> v.modify(command))
                                              .orElseGet(() -> new Num3LayerFilterDo(command));
        Assert.state(!layerDo.isEmpty(), ResponseHandler.LAYER_FILTER_EMPTY);
        LotteryInfoPo lottery = mapper.getLotteryInfo(command.getType().value(), command.getPeriod());
        Optional.ofNullable(lottery)
                .map(LotteryInfoPo::redBalls)
                .map(ICaiConstants::judgeLottery)
                .ifPresent(layerDo::calcHit);
        repository.save(layerDo);
    }

    @Override
    @Transactional
    public void fetchNum3Layer(LotteryEnum type) {
        String        period    = mapper.latestPeriod(type.getType());
        String        latest    = type.nextPeriod(period);
        OpenNum3Layer openLayer = openLayerFetcher.fetch(latest, type);
        if (openLayer != null) {
            this.createOpenLayer(openLayer, type);
        }
    }

    private void createOpenLayer(OpenNum3Layer layer, LotteryEnum type) {
        Num3LayerFilterDo layerDo = repository.ofUk(layer.getPeriod(), type)
                                              .map(v -> v.modify(layer))
                                              .orElseGet(() -> new Num3LayerFilterDo(layer, type));
        LotteryInfoPo lottery = mapper.getLotteryInfo(type.value(), layer.getPeriod());
        Optional.ofNullable(lottery)
                .map(LotteryInfoPo::redBalls)
                .map(ICaiConstants::judgeLottery)
                .ifPresent(layerDo::calcHit);
        repository.save(layerDo);
    }

    @Override
    @Transactional
    public void syncNum3Layer(LotteryEnum type) {
        Assert.state(type == LotteryEnum.FC3D || type == LotteryEnum.PL3, ResponseHandler.LOTTO_TYPE_ERROR);
        String        period    = mapper.latestPeriod(type.getType());
        String        latest    = type.nextPeriod(period);
        OpenNum3Layer openLayer = openLayerFetcher.fetch(latest, type);
        Assert.notNull(openLayer, ResponseHandler.LAYER_FILTER_EMPTY);
        this.createOpenLayer(openLayer, type);
    }

    @Override
    @Transactional
    public void calcLayerHit(String period, LotteryEnum type) {
        repository.ofUk(period, type).ifPresent(this::calcHit);
    }

    @Override
    @Transactional
    public void calcLayerHit(Long id) {
        repository.ofId(id).ifPresent(this::calcHit);
    }

    private void calcHit(Num3LayerFilterDo layer) {
        LotteryInfoPo lottery = mapper.getLotteryInfo(layer.getType().value(), layer.getPeriod());
        Optional.ofNullable(lottery).map(LotteryInfoPo::redBalls).map(ICaiConstants::judgeLottery).ifPresent(v -> {
            layer.calcHit(v);
            repository.save(layer);
        });
    }

}
