package com.prize.lottery.domain.value;

import java.util.Objects;

public class MasterIdentity {

    private String period;
    private String masterId;

    public MasterIdentity(String period, String masterId) {
        this.period   = period;
        this.masterId = masterId;
    }

    public String getPeriod() {
        return period;
    }

    public String getMasterId() {
        return masterId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MasterIdentity that = (MasterIdentity) o;
        return Objects.equals(period, that.period) &&
                Objects.equals(masterId, that.masterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(period, masterId);
    }
}
