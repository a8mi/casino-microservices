package slotmachine_service.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class SlotSymbolTest {

    @Test
    void weightsFormOneHundredPercentDistribution() {
        int total = Arrays.stream(SlotSymbol.values()).mapToInt(SlotSymbol::weight).sum();

        assertThat(total).isEqualTo(100);
    }

    @Test
    void everyWinPaysMoreThanTheStake() {
        assertThat(Arrays.stream(SlotSymbol.values())
                .map(SlotSymbol::payoutMultiplier))
                .allMatch(multiplier -> multiplier.compareTo(BigDecimal.ONE) > 0);
    }
}
