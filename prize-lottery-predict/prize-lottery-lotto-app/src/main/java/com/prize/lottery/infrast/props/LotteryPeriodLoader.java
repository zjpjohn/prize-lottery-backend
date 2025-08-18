package com.prize.lottery.infrast.props;

import com.cloud.arch.utils.CollectionUtils;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.prize.lottery.utils.PeriodCalculator;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
@Configuration
@EnableConfigurationProperties(LotteryPeriodLoader.LotteryPeriodProps.class)
public class LotteryPeriodLoader implements SmartInitializingSingleton {

    @Resource
    private LotteryPeriodProps props;

    @Override
    public void afterSingletonsInstantiated() {
        this.buildPeriodTable();
    }

    @EventListener
    public void changeListener(EnvironmentChangeEvent event) {
        this.buildPeriodTable();
    }

    private void buildPeriodTable() {
        Table<String, String, String> table = this.props.build();
        PeriodCalculator.loadMax(table);
    }

    @Data
    @ConfigurationProperties(prefix = "cloud.lottery")
    public static class LotteryPeriodProps implements DisposableBean {

        private Map<String, List<YearMaxPeriod>> periods;

        @Override
        public void destroy() throws Exception {
            this.periods = null;
        }

        public Table<String, String, String> build() {
            HashBasedTable<String, String, String> table = HashBasedTable.create();
            if (CollectionUtils.isNotEmpty(this.periods)) {
                periods.forEach((type, list) -> list.forEach(period -> table.put(type, period.getYear(), period.getMaxPeriod())));
            }
            return table;
        }

        @Data
        public static class YearMaxPeriod {

            private String year;
            private String maxPeriod;

        }
    }

}
