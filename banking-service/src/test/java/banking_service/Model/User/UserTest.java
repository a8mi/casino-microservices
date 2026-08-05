package banking_service.Model.User;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void givenValidNames_whenCreate_thenBalanceIsZero() {
        User user = (User) User.create("Max", "Mustermann");
        assertEquals("Max", user.getFirstName());
        assertEquals("Mustermann", user.getLastName());
        assertEquals(0, BigDecimal.ZERO.compareTo(user.getBalance()));
        assertNull(user.getId());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n"})
    void givenBlankFirstName_whenCreate_thenThrowsException(String invalid) {
        assertThrows(IllegalArgumentException.class,
                () -> User.create(invalid, "Muster"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n"})
    void givenBlankLastName_whenCreate_thenThrowsException(String invalid) {
        assertThrows(IllegalArgumentException.class,
                () -> User.create("Max", invalid));
    }

    @Test
    void givenValidFirstName_whenSetFirstName_thenUpdated() {
        User user = (User) User.create("Max", "Muster");
        user.setFirstName("Moritz");
        assertEquals("Moritz", user.getFirstName());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t"})
    void givenBlankFirstName_whenSetFirstName_thenThrowsException(String invalid) {
        User user = (User) User.create("Max", "Muster");
        assertThrows(IllegalArgumentException.class,
                () -> user.setFirstName(invalid));
    }

    @Test
    void givenValidLastName_whenSetLastName_thenUpdated() {
        User user = (User) User.create("Max", "Muster");
        user.setLastName("Schmidt");
        assertEquals("Schmidt", user.getLastName());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t"})
    void givenBlankLastName_whenSetLastName_thenThrowsException(String invalid) {
        User user = (User) User.create("Max", "Muster");
        assertThrows(IllegalArgumentException.class,
                () -> user.setLastName(invalid));
    }

    @Test
    void givenValidBalance_whenSetBalance_thenUpdated() {
        User user = (User) User.create("Max", "Muster");
        user.setBalance(new BigDecimal("100.50"));
        assertEquals(0, new BigDecimal("100.50").compareTo(user.getBalance()));
    }

    @Test
    void givenNegativeBalance_whenSetBalance_thenThrowsException() {
        User user = (User) User.create("Max", "Muster");
        assertThrows(IllegalArgumentException.class,
                () -> user.setBalance(new BigDecimal("-1.00")));
    }

    @Test
    void givenZeroBalance_whenSetBalance_thenUpdated() {
        User user = (User) User.create("Max", "Muster");
        user.setBalance(BigDecimal.ZERO);
        assertEquals(0, BigDecimal.ZERO.compareTo(user.getBalance()));
    }

    @Test
    void givenNullBalance_whenSetBalance_thenThrowsException() {
        User user = (User) User.create("Max", "Muster");
        assertThrows(IllegalArgumentException.class,
                () -> user.setBalance(null));
    }

    @Test
    void givenLongNames_whenCreate_thenWorks() {
        String n = "a".repeat(10_000);
        User user = (User) User.create(n, n);
        assertEquals(10_000, user.getFirstName().length());
    }
}