package slotmachine_service.game;

import org.junit.jupiter.api.Test;
import slotmachine_service.model.SlotSymbol;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

class PayoutPolicyRandomizedTest {

    private final PayoutPolicy policy = new PayoutPolicy();

    @Test
    void randomizedValidBetsPreserveMoneyInvariant() {
        Random random = new Random(42);

        for (int iteration = 0; iteration < 500; iteration++) {
            BigDecimal bet = BigDecimal.valueOf(random.nextInt(100_000) + 1, 2);
            SlotSymbol first = randomSymbol(random);
            SlotSymbol second = randomSymbol(random);
            SlotSymbol third = randomSymbol(random);
            List<SlotSymbol> symbols = List.of(first, second, third);

            BigDecimal payout = policy.calculatePayout(bet, symbols);
            BigDecimal amount = policy.calculateNetAmount(bet, payout);

            assertThat(amount.add(bet)).isEqualByComparingTo(payout);
            assertThat(payout.scale()).isEqualTo(2);

            if (first == second && second == third) {
                assertThat(payout).isEqualByComparingTo(
                        bet.multiply(first.payoutMultiplier()).setScale(2, RoundingMode.UNNECESSARY)
                );
            } else {
                assertThat(payout).isEqualByComparingTo("0.00");
            }
        }
    }

    private static SlotSymbol randomSymbol(Random random) {
        SlotSymbol[] symbols = SlotSymbol.values();
        return symbols[random.nextInt(symbols.length)];
    }
}
