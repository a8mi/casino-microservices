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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatsHandlerTest {

    @Mock private IUserRepository userRepository;
    @Mock private TransactionRepository transactionRepository;
    @InjectMocks private StatsHandler statsHandler;

    @Test
    void givenTransactionsExist_whenGetGlobalStats_thenReturnsCorrectStats() {
        Transaction tx1 = (Transaction) Transaction.create(1L, "roulette", new BigDecimal("100.00"));
        Transaction tx2 = (Transaction) Transaction.create(2L, "slotmachine", new BigDecimal("-50.00"));

        when(userRepository.findAll()).thenReturn(List.of(mock(User.class)));
        when(transactionRepository.findAll()).thenReturn(List.of(tx1, tx2));

        StatsResponse response = statsHandler.getGlobalStats();

        assertEquals(1, response.getTotalUsers());
        assertEquals(2, response.getTotalTransactions());
        assertEquals(new BigDecimal("50.00"), response.getTotalTurnover());
        assertEquals(new BigDecimal("100.00"), response.getTotalHouseProfit());
        assertEquals(new BigDecimal("50.00"), response.getTotalClientLosses());
    }

    @Test
    void givenUserExists_whenGetUserStats_thenReturnsCorrectStats() {
        User user = mock(User.class);
        when(user.getId()).thenReturn(1L);
        when(user.getFirstName()).thenReturn("John");
        when(user.getLastName()).thenReturn("Doe");
        when(user.getBalance()).thenReturn(new BigDecimal("50.00"));

        Transaction tx1 = (Transaction) Transaction.create(1L, "roulette", new BigDecimal("100.00"));
        Transaction tx2 = (Transaction) Transaction.create(2L, "slotmachine", new BigDecimal("-50.00"));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(transactionRepository.findByUserId(1L)).thenReturn(List.of(tx1, tx2));

        UserStatsResponse response = statsHandler.getUserStats(1L);

        assertEquals(1L, response.getUserId());
        assertEquals(2, response.getTotalTransactions());
    }

    @Test
    void givenUserNotFound_whenGetUserStats_thenThrows() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> statsHandler.getUserStats(999L));
    }
}