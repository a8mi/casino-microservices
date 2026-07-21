package slotmachine_service.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import slotmachine_service.api.GameResponse;
import slotmachine_service.exception.GameNotFoundException;
import slotmachine_service.service.GameInfoService;
import slotmachine_service.service.SlotMachineService;
import slotmachine_service.service.SlotStatsService;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class SlotMachineControllerTest {

    private SlotMachineService slotMachineService;
    private SlotStatsService statsService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        slotMachineService = mock(SlotMachineService.class);
        statsService = mock(SlotStatsService.class);

        SlotMachineController controller = new SlotMachineController(
                slotMachineService,
                statsService,
                new GameInfoService()
        );
        mockMvc = standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void playReturnsAssignmentFields() throws Exception {
        when(slotMachineService.play(any())).thenReturn(new GameResponse(
                1L,
                7L,
                false,
                new BigDecimal("-2.00"),
                new BigDecimal("2.00"),
                new BigDecimal("0.00"),
                List.of("CHERRY", "LEMON", "ORANGE"),
                Instant.parse("2026-07-12T10:00:00Z")
        ));

        mockMvc.perform(post("/casino/slots/api/play")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"user":7,"bet":2.00}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user").value(7))
                .andExpect(jsonPath("$.winning").value(false))
                .andExpect(jsonPath("$.amount").value(-2.00))
                .andExpect(jsonPath("$.slot_states[0]").value("CHERRY"));
    }

    @Test
    void invalidBetReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/casino/slots/api/play")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"user":7,"bet":0}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void missingGameReturnsNotFound() throws Exception {
        when(statsService.getGame(99L)).thenThrow(new GameNotFoundException(99L));

        mockMvc.perform(get("/casino/slots/api/stat/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void chancesAreExposedAsPlainText() throws Exception {
        mockMvc.perform(get("/casino/slots/api/info/chances"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Theoretical RTP")));
    }
}
