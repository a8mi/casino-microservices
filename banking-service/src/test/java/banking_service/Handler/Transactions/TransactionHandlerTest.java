package banking_service.Handler.Transactions;

import banking_service.Model.Transactions.ITransactionFactory;
import banking_service.Model.Transactions.Transaction;
import banking_service.Repository.Transactions.TransactionRepository;
import banking_service.Repository.User.IUserRepository;
import banking_service.View.Transactions.TransactionRequest;
import banking_service.View.Transactions.TransactionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TransactionHandlerTest {

    private TransactionRepository transactionRepository;
    private IUserRepository userRepository;
    private ITransactionFactory transactionFactory;
    private TransactionHandler handler;

    @BeforeEach
    void setUp() {
        transactionRepository = mock(TransactionRepository.class);
        userRepository = mock(IUserRepository.class);
        transactionFactory = mock(ITransactionFactory.class);
        handler = new TransactionHandler(transactionRepository, userRepository, transactionFactory);
    }

    @Test
    void givenTransactionsExist_whenGetAllTransactions_thenReturnsList() {
        Transaction tx1 = (Transaction) Transaction.create(1L, "roulette", new BigDecimal("10.00"));
        Transaction tx2 = (Transaction) Transaction.create(2L, "slotmachine", new BigDecimal("20.00"));
        tx1.setId(1L);
        tx2.setId(2L);
        when(transactionRepository.findAll()).thenReturn(List.of(tx1, tx2));

        List<TransactionResponse> result = handler.getAllTransactions();

        assertEquals(2, result.size());
        verify(transactionRepository).findAll();
    }

    @Test
    void givenNoTransactions_whenGetAllTransactions_thenReturnsEmptyList() {
        when(transactionRepository.findAll()).thenReturn(List.of());

        List<TransactionResponse> result = handler.getAllTransactions();

        assertTrue(result.isEmpty());
    }

    @Test
    void givenUserExists_whenGetTransactionsByUserId_thenReturnsTransactions() {
        when(userRepository.existsById(1L)).thenReturn(true);
        Transaction tx = (Transaction) Transaction.create(1L, "roulette", new BigDecimal("10.00"));
        tx.setId(1L);
        when(transactionRepository.findByUserId(1L)).thenReturn(List.of(tx));

        List<TransactionResponse> result = handler.getTransactionsByUserId(1L);

        assertEquals(1, result.size());
    }

    @Test
    void givenUserNotFound_whenGetTransactionsByUserId_thenThrows() {
        when(userRepository.existsById(99L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> handler.getTransactionsByUserId(99L));
        verify(transactionRepository, never()).findByUserId(anyLong());
    }

    @Test
    void givenValidRequest_whenCreateTransaction_thenSavesAndReturns() {
        when(userRepository.existsById(1L)).thenReturn(true);
        Transaction tx = (Transaction) Transaction.create(1L, "roulette", new BigDecimal("15.00"));
        when(transactionFactory.create(1L, "roulette", new BigDecimal("15.00"))).thenReturn(tx);
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(inv -> {
            Transaction saved = inv.getArgument(0);
            saved.setId(10L);
            return saved;
        });

        TransactionRequest req = new TransactionRequest("roulette", new BigDecimal("15.00"));
        TransactionResponse response = handler.createTransaction(1L, req);

        assertNotNull(response);
        verify(transactionFactory).create(1L, "roulette", new BigDecimal("15.00"));
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    void givenUserNotFound_whenCreateTransaction_thenThrows() {
        when(userRepository.existsById(99L)).thenReturn(false);
        TransactionRequest req = new TransactionRequest("roulette", new BigDecimal("15.00"));

        assertThrows(RuntimeException.class, () -> handler.createTransaction(99L, req));
        verify(transactionFactory, never()).create(anyLong(), anyString(), any());
    }

    @ParameterizedTest
    @ValueSource(strings = {"roulette", "slotmachine"})
    void givenValidInvoicingParty_whenCreateTransaction_thenWorks(String party) {
        when(userRepository.existsById(1L)).thenReturn(true);
        Transaction tx = (Transaction) Transaction.create(1L, party, new BigDecimal("5.00"));
        when(transactionFactory.create(eq(1L), eq(party), any())).thenReturn(tx);
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(inv -> inv.getArgument(0));

        TransactionRequest req = new TransactionRequest(party, new BigDecimal("5.00"));
        TransactionResponse response = handler.createTransaction(1L, req);

        assertEquals(party, response.getInvoicingParty());
    }

    @Test
    void givenTransactionExists_whenUpdateTransaction_thenUpdates() {
        Transaction existing = (Transaction) Transaction.create(1L, "roulette", new BigDecimal("10.00"));
        existing.setId(5L);
        banking_service.Model.User.User user = mock(banking_service.Model.User.User.class);
        when(user.getBalance()).thenReturn(new BigDecimal("100.00"));
        when(transactionRepository.findById(5L)).thenReturn(Optional.of(existing));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(inv -> inv.getArgument(0));

        TransactionRequest req = new TransactionRequest("slotmachine", new BigDecimal("99.99"));
        TransactionResponse response = handler.updateTransaction(5L, req);

        assertEquals("slotmachine", response.getInvoicingParty());
    }

    @Test
    void givenTransactionNotFound_whenUpdateTransaction_thenThrows() {
        when(transactionRepository.findById(99L)).thenReturn(Optional.empty());
        TransactionRequest req = new TransactionRequest("roulette", new BigDecimal("10.00"));

        assertThrows(RuntimeException.class, () -> handler.updateTransaction(99L, req));
    }

    @Test
    void givenTransactionExists_whenDeleteTransaction_thenDeletes() {
        Transaction existing = (Transaction) Transaction.create(1L, "roulette", new BigDecimal("10.00"));
        existing.setId(5L);
        banking_service.Model.User.User user = mock(banking_service.Model.User.User.class);
        when(user.getBalance()).thenReturn(new BigDecimal("100.00"));
        when(transactionRepository.findById(5L)).thenReturn(Optional.of(existing));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        TransactionResponse response = handler.deleteTransaction(5L);

        assertNotNull(response);
        verify(transactionRepository).delete(existing);
    }

    @Test
    void givenTransactionNotFound_whenDeleteTransaction_thenThrows() {
        when(transactionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> handler.deleteTransaction(99L));
        verify(transactionRepository, never()).delete(any(Transaction.class));
    }
}