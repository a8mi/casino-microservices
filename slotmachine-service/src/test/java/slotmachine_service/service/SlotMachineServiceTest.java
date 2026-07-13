package slotmachine_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import slotmachine_service.api.GameResponse;
import slotmachine_service.api.PlayRequest;
import slotmachine_service.client.BankingClient;
import slotmachine_service.exception.BankingServiceException;
import slotmachine_service.exception.InsufficientFundsException;
import slotmachine_service.game.PayoutPolicy;
import slotmachine_service.game.SymbolGenerator;
import slotmachine_service.model.SlotGame;
import slotmachine_service.model.SlotSymbol;
import slotmachine_service.repository.SlotGameRepository;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class SlotMachineServiceTest {

    private SlotGameRepository repository;
    private BankingClient bankingClient;
    private SymbolGenerator symbolGenerator;
    private SlotMachineService service;

    @BeforeEach
    void setUp() {
        repository = mock(SlotGameRepository.class);
        bankingClient = mock(BankingClient.class);
        symbolGenerator = mock(SymbolGenerator.class);

        SlotGameMapper mapper = new SlotGameMapper();
        Clock clock = Clock.fixed(Instant.parse("2026-07-12T10:00:00Z"), ZoneOffset.UTC);
        service = new SlotMachineService(
                repository,
                bankingClient,
                symbolGenerator,
                new PayoutPolicy(),
                mapper,
                clock
        );

        when(repository.save(any(SlotGame.class))).thenAnswer(invocation -> {
            SlotGame game = invocation.getArgument(0);
            setId(game, 42L);
            return game;
        });
    }

    @Test
    void winningRoundUpdatesBankThenPersistsHistory() {
        when(bankingClient.getUser(7L))
                .thenReturn(new BankingClient.UserAccount(7L, new BigDecimal("100.00")));
        when(symbolGenerator.spin()).thenReturn(List.of(
                SlotSymbol.CHERRY,
                SlotSymbol.CHERRY,
                SlotSymbol.CHERRY
        ));

        GameResponse response = service.play(new PlayRequest(7L, new BigDecimal("2.00")));

        assertThat(response.id()).isEqualTo(42L);
        assertThat(response.winning()).isTrue();
        assertThat(response.amount()).isEqualByComparingTo("20.00");
        assertThat(response.slot_states()).containsExactly("CHERRY", "CHERRY", "CHERRY");

        verify(bankingClient).createTransaction(7L, new BigDecimal("20.00"));
        verify(repository).save(any(SlotGame.class));
    }

    @Test
    void insufficientBalanceDoesNotSpinChargeOrPersist() {
        when(bankingClient.getUser(7L))
                .thenReturn(new BankingClient.UserAccount(7L, new BigDecimal("1.99")));

        assertThatThrownBy(() -> service.play(new PlayRequest(7L, new BigDecimal("2.00"))))
                .isInstanceOf(InsufficientFundsException.class);

        verifyNoInteractions(symbolGenerator);
        verify(bankingClient, never()).createTransaction(anyLong(), any());
        verifyNoInteractions(repository);
    }

    @Test
    void failedBankTransactionDoesNotPersistGame() {
        when(bankingClient.getUser(7L))
                .thenReturn(new BankingClient.UserAccount(7L, new BigDecimal("100.00")));
        when(symbolGenerator.spin()).thenReturn(List.of(
                SlotSymbol.CHERRY,
                SlotSymbol.LEMON,
                SlotSymbol.ORANGE
        ));
        doThrow(new BankingServiceException("down"))
                .when(bankingClient).createTransaction(7L, new BigDecimal("-2.00"));

        assertThatThrownBy(() -> service.play(new PlayRequest(7L, new BigDecimal("2.00"))))
                .isInstanceOf(BankingServiceException.class);

        verify(repository, never()).save(any());
    }

    private static void setId(SlotGame game, Long id) {
        try {
            Field field = SlotGame.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(game, id);
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError(exception);
        }
    }
}
