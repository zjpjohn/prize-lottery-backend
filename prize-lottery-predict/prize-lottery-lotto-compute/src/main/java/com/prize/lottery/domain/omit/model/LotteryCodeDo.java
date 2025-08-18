package com.prize.lottery.domain.omit.model;

import com.google.common.collect.Lists;
import com.prize.lottery.enums.CodeType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.utils.LottoCodeUtil;
import com.prize.lottery.value.CodePosition;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class LotteryCodeDo {

    private Long        id;
    private LotteryEnum lotto;
    private String       period;
    private CodeType     type;
    private CodePosition position;

    public LotteryCodeDo(LotteryEnum lotto, String period, CodeType type, CodePosition position) {
        this.lotto    = lotto;
        this.period   = period;
        this.type     = type;
        this.position = position;
    }

    public static List<LotteryCodeDo> fc3d(String period, String lotto) {
        return Lists.newArrayList(fc3dFour(period, lotto), fc3dFive(period, lotto));
    }

    /**
     * 3D万能4码计算
     *
     * @param period 开奖期号
     * @param lotto  开奖号码
     */
    public static LotteryCodeDo fc3dFour(String period, String lotto) {
        List<Pair<Integer, String>> pairs        = LottoCodeUtil.fourParse(lotto);
        List<Integer>               positions    = pairs.stream().map(Pair::getKey).collect(Collectors.toList());
        CodePosition                codePosition = new CodePosition(positions);
        return new LotteryCodeDo(LotteryEnum.FC3D, period, CodeType.FOUR, codePosition);
    }

    /**
     * 3D万能5码计算
     *
     * @param period 开奖期号
     * @param lotto  开奖号码
     */
    public static LotteryCodeDo fc3dFive(String period, String lotto) {
        List<Pair<Integer, String>> pairs        = LottoCodeUtil.fiveParse(lotto);
        List<Integer>               positions    = pairs.stream().map(Pair::getKey).collect(Collectors.toList());
        CodePosition                codePosition = new CodePosition(positions);
        return new LotteryCodeDo(LotteryEnum.FC3D, period, CodeType.FIVE, codePosition);
    }

    public static List<LotteryCodeDo> pl3(String period, String lotto) {
        return Lists.newArrayList(pl3Four(period, lotto), pl3Five(period, lotto));
    }

    /**
     * 排三万能4码计算
     *
     * @param period 开奖期号
     * @param lotto  开奖号码
     */

    public static LotteryCodeDo pl3Four(String period, String lotto) {
        List<Pair<Integer, String>> pairs        = LottoCodeUtil.fourParse(lotto);
        List<Integer>               positions    = pairs.stream().map(Pair::getKey).collect(Collectors.toList());
        CodePosition                codePosition = new CodePosition(positions);
        return new LotteryCodeDo(LotteryEnum.PL3, period, CodeType.FOUR, codePosition);
    }

    /**
     * 排三万能5码计算
     *
     * @param period 开奖期号
     * @param lotto  开奖号码
     */
    public static LotteryCodeDo pl3Five(String period, String lotto) {
        List<Pair<Integer, String>> pairs        = LottoCodeUtil.fiveParse(lotto);
        List<Integer>               positions    = pairs.stream().map(Pair::getKey).collect(Collectors.toList());
        CodePosition                codePosition = new CodePosition(positions);
        return new LotteryCodeDo(LotteryEnum.PL3, period, CodeType.FIVE, codePosition);
    }

}
