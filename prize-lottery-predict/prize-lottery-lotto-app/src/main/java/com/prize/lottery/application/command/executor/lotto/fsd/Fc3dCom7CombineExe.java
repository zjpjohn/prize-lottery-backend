package com.prize.lottery.application.command.executor.lotto.fsd;

import com.prize.lottery.application.command.dto.ComCombineCalcCmd;
import com.prize.lottery.application.command.executor.CombineExecutor;
import com.prize.lottery.enums.Fc3dChannel;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.utils.Com7CombineTask;
import com.prize.lottery.mapper.Fc3dIcaiMapper;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.po.fc3d.Fc3dIcaiPo;
import com.prize.lottery.value.ForecastValue;
import com.prize.lottery.vo.fc3d.Fc3dIcaiDataVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class Fc3dCom7CombineExe implements CombineExecutor {

    private final Fc3dIcaiMapper    mapper;
    private final LotteryInfoMapper lotteryMapper;

    public List<Pair<String, Long>> execute(ComCombineCalcCmd cmd) {
        String               last     = LotteryEnum.FC3D.lastPeriod(cmd.getPeriod());
        List<Fc3dIcaiDataVo> dataList = mapper.getFc3dRankedDataList(Fc3dChannel.COM7.getChannel(), cmd.getPeriod(), last, 20);
        return dataList.stream()
                       .map(Fc3dIcaiPo::getCom7)
                       .map(ForecastValue::getData)
                       .map(forecast -> new Com7CombineTask(forecast, cmd.getDanList(), cmd.getKillList()))
                       .map(Com7CombineTask::execute)
                       .flatMap(List::stream)
                       .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                       .entrySet()
                       .stream()
                       .filter(entry -> entry.getValue() >= cmd.getMinSize() && entry.getValue() <= cmd.getMaxSize())
                       .filter(entry -> filterKua(cmd.getKuaList(), entry.getKey()))
                       .filter(entry -> filterSum(cmd.getSumList(), entry.getKey()))
                       .filter(entry -> filterDan(entry.getKey(), cmd.getWensDan()))
                       .filter(entry -> filterDan(entry.getKey(), cmd.getWeekDan()))
                       .filter(entry -> filterDan(entry.getKey(), cmd.getShiDan()))
                       .filter(entry -> filterDan(entry.getKey(), cmd.getTenDan()))
                       .filter(entry -> filterTwoMa(entry.getKey(), cmd.getTwoMa()))
                       .sorted((o1, o2) -> (int) (o2.getValue() - o1.getValue()))
                       .map(item -> Pair.of(item.getKey(), item.getValue()))
                       .collect(Collectors.toList());
    }

}
