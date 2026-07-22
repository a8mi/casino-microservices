package banking_service.Model.Transactions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TransactionFactoryTest {

    private final TransactionFactory factory = new TransactionFactory();

    @Test
    void givenValidArgs_whenCreate_thenReturnsITransaction() {
        ITransaction tx = factory.create(1L, "roulette", new BigDecimal("10.00"));
        assertNotNull(tx);
        assertTrue(tx instanceof Transaction);
        assertEquals(1L, tx.getUserId());
        assertEquals("roulette", tx.getInvoicingParty());
    }

    @Test
    void givenNullUserId_whenCreate_thenThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> factory.create(null, "roulette", new BigDecimal("10.00")));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ", "\t"})
    void givenBlankInvoicingParty_whenCreate_thenThrows(String blank) {
        assertThrows(IllegalArgumentException.class,
                () -> factory.create(1L, blank, new BigDecimal("10.00")));
    }

    @Test
    void givenNullAmount_whenCreate_thenThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> factory.create(1L, "roulette", null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"roulette", "slotmachine"})
    void givenValidInvoicingParties_whenCreate_thenWorks(String party) {
        ITransaction tx = factory.create(1L, party, new BigDecimal("5.00"));
        assertEquals(party, tx.getInvoicingParty());
    }

    @Test
    void givenLargeAmount_whenCreate_thenWorks() {
        BigDecimal huge = new BigDecimal("999999999.99");
        ITransaction tx = factory.create(1L, "roulette", huge);
        assertEquals(0, huge.compareTo(tx.getAmount()));
    }

    @Test
    void givenNegativeAmount_whenCreate_thenWorks() {
        ITransaction tx = factory.create(1L, "roulette", new BigDecimal("-50.00"));
        assertEquals(0, new BigDecimal("-50.00").compareTo(tx.getAmount()));
    }
}