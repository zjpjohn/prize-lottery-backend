package com.prize.lottery.domain.lottery.value;

public class LevelValue {

    private final Integer level;
    private final Integer extra;
    private final Integer quantity;
    private final double  bonus;
    private final double  amount;


    public LevelValue(Integer level, Integer quantity, double amount) {
        this(level, 0, quantity, 0, amount);
    }

    public LevelValue(Integer level, Integer extra, Integer quantity, double bonus, double amount) {
        this.level    = level;
        this.extra    = extra;
        this.quantity = quantity;
        this.bonus    = bonus;
        this.amount   = amount;
    }

    public Integer getLevel() {
        return level;
    }

    public Integer getExtra() {
        return extra;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public double getBonus() {
        return bonus;
    }

    public double getAmount() {
        return amount;
    }
}
