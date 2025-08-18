package com.prize.lottery.domain.share;

import lombok.Data;

import java.util.Objects;

@Data
public class MasterId {

    private String masterId;

    public MasterId(String masterId) {
        this.masterId = masterId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return masterId.equals(((MasterId) o).masterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(masterId);
    }
}
