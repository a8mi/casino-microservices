package banking_service.View.User;

import banking_service.Model.User.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class UserViewTest {

    @Test
    @DisplayName("of(User): mappt alle Felder")
    void of_mapsAllFields() {
        User user = (User) User.create("Max", "Mustermann");
        user.setBalance(new BigDecimal("42.50"));
        IUserView view = UserView.of(user);
        assertNotNull(view);
        assertEquals("Max", view.getFirstName());
        assertEquals("Mustermann", view.getLastName());
        assertEquals(0, new BigDecimal("42.50").compareTo(view.getBalance()));
    }

    @Test
    @DisplayName("of(User): id ist null bei neuem User")
    void of_unpersistedUser_idIsNull() {
        User user = (User) User.create("Anna", "Schmidt");
        IUserView view = UserView.of(user);
        assertNull(view.getId());
    }

    @Test
    @DisplayName("UserView-Record: equals/hashCode")
    void record_equality() {
        UserView a = new UserView(1L, "Max", "M", BigDecimal.TEN);
        UserView b = new UserView(1L, "Max", "M", BigDecimal.TEN);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }
}