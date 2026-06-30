package banking_service.Controller.User;

import banking_service.Handler.User.IUserHandler;
import banking_service.View.User.IUserTestView;
import banking_service.View.User.IUserView;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private IUserHandler handler;

    static class FakeUserView implements IUserView {
        private final Long id; private final String firstName, lastName; private final BigDecimal balance;
        FakeUserView(Long id, String f, String l, BigDecimal b) {
            this.id = id; this.firstName = f; this.lastName = l; this.balance = b;
        }
        public Long getId() { return id; }
        public String getFirstName() { return firstName; }
        public String getLastName() { return lastName; }
        public BigDecimal getBalance() { return balance; }
    }

    static class FakeTestView implements IUserTestView {
        private final String text;
        FakeTestView(String t) { this.text = t; }
        public String getText() { return text; }
    }

    private FakeUserView view(Long id, String first, String last, String balance) {
        return new FakeUserView(id, first, last, new BigDecimal(balance));
    }

    @Test
    @DisplayName("GET /user/{id}: vorhanden -> 200")
    void getUserById_found() throws Exception {
        FakeUserView v = view(1L, "Max", "M", "0");
        when(handler.getUserById(1L)).thenReturn(v);
        mockMvc.perform(get("/casino/bank/api/user/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Max"));
    }

    @Test
    @DisplayName("GET /user/{id}: nicht gefunden -> 404")
    void getUserById_notFound() throws Exception {
        when(handler.getUserById(99L)).thenThrow(new RuntimeException());
        mockMvc.perform(get("/casino/bank/api/user/{id}", 99L))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /users: leer -> []")
    void getAllUsers_empty() throws Exception {
        when(handler.getAllUsers()).thenReturn(List.of());
        mockMvc.perform(get("/casino/bank/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @DisplayName("GET /users: mehrere -> Array")
    void getAllUsers_multiple() throws Exception {
        when(handler.getAllUsers()).thenReturn(List.of(
                view(1L, "Max", "M", "0"),
                view(2L, "Anna", "S", "10.50")));
        mockMvc.perform(get("/casino/bank/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("POST /user: valid -> 201")
    void createUser_valid() throws Exception {
        FakeUserView v = view(1L, "Max", "Muster", "0");
        when(handler.createUser("Max", "Muster")).thenReturn(v);
        mockMvc.perform(post("/casino/bank/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"first_name\":\"Max\",\"last_name\":\"Muster\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("POST /user: leer -> 400")
    void createUser_blank() throws Exception {
        when(handler.createUser(eq(""), anyString())).thenThrow(new IllegalArgumentException());
        mockMvc.perform(post("/casino/bank/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"first_name\":\"\",\"last_name\":\"X\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /user/{id}: valid -> 200")
    void updateUser_valid() throws Exception {
        FakeUserView v = view(1L, "M", "S", "0");
        when(handler.updateUser(1L, "M", "S")).thenReturn(v);
        mockMvc.perform(put("/casino/bank/api/user/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"first_name\":\"M\",\"last_name\":\"S\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PUT /user/{id}: nicht gefunden -> 404")
    void updateUser_notFound() throws Exception {
        when(handler.updateUser(eq(99L), anyString(), anyString())).thenThrow(new RuntimeException());
        mockMvc.perform(put("/casino/bank/api/user/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"first_name\":\"X\",\"last_name\":\"Y\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /user/{id}: valid -> 200")
    void deleteUser_valid() throws Exception {
        FakeUserView v = view(1L, "M", "X", "0");
        when(handler.deleteUser(1L)).thenReturn(v);
        mockMvc.perform(delete("/casino/bank/api/user/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /user/{id}: nicht gefunden -> 404")
    void deleteUser_notFound() throws Exception {
        when(handler.deleteUser(99L)).thenThrow(new RuntimeException());
        mockMvc.perform(delete("/casino/bank/api/user/{id}", 99L))
                .andExpect(status().isNotFound());
    }

    @ParameterizedTest
    @ValueSource(strings = {"100|2", "50|0", "1|0"})
    @DisplayName("POST /deposit: valid -> 200")
    void deposit_valid(String params) throws Exception {
        String[] p = params.split("\\|");
        long amount = Long.parseLong(p[0]);
        int dec = Integer.parseInt(p[1]);
        FakeUserView v = view(1L, "M", "X", "0");
        when(handler.deposit(eq(1L), any(BigDecimal.class))).thenReturn(v);
        mockMvc.perform(post("/casino/bank/api/user/{id}/deposit/{amount}/{decimals}", 1L, amount, dec))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /deposit: nicht gefunden -> 404")
    void deposit_notFound() throws Exception {
        when(handler.deposit(eq(99L), any(BigDecimal.class))).thenThrow(new RuntimeException());
        mockMvc.perform(post("/casino/bank/api/user/{id}/deposit/{amount}/{decimals}", 99L, 100L, 2))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /test/{input}: smoke")
    void getTest_smoke() throws Exception {
        when(handler.getTest(anyString())).thenReturn(Optional.of(new FakeTestView("HELLO")));
        mockMvc.perform(get("/casino/bank/api/test/{input}", "hello"))
                .andExpect(status().isOk());
    }
}