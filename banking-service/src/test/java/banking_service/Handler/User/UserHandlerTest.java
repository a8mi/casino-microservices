package banking_service.Handler.User;

import banking_service.Model.User.User;
import banking_service.Repository.User.IUserRepository;
import banking_service.View.User.IUserView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserHandlerTest {

    private IUserRepository repository;
    private UserHandler handler;

    @BeforeEach
    void setUp() {
        repository = mock(IUserRepository.class);
        handler = new UserHandler(repository);
    }

    @Test
    @DisplayName("getUserById: vorhanden -> View")
    void getUserById_found() {
        User u = new User("Max", "Mustermann");
        when(repository.findById(1L)).thenReturn(Optional.of(u));
        IUserView view = handler.getUserById(1L);
        assertNotNull(view);
        assertEquals("Max", view.getFirstName());
    }

    @Test
    @DisplayName("getUserById: nicht gefunden -> Exception")
    void getUserById_notFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> handler.getUserById(99L));
    }

    @Test
    @DisplayName("getAllUsers: leer -> leere Liste")
    void getAllUsers_empty() {
        when(repository.findAll()).thenReturn(List.of());
        assertTrue(handler.getAllUsers().isEmpty());
    }

    @Test
    @DisplayName("getAllUsers: mehrere -> alle gemappt")
    void getAllUsers_multiple() {
        when(repository.findAll()).thenReturn(List.of(
                new User("A", "1"), new User("B", "2")));
        assertEquals(2, handler.getAllUsers().size());
    }

    @Test
    @DisplayName("createUser: valid -> save")
    void createUser_valid() {
        when(repository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));
        IUserView view = handler.createUser("Max", "Mustermann");
        assertNotNull(view);
        verify(repository).save(any(User.class));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t"})
    @DisplayName("createUser: leerer Vorname -> Exception")
    void createUser_blankFirstName(String blank) {
        assertThrows(IllegalArgumentException.class,
                () -> handler.createUser(blank, "Muster"));
        verify(repository, never()).save(any(User.class));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t"})
    @DisplayName("createUser: leerer Nachname -> Exception")
    void createUser_blankLastName(String blank) {
        assertThrows(IllegalArgumentException.class,
                () -> handler.createUser("Max", blank));
        verify(repository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("updateUser: vorhanden -> update")
    void updateUser_found() {
        User existing = new User("Max", "Mustermann");
        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));
        IUserView view = handler.updateUser(1L, "Moritz", "Schmidt");
        assertEquals("Moritz", view.getFirstName());
    }

    @Test
    @DisplayName("updateUser: nicht gefunden -> Exception")
    void updateUser_notFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class,
                () -> handler.updateUser(99L, "X", "Y"));
    }

    @Test
    @DisplayName("deleteUser: vorhanden -> deleteById")
    void deleteUser_found() {
        User existing = new User("Max", "Mustermann");
        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        assertNotNull(handler.deleteUser(1L));
        verify(repository).deleteById(1L);
    }

    @Test
    @DisplayName("deleteUser: nicht gefunden -> Exception")
    void deleteUser_notFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> handler.deleteUser(99L));
    }

    @Test
    @DisplayName("deposit: positive -> Balance gesetzt")
    void deposit_positive() {
        User user = new User("Max", "Mustermann");
        when(repository.findById(1L)).thenReturn(Optional.of(user));
        when(repository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));
        handler.deposit(1L, new BigDecimal("100.50"));
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(repository).save(captor.capture());
        assertEquals(0, new BigDecimal("100.50").compareTo(captor.getValue().getBalance()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1.00", "-0.01", "-999.99"})
    @DisplayName("deposit: negativ -> Exception")
    void deposit_negative(String neg) {
        User user = new User("Max", "Mustermann");
        when(repository.findById(1L)).thenReturn(Optional.of(user));
        assertThrows(IllegalArgumentException.class,
                () -> handler.deposit(1L, new BigDecimal(neg)));
    }

    @Test
    @DisplayName("deposit: null -> NPE")
    void deposit_null() {
        User user = new User("Max", "Mustermann");
        when(repository.findById(1L)).thenReturn(Optional.of(user));
        assertThrows(NullPointerException.class,
                () -> handler.deposit(1L, null));
    }
}