package banking_service.Controller.User;

import banking_service.Handler.User.IUserHandler;
import banking_service.View.User.IUserView;
import banking_service.View.User.UserView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    private IUserHandler userHandler;
    private UserController controller;

    @BeforeEach
    void setUp() {
        userHandler = mock(IUserHandler.class);
        controller = new UserController(userHandler);
    }

    @Test
    void givenExistingUser_whenGetUserById_thenReturns200() {
        IUserView view = new UserView(1L, "Max", "Mustermann", BigDecimal.ZERO);
        when(userHandler.getUserById(1L)).thenReturn(view);

        ResponseEntity<IUserView> response = controller.getUserById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Max", response.getBody().getFirstName());
    }

    @Test
    void givenUnknownId_whenGetUserById_thenThrowsException() {
        when(userHandler.getUserById(99L)).thenThrow(new RuntimeException("nicht gefunden"));

        assertThrows(RuntimeException.class, () -> controller.getUserById(99L));
    }

    @Test
    void givenNoUsers_whenGetAllUsers_thenReturnsEmptyList() {
        when(userHandler.getAllUsers()).thenReturn(List.of());

        ResponseEntity<List<IUserView>> response = controller.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void givenMultipleUsers_whenGetAllUsers_thenReturnsAll() {
        IUserView v1 = new UserView(1L, "Max", "M", BigDecimal.ZERO);
        IUserView v2 = new UserView(2L, "Anna", "A", BigDecimal.ZERO);
        when(userHandler.getAllUsers()).thenReturn(List.of(v1, v2));

        ResponseEntity<List<IUserView>> response = controller.getAllUsers();

        assertEquals(2, response.getBody().size());
    }

    @Test
    void givenValidBody_whenCreateUser_thenReturns201() {
        IUserView view = new UserView(1L, "Max", "Mustermann", BigDecimal.ZERO);
        when(userHandler.createUser("Max", "Mustermann")).thenReturn(view);

        Map<String, String> body = Map.of("first_name", "Max", "last_name", "Mustermann");
        ResponseEntity<IUserView> response = controller.createUser(body);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Max", response.getBody().getFirstName());
    }

    @Test
    void givenValidBody_whenUpdateUser_thenReturns200() {
        IUserView view = new UserView(1L, "Moritz", "Schmidt", BigDecimal.ZERO);
        when(userHandler.updateUser(1L, "Moritz", "Schmidt")).thenReturn(view);

        Map<String, String> body = Map.of("first_name", "Moritz", "last_name", "Schmidt");
        ResponseEntity<IUserView> response = controller.updateUser(1L, body);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Moritz", response.getBody().getFirstName());
    }

    @Test
    void givenExistingUser_whenDeleteUser_thenReturns200() {
        IUserView view = new UserView(1L, "Max", "Mustermann", BigDecimal.ZERO);
        when(userHandler.deleteUser(1L)).thenReturn(view);

        ResponseEntity<IUserView> response = controller.deleteUser(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void givenUnknownId_whenDeleteUser_thenThrowsException() {
        when(userHandler.deleteUser(99L)).thenThrow(new RuntimeException("nicht gefunden"));

        assertThrows(RuntimeException.class, () -> controller.deleteUser(99L));
    }

    @Test
    void givenValidDeposit_whenDeposit_thenReturns200() {
        IUserView view = new UserView(1L, "Max", "Mustermann", new BigDecimal("1.00"));
        when(userHandler.deposit(1L, new BigDecimal("1.00"))).thenReturn(view);

        ResponseEntity<IUserView> response = controller.deposit(1L, 100L, 2);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, new BigDecimal("1.00").compareTo(response.getBody().getBalance()));
    }

    @Test
    void givenInvalidDecimals_whenDeposit_thenReturnsBadRequest() {
        ResponseEntity<IUserView> response = controller.deposit(1L, 100L, 3);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}