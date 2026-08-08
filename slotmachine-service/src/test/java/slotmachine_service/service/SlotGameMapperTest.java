package slotmachine_service.service;

import org.junit.jupiter.api.Test;

import slotmachine_service.Model.SlotGame;
import slotmachine_service.View.GameView;
import slotmachine_service.Model.ESlotSymbol;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SlotGameMapperTest {

    @Test
    void mapsDomainEntityToAssignmentResponseShape() {
        SlotGame game = new SlotGame(
                5L,
                new BigDecimal("1.00"),
                BigDecimal.ZERO.setScale(2),
                new BigDecimal("-1.00"),
                List.of(ESlotSymbol.CHERRY, ESlotSymbol.LEMON, ESlotSymbol.ORANGE),
                Instant.parse("2026-07-12T10:00:00Z")
        );

        GameView response = GameView.of(game);

        assertThat(response.getUser()).isEqualTo(5L);
        assertThat(response.getWinning()).isFalse();
        assertThat(response.getAmount()).isEqualByComparingTo("-1.00");
        assertThat(response.getSlotStates()).containsExactly("CHERRY", "LEMON", "ORANGE");
    }
}
