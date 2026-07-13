package slotmachine_service.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class SlotGameTest {

    @Test
    void createsWinningGameWithConsistentMoneyAndSymbols() {
        SlotGame game = new SlotGame(
                7L,
                new BigDecimal("2.00"),
                new BigDecimal("22.00"),
                new BigDecimal("20.00"),
                List.of(SlotSymbol.CHERRY, SlotSymbol.CHERRY, SlotSymbol.CHERRY),
                Instant.parse("2026-07-12T10:00:00Z")
        );

        assertThat(game.getUserId()).isEqualTo(7L);
        assertThat(game.isWinning()).isTrue();
        assertThat(game.getSymbols()).containsExactly(
                SlotSymbol.CHERRY,
                SlotSymbol.CHERRY,
                SlotSymbol.CHERRY
        );
    }

    @Test
    void rejectsInconsistentNetAmount() {
        assertThatThrownBy(() -> new SlotGame(
                1L,
                new BigDecimal("2.00"),
                BigDecimal.ZERO.setScale(2),
                new BigDecimal("-1.00"),
                List.of(SlotSymbol.CHERRY, SlotSymbol.LEMON, SlotSymbol.BELL),
                Instant.now()
        )).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("payout minus bet");
    }


    @Test
    void rejectsPayoutThatDoesNotMatchSymbols() {
        assertThatThrownBy(() -> new SlotGame(
                1L,
                new BigDecimal("2.00"),
                new BigDecimal("22.00"),
                new BigDecimal("20.00"),
                List.of(SlotSymbol.CHERRY, SlotSymbol.LEMON, SlotSymbol.ORANGE),
                Instant.now()
        )).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("payout table");
    }

    @Test
    void rejectsWrongNumberOfReels() {
        assertThatThrownBy(() -> new SlotGame(
                1L,
                new BigDecimal("2.00"),
                BigDecimal.ZERO.setScale(2),
                new BigDecimal("-2.00"),
                List.of(SlotSymbol.CHERRY, SlotSymbol.LEMON),
                Instant.now()
        )).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("three");
    }
}
