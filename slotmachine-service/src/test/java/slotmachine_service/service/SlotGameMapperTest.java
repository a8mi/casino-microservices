package slotmachine_service.service;

import org.junit.jupiter.api.Test;
import slotmachine_service.api.GameResponse;
import slotmachine_service.model.SlotGame;
import slotmachine_service.model.SlotSymbol;

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
                List.of(SlotSymbol.CHERRY, SlotSymbol.LEMON, SlotSymbol.ORANGE),
                Instant.parse("2026-07-12T10:00:00Z")
        );

        GameResponse response = new SlotGameMapper().toResponse(game);

        assertThat(response.user()).isEqualTo(5L);
        assertThat(response.winning()).isFalse();
        assertThat(response.amount()).isEqualByComparingTo("-1.00");
        assertThat(response.slot_states()).containsExactly("CHERRY", "LEMON", "ORANGE");
    }
}
