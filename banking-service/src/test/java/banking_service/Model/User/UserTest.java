package banking_service.Model.User;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    @DisplayName("create: gueltige Werte -> Balance ist 0")
    void create_valid() {
        User user = (User) User.create("Max", "Mustermann");
        assertEquals("Max", user.getFirstName());
        assertEquals("Mustermann", user.getLastName());
        assertEquals(0, BigDecimal.ZERO.compareTo(user.getBalance()));
        assertNull(user.getId());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n"})
    @DisplayName("create: leerer firstName -> Exception")
    void blankFirstName_throws(String invalid) {
        assertThrows(IllegalArgumentException.class,
                () -> User.create(invalid, "Muster"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n"})
    @DisplayName("create: leerer lastName -> Exception")
    void blankLastName_throws(String invalid) {
        assertThrows(IllegalArgumentException.class,
                () -> User.create("Max", invalid));
    }

    @Test
    @DisplayName("setters funktionieren")
    void setters_work() {
        User user = (User) User.create("Max", "Muster");
        user.setFirstName("Moritz");
        user.setLastName("Schmidt");
        user.setBalance(new BigDecimal("100.50"));
        assertEquals("Moritz", user.getFirstName());
        assertEquals("Schmidt", user.getLastName());
        assertEquals(0, new BigDecimal("100.50").compareTo(user.getBalance()));
    }

    @Test
    @DisplayName("Extremwerte: 10.000 Zeichen")
    void extremeLongNames() {
        String n = "a".repeat(10_000);
        User user = (User) User.create(n, n);
        assertEquals(10_000, user.getFirstName().length());
    }

    @Test
    @DisplayName("setBalance: negativ -> Exception")
    void setBalance_negative() {
        User user = (User) User.create("Max", "Muster");
        assertThrows(IllegalArgumentException.class,
                () -> user.setBalance(new BigDecimal("-1.00")));
    }

    @Test
    @DisplayName("setBalance: null -> Exception")
    void setBalance_null() {
        User user = (User) User.create("Max", "Muster");
        assertThrows(IllegalArgumentException.class,
                () -> user.setBalance(null));
    }
}