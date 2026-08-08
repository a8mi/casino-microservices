package slotmachine_service.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import slotmachine_service.Controller.GlobalExceptionHandler;
import slotmachine_service.Controller.SlotMachineController;
import slotmachine_service.Exceptions.GameNotFoundException;
import slotmachine_service.Handler.SlotMachineHandler;
import slotmachine_service.Utils.GameChances;
import slotmachine_service.View.GameView;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class SlotMachineControllerTest {

    private SlotMachineHandler slotMachineHandler;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        slotMachineHandler = mock(SlotMachineHandler.class);
        when(slotMachineHandler.getChances()).thenReturn(GameChances.getGameChances());

        SlotMachineController controller = new SlotMachineController(
                slotMachineHandler
        );
        mockMvc = standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void playReturnsAssignmentFields() throws Exception {
        when(slotMachineHandler.playGame(any())).thenReturn(new GameView(
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
                .andExpect(jsonPath("$.slotStates[0]").value("CHERRY"));
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
        when(slotMachineHandler.getGameById(99L)).thenThrow(new GameNotFoundException(99L));

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
        verify(slotMachineHandler).getChances();
    }
}
