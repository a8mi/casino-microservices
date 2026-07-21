package slotmachine_service.model;

import java.math.BigDecimal;

public enum SlotSymbol {
    CHERRY(30, 11),
    LEMON(25, 15),
    ORANGE(20, 21),
    BELL(12, 50),
    BAR(8, 110),
    SEVEN(5, 550);

    private final int weight;
    private final BigDecimal payoutMultiplier;

    SlotSymbol(int weight, int payoutMultiplier) {
        if (weight <= 0) {
            throw new IllegalArgumentException("weight must be positive");
        }
        if (payoutMultiplier <= 1) {
            throw new IllegalArgumentException("payout multiplier must be greater than one");
        }
        this.weight = weight;
        this.payoutMultiplier = BigDecimal.valueOf(payoutMultiplier);
    }

    public int weight() {
        return weight;
    }

    public BigDecimal payoutMultiplier() {
        return payoutMultiplier;
    }
}
