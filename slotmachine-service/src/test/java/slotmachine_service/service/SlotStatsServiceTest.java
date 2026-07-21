package slotmachine_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slotmachine_service.api.OverallStatsResponse;
import slotmachine_service.api.UserStatsResponse;
import slotmachine_service.client.BankingClient;
import slotmachine_service.exception.GameNotFoundException;
import slotmachine_service.model.SlotGame;
import slotmachine_service.model.SlotSymbol;
import slotmachine_service.repository.SlotGameRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class SlotStatsServiceTest {

    private SlotGameRepository repository;
    private BankingClient bankingClient;
    private SlotStatsService service;

    @BeforeEach
    void setUp() {
        repository = mock(SlotGameRepository.class);
        bankingClient = mock(BankingClient.class);
        service = new SlotStatsService(repository, bankingClient, new SlotGameMapper());
    }

    @Test
    void aggregatesHouseAndClientStatistics() {
        SlotGame loss = game(1L, "2.00", "0.00", "-2.00");
        SlotGame win = game(2L, "1.00", "11.00", "10.00");
        when(repository.findAllByOrderByIdAsc()).thenReturn(List.of(loss, win));

        OverallStatsResponse stats = service.getOverallStats();

        assertThat(stats.total_client_count()).isEqualTo(2);
        assertThat(stats.total_games_count()).isEqualTo(2);
        assertThat(stats.total_profit()).isEqualByComparingTo("-8.00");
        assertThat(stats.total_cash_out()).isEqualByComparingTo("11.00");
        assertThat(stats.total_turnover()).isEqualByComparingTo("3.00");
    }

    @Test
    void returnsZeroHistoryForExistingBankingUser() {
        when(repository.findByUserIdOrderByIdAsc(9L)).thenReturn(List.of());
        when(bankingClient.getUser(9L))
                .thenReturn(new BankingClient.UserAccount(9L, BigDecimal.ZERO));

        UserStatsResponse stats = service.getUserStats(9L);

        assertThat(stats.total_games_count()).isZero();
        assertThat(stats.total_client_profit()).isEqualByComparingTo("0.00");
        verify(bankingClient).getUser(9L);
    }

    @Test
    void missingGameProducesNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getGame(99L))
                .isInstanceOf(GameNotFoundException.class);
    }

    private static SlotGame game(Long userId, String bet, String payout, String amount) {
        return new SlotGame(
                userId,
                new BigDecimal(bet),
                new BigDecimal(payout),
                new BigDecimal(amount),
                payout.equals("0.00")
                        ? List.of(SlotSymbol.CHERRY, SlotSymbol.LEMON, SlotSymbol.ORANGE)
                        : List.of(SlotSymbol.CHERRY, SlotSymbol.CHERRY, SlotSymbol.CHERRY),
                Instant.parse("2026-07-12T10:00:00Z")
        );
    }
}
