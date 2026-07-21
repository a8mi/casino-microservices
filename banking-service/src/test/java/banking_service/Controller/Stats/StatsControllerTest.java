package banking_service.Controller.Stats;

import banking_service.Handler.Stats.IStatsHandler;
import banking_service.View.Stats.StatsResponse;
import banking_service.View.Stats.UserStatsResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StatsController.class)
class StatsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IStatsHandler statsHandler;

    @Test
    void givenTransactionsExist_whenGetGlobalStats_thenReturns200() throws Exception {
        StatsResponse response = new StatsResponse(1, 2,
                new BigDecimal("50.00"), new BigDecimal("100.00"), new BigDecimal("50.00"));
        when(statsHandler.getGlobalStats()).thenReturn(response);

        mockMvc.perform(get("/casino/bank/api/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalUsers").value(1))
                .andExpect(jsonPath("$.totalTransactions").value(2));
    }

    @Test
    void givenUserExists_whenGetUserStats_thenReturns200() throws Exception {
        UserStatsResponse response = new UserStatsResponse(1L, "John", "Doe",
                new BigDecimal("50.00"), 2, new BigDecimal("50.00"),
                new BigDecimal("100.00"), new BigDecimal("50.00"), new BigDecimal("50.00"));
        when(statsHandler.getUserStats(1L)).thenReturn(response);

        mockMvc.perform(get("/casino/bank/api/stats/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void givenUserNotFound_whenGetUserStats_thenReturns404() throws Exception {
        when(statsHandler.getUserStats(999L)).thenThrow(new RuntimeException("User not found: 999"));

        mockMvc.perform(get("/casino/bank/api/stats/user/999"))
                .andExpect(status().isNotFound());
    }
}