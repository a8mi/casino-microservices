package banking_service.Model.Transactions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    @Test
    void givenValidArgs_whenCreate_thenReturnsTransaction() {
        ITransaction tx = Transaction.create(1L, "roulette", new BigDecimal("10.50"));
        assertNotNull(tx);
        assertEquals(1L, tx.getUserId());
        assertEquals("roulette", tx.getInvoicingParty());
        assertEquals(0, new BigDecimal("10.50").compareTo(tx.getAmount()));
    }

    @Test
    void givenNullUserId_whenCreate_thenThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> Transaction.create(null, "roulette", new BigDecimal("10.00")));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ", "\t"})
    void givenBlankInvoicingParty_whenCreate_thenThrows(String blank) {
        assertThrows(IllegalArgumentException.class,
                () -> Transaction.create(1L, blank, new BigDecimal("10.00")));
    }

    @Test
    void givenNullAmount_whenCreate_thenThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> Transaction.create(1L, "roulette", null));
    }

    @Test
    void givenNullUserId_whenSetUserId_thenThrows() {
        Transaction tx = (Transaction) Transaction.create(1L, "roulette", new BigDecimal("10.00"));
        assertThrows(IllegalArgumentException.class, () -> tx.setUserId(null));
    }

    @Test
    void givenBlankInvoicingParty_whenSetInvoicingParty_thenThrows() {
        Transaction tx = (Transaction) Transaction.create(1L, "roulette", new BigDecimal("10.00"));
        assertThrows(IllegalArgumentException.class, () -> tx.setInvoicingParty(""));
    }

    @Test
    void givenNullAmount_whenSetAmount_thenThrows() {
        Transaction tx = (Transaction) Transaction.create(1L, "roulette", new BigDecimal("10.00"));
        assertThrows(IllegalArgumentException.class, () -> tx.setAmount(null));
    }

    @Test
    void givenValidValues_whenSetters_thenFieldsUpdated() {
        Transaction tx = (Transaction) Transaction.create(1L, "roulette", new BigDecimal("10.00"));
        tx.setInvoicingParty("slotmachine");
        tx.setAmount(new BigDecimal("99.99"));
        assertEquals("slotmachine", tx.getInvoicingParty());
        assertEquals(0, new BigDecimal("99.99").compareTo(tx.getAmount()));
    }

    @Test
    void givenLargeAmount_whenCreate_thenWorks() {
        BigDecimal huge = new BigDecimal("999999999.99");
        ITransaction tx = Transaction.create(1L, "roulette", huge);
        assertEquals(0, huge.compareTo(tx.getAmount()));
    }

    @Test
    void givenNegativeAmount_whenCreate_thenWorks() {
        ITransaction tx = Transaction.create(1L, "roulette", new BigDecimal("-50.00"));
        assertEquals(0, new BigDecimal("-50.00").compareTo(tx.getAmount()));
    }
}