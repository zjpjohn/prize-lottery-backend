package com.prize.lottery.domain.fc3d.model;

import com.cloud.arch.aggregate.AggregateRoot;
import com.cloud.arch.utils.CollectionUtils;
import com.cloud.arch.utils.IdWorker;
import com.prize.lottery.utils.ICaiConstants;
import com.prize.lottery.value.ForecastValue;
import com.prize.lottery.value.PivotMaster;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
public class Fc3dPivotDo implements AggregateRoot<Long> {

    private static final long serialVersionUID = 8141483156094690521L;

    private Long          id;
    //预测期号
    private String        period;
    //第一胆码
    private ForecastValue best;
    //第二胆码
    private ForecastValue second;
    //优质号码
    private ForecastValue quality;
    //推荐专家
    private PivotMaster   master;
    //编辑次数
    private Integer       edits;
    //计算时间
    private LocalDateTime calcTime;
    //数据版本号
    private Integer       version;

    public Fc3dPivotDo(String period,
                       String best,
                       String second,
                       String quality,
                       List<String> masterIds,
                       List<String> balls) {
        this.id      = IdWorker.nextId();
        this.version = 0;
        this.period  = period;
        this.best    = this.calcHit(new ForecastValue(best), balls);
        this.second  = this.calcHit(new ForecastValue(second), balls);
        this.quality = this.calcHit(new ForecastValue(quality), balls);
        this.master  = new PivotMaster(masterIds);
        if (this.best.getHitData() != null) {
            this.calcTime = LocalDateTime.now();
        }
    }

    public void editPivot(String best, String second, String quality, List<String> masterIds, List<String> balls) {
        this.edits   = this.edits + 1;
        this.master  = new PivotMaster(masterIds);
        this.best    = this.calcHit(new ForecastValue(best), balls);
        this.second  = this.calcHit(new ForecastValue(second), balls);
        this.quality = this.calcHit(new ForecastValue(quality), balls);
        if (this.best.getHitData() != null) {
            this.calcTime = LocalDateTime.now();
        }
    }

    public void calcPivotHit(List<String> balls) {
        this.calcTime = LocalDateTime.now();
        this.best     = this.calcHit(this.best, balls);
        this.second   = this.calcHit(this.second, balls);
        this.quality  = this.calcHit(this.quality, balls);
    }

    private ForecastValue calcHit(ForecastValue value, List<String> balls) {
        if (CollectionUtils.isEmpty(balls)) {
            return value;
        }
        int                             hit     = 0;
        String                          data    = value.getData();
        Map<String, Integer>            map     = ICaiConstants.judgeLottery(balls);
        Set<Map.Entry<String, Integer>> entries = map.entrySet();
        for (Map.Entry<String, Integer> entry : entries) {
            if (data.contains(entry.getKey())) {
                hit  = hit + entry.getValue();
                data = data.replaceAll(entry.getKey(), "[" + entry.getKey() + "]");
            }
        }
        return value.forecastHit(data, Math.min(hit, data.length()));
    }

    @Override
    public boolean isNew() {
        return this.version == 0;
    }
}
