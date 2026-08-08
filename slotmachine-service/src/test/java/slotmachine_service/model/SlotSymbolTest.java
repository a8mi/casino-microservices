package slotmachine_service.model;

import org.junit.jupiter.api.Test;

import slotmachine_service.Model.ESlotSymbol;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class SlotSymbolTest {

    @Test
    void weightsFormOneHundredPercentDistribution() {
        int total = Arrays.stream(ESlotSymbol.values()).mapToInt(ESlotSymbol::weight).sum();

        assertThat(total).isEqualTo(100);
    }

    @Test
    void everyWinPaysMoreThanTheStake() {
        assertThat(Arrays.stream(ESlotSymbol.values())
                .map(ESlotSymbol::payoutMultiplier))
                .allMatch(multiplier -> multiplier.compareTo(BigDecimal.ONE) > 0);
    }
}
