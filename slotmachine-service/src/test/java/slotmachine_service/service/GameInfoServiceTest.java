package slotmachine_service.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GameInfoServiceTest {

    private final GameInfoService service = new GameInfoService();

    @Test
    void rulesExplainNetAmountAndHistoryDeletion() {
        assertThat(service.rules())
                .contains("payout minus bet")
                .contains("does not reverse");
    }

    @Test
    void chancesContainFormulaAndCalculatedRtp() {
        assertThat(service.chances())
                .contains("P(three identical symbols)")
                .contains("Theoretical RTP: 91.0845%")
                .contains("Theoretical house edge: 8.9155%");
    }
}
