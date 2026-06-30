package banking_service.Model.User;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    @DisplayName("Konstruktor: gueltige Werte -> Balance ist 0")
    void constructor_valid() {
        User user = new User("Max", "Mustermann");
        assertEquals("Max", user.getFirstName());
        assertEquals("Mustermann", user.getLastName());
        assertEquals(0, BigDecimal.ZERO.compareTo(user.getBalance()));
        assertNull(user.getId());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { " ", "   ", "\t", "\n" })
    @DisplayName("Konstruktor: leerer firstName -> Exception")
    void blankFirstName_throws(String invalid) {
        assertThrows(IllegalArgumentException.class, () -> new User(invalid, "Muster"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { " ", "   ", "\t", "\n" })
    @DisplayName("Konstruktor: leerer lastName -> Exception")
    void blankLastName_throws(String invalid) {
        assertThrows(IllegalArgumentException.class, () -> new User("Max", invalid));
    }

    @Test
    @DisplayName("setters funktionieren")
    void setters_work() {
        User user = new User("Max", "Muster");
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
        User user = new User(n, n);
        assertEquals(10_000, user.getFirstName().length());
    }

    @Test
    @DisplayName("Unicode funktioniert")
    void unicodeNames() {
        User user = new User("李明", "Mueller");
        assertEquals("李明", user.getFirstName());
    }

    @ParameterizedTest
    @MethodSource("randomValidNames")
    @DisplayName("Random: 50 Namen")
    void randomNames(String f, String l) {
        User user = new User(f, l);
        assertEquals(f, user.getFirstName());
    }

    static Stream<Arguments> randomValidNames() {
        String[] firsts = {"Max", "Anna", "Li", "OBrien", "Jose", "X"};
        String[] lasts = {"Muster", "Schmidt", "Wang", "Smith", "Garcia", "Y"};
        Random r = new Random(42);
        return IntStream.range(0, 50)
                .mapToObj(i -> Arguments.of(
                        firsts[r.nextInt(firsts.length)],
                        lasts[r.nextInt(lasts.length)]));
    }
}