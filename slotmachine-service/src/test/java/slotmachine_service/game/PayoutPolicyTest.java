package slotmachine_service.game;

import org.junit.jupiter.api.Test;

import slotmachine_service.GameLogic.PayoutPolicy;
import slotmachine_service.Model.ESlotSymbol;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class PayoutPolicyTest {

    private final PayoutPolicy policy = new PayoutPolicy();

    @Test
    void paysConfiguredMultiplierForThreeEqualSymbols() {
        BigDecimal payout = policy.calculatePayout(
                new BigDecimal("2.00"),
                List.of(ESlotSymbol.CHERRY, ESlotSymbol.CHERRY, ESlotSymbol.CHERRY)
        );

        assertThat(payout).isEqualByComparingTo("22.00");
        assertThat(policy.calculateNetAmount(new BigDecimal("2.00"), payout))
                .isEqualByComparingTo("20.00");
    }

    @Test
    void losesStakeForMixedSymbols() {
        BigDecimal payout = policy.calculatePayout(
                new BigDecimal("2.00"),
                List.of(ESlotSymbol.CHERRY, ESlotSymbol.LEMON, ESlotSymbol.CHERRY)
        );

        assertThat(payout).isEqualByComparingTo("0.00");
        assertThat(policy.calculateNetAmount(new BigDecimal("2.00"), payout))
                .isEqualByComparingTo("-2.00");
    }

    @Test
    void rejectsFractionalCents() {
        assertThatThrownBy(() -> policy.calculatePayout(
                new BigDecimal("1.001"),
                List.of(ESlotSymbol.CHERRY, ESlotSymbol.CHERRY, ESlotSymbol.CHERRY)
        )).isInstanceOf(IllegalArgumentException.class);
    }
}
