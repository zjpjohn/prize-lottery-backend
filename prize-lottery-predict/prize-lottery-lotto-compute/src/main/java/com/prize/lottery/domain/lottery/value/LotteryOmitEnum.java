package com.prize.lottery.domain.lottery.value;

import com.cloud.arch.utils.JsonUtils;
import com.cloud.arch.web.error.ApiBizException;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.po.lottery.LotteryInfoPo;
import com.prize.lottery.po.lottery.LotteryOmitPo;
import com.prize.lottery.value.Omit;
import com.prize.lottery.value.OmitValue;
import lombok.Getter;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Getter
public enum LotteryOmitEnum {

    FC3D_OM(LotteryEnum.FC3D) {
        @Override
        public LotteryOmitPo calcOmit(LotteryOmitPo omit, LotteryInfoPo lottery) {
            LotteryOmitPo omitPo = new LotteryOmitPo(this.getLottery());
            omitPo.setPeriod(lottery.getPeriod());
            //号码遗漏
            List<String> balls = getBalls(lottery.getRed());
            omitPo.setCb1(omit(omit.getCb1(), Lists.newArrayList(balls.get(0))));
            omitPo.setCb2(omit(omit.getCb2(), Lists.newArrayList(balls.get(1))));
            omitPo.setCb3(omit(omit.getCb3(), Lists.newArrayList(balls.get(2))));

            //号码分布遗漏状态:组六、组三、豹子
            HashSet<String> sets     = Sets.newHashSet(balls);
            List<OmitValue> newExtra = Lists.newArrayList();
            Omit            oldExtra = omit.getExtra();
            newExtra.add(OmitValue.of("3", sets.size() == 3 ? 0 : oldExtra.getValues().get(0).getValue() + 1));
            newExtra.add(OmitValue.of("2", sets.size() == 2 ? 0 : oldExtra.getValues().get(1).getValue() + 1));
            newExtra.add(OmitValue.of("1", sets.size() == 1 ? 0 : oldExtra.getValues().get(2).getValue() + 1));
            omitPo.setExtra(new Omit(newExtra));
            return omitPo;
        }

    },
    PL3_OM(LotteryEnum.PL3) {
        @Override
        public LotteryOmitPo calcOmit(LotteryOmitPo omit, LotteryInfoPo lottery) {
            LotteryOmitPo omitPo = new LotteryOmitPo(this.getLottery());
            omitPo.setPeriod(lottery.getPeriod());
            //号码遗漏
            List<String> balls = getBalls(lottery.getRed());
            omitPo.setCb1(omit(omit.getCb1(), Lists.newArrayList(balls.get(0))));
            omitPo.setCb2(omit(omit.getCb2(), Lists.newArrayList(balls.get(1))));
            omitPo.setCb3(omit(omit.getCb3(), Lists.newArrayList(balls.get(2))));

            //号码分布遗漏状态:组六、组三
            HashSet<String> sets     = Sets.newHashSet(balls);
            List<OmitValue> newExtra = Lists.newArrayList();
            Omit            oldExtra = omit.getExtra();
            newExtra.add(OmitValue.of("6", sets.size() == 3 ? 0 : oldExtra.getValues().get(0).getValue() + 1));
            newExtra.add(OmitValue.of("3", sets.size() <= 2 ? 0 : oldExtra.getValues().get(1).getValue() + 1));
            omitPo.setExtra(new Omit(newExtra));
            return omitPo;
        }

    },
    SSQ_OM(LotteryEnum.SSQ) {
        @Override
        public LotteryOmitPo calcOmit(LotteryOmitPo omit, LotteryInfoPo lottery) {
            LotteryOmitPo omitPo = new LotteryOmitPo(this.getLottery());
            omitPo.setPeriod(lottery.getPeriod());
            omitPo.setRed(omit(omit.getRed(), getBalls(lottery.getRed())));
            omitPo.setBlue(omit(omit.getBlue(), getBalls(lottery.getBlue())));
            return omitPo;
        }

    },
    DLT_OM(LotteryEnum.DLT) {
        @Override
        public LotteryOmitPo calcOmit(LotteryOmitPo omit, LotteryInfoPo lottery) {
            LotteryOmitPo omitPo = new LotteryOmitPo(this.getLottery());
            omitPo.setPeriod(lottery.getPeriod());
            omitPo.setRed(omit(omit.getRed(), getBalls(lottery.getRed())));
            omitPo.setBlue(omit(omit.getBlue(), getBalls(lottery.getBlue())));
            return omitPo;
        }

    },
    QLC_OM(LotteryEnum.QLC) {
        @Override
        public LotteryOmitPo calcOmit(LotteryOmitPo omit, LotteryInfoPo lottery) {
            //红球号码遗漏
            Omit          redOmit = omit(omit.getRed(), getBalls(lottery.getRed()));
            LotteryOmitPo omitPo  = new LotteryOmitPo(this.getLottery());
            omitPo.setRed(redOmit);
            omitPo.setPeriod(lottery.getPeriod());
            return omitPo;
        }

    },
    KL8_OM(LotteryEnum.KL8) {
        @Override
        public LotteryOmitPo calcOmit(LotteryOmitPo omit, LotteryInfoPo lottery) {
            Omit          redOmit     = omit(omit.getRed(), getBalls(lottery.getRed()));
            LotteryOmitPo lotteryOmit = new LotteryOmitPo(this.getLottery());
            lotteryOmit.setRed(redOmit);
            lotteryOmit.setPeriod(lottery.getPeriod());
            return lotteryOmit;
        }

    };

    private final LotteryEnum lottery;

    public static final String INIT_OMIT_PATH_PATTERN = "lottery/omits/%s_init_omit.json";

    LotteryOmitEnum(LotteryEnum lottery) {
        this.lottery = lottery;
    }

    public abstract LotteryOmitPo calcOmit(LotteryOmitPo omit, LotteryInfoPo lottery);

    public LotteryOmitPo loadInit() {
        String initOmitPath = String.format(INIT_OMIT_PATH_PATTERN, lottery.getType());
        URL    resource     = this.getClass().getClassLoader().getResource(initOmitPath);
        if (resource == null) {
            return null;
        }
        return JsonUtils.readValue(new File(resource.getPath()), LotteryOmitPo.class);

    }

    public static LotteryOmitEnum findOf(LotteryEnum lottery) {
        return Arrays.stream(values())
                     .filter(v -> v.lottery == lottery)
                     .findFirst()
                     .orElseThrow(() -> new ApiBizException(404, "彩票类型不存在"));
    }

    /**
     * 分割号码
     *
     * @param source 号码字符串
     */
    public static List<String> getBalls(String source) {
        String regex = source.contains(",") ? "," : "\\s+";
        return Splitter.on(Pattern.compile(regex)).omitEmptyStrings().trimResults().splitToList(source);
    }

    public Omit omit(Omit omit, List<String> balls) {
        List<OmitValue> values = omit.getValues().stream().map(o -> {
            int value = balls.contains(o.getKey()) ? 0 : o.getValue() + 1;
            return OmitValue.of(o.getKey(), value);
        }).collect(Collectors.toList());
        return new Omit(values);
    }

}
