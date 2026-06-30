package banking_service.Handler.Stats;

import banking_service.Model.Transactions.Transaction;
import banking_service.Model.User.User;
import banking_service.Repository.Transactions.TransactionRepository;
import banking_service.Repository.User.IUserRepository;
import banking_service.View.Stats.StatsResponse;
import banking_service.View.Stats.UserStatsResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatsHandlerTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private StatsHandler statsHandler;

    @Test
    void testGetGlobalStats() {
        // 1. User anlegen
        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setBalance(BigDecimal.ZERO);

        // 2. Transaktionen anlegen
        Transaction transaction1 = new Transaction();
        transaction1.setId(1L);
        transaction1.setUserId(1L);
        transaction1.setInvoicingParty("roulette");
        transaction1.setAmount(new BigDecimal("100.00"));

        Transaction transaction2 = new Transaction();
        transaction2.setId(2L);
        transaction2.setUserId(1L);
        transaction2.setInvoicingParty("slotmachine");
        transaction2.setAmount(new BigDecimal("-50.00"));

        // 3. Mocks konfigurieren
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(transactionRepository.findAll()).thenReturn(List.of(transaction1, transaction2));

        // 4. Test ausführen
        StatsResponse response = statsHandler.getGlobalStats();

        // 5. Assertions
        assertEquals(1, response.getTotalUsers());
        assertEquals(2, response.getTotalTransactions());
        assertEquals(new BigDecimal("50.00"), response.getTotalTurnover());
        assertEquals(new BigDecimal("100.00"), response.getTotalHouseProfit());
        assertEquals(new BigDecimal("50.00"), response.getTotalClientLosses());
    }

    @Test
    void testGetUserStats() {
        // 1. User anlegen
        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setBalance(new BigDecimal("50.00"));

        // 2. Transaktionen anlegen
        Transaction transaction1 = new Transaction();
        transaction1.setId(1L);
        transaction1.setUserId(1L);
        transaction1.setInvoicingParty("roulette");
        transaction1.setAmount(new BigDecimal("100.00"));

        Transaction transaction2 = new Transaction();
        transaction2.setId(2L);
        transaction2.setUserId(1L);
        transaction2.setInvoicingParty("slotmachine");
        transaction2.setAmount(new BigDecimal("-50.00"));

        // 3. Mocks konfigurieren
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(transactionRepository.findByUserId(1L)).thenReturn(List.of(transaction1, transaction2));

        // 4. Test ausführen
        UserStatsResponse response = statsHandler.getUserStats(1L);

        // 5. Assertions
        assertEquals(1L, response.getUserId());
        assertEquals(2, response.getTotalTransactions());
        assertEquals(new BigDecimal("50.00"), response.getTotalTurnover());
        assertEquals(new BigDecimal("100.00"), response.getTotalWinnings());
        assertEquals(new BigDecimal("50.00"), response.getTotalLosses());
        assertEquals(new BigDecimal("50.00"), response.getNetProfit());
        assertEquals(new BigDecimal("50.00"), response.getCurrentBalance());
    }

    @Test
    void testGetUserStatsUserNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            statsHandler.getUserStats(999L);
        });
    }
}