package slotmachine_service.handler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import slotmachine_service.Client.IHTTPClient;
import slotmachine_service.Exceptions.BankingServiceException;
import slotmachine_service.Exceptions.InsufficientFundsException;
import slotmachine_service.GameLogic.ISymbolGenerator;
import slotmachine_service.GameLogic.PayoutPolicy;
import slotmachine_service.GameLogic.SymbolGenerator;
import slotmachine_service.Handler.SlotMachineHandler;
import slotmachine_service.Model.ESlotSymbol;
import slotmachine_service.Model.ISlotGameFactory;
import slotmachine_service.Model.SlotGame;
import slotmachine_service.Model.SlotGameFactory;
import slotmachine_service.Repository.ISlotGameRepository;
import slotmachine_service.View.GameView;
import slotmachine_service.View.PlayRequest;
import slotmachine_service.View.StatsView;
import slotmachine_service.View.UserStatsView;


class SlotHandlerTest {
        @Mock
        private ISlotGameRepository repository;

        private ISlotGameFactory factory;
        
        private IHTTPClient bankingClient;
        
        private ISymbolGenerator symbolGenerator;
        
        private SlotMachineHandler handler;

        @BeforeEach
        void setUp(){
                repository = mock(ISlotGameRepository.class);
                factory = new SlotGameFactory();
                bankingClient = mock(IHTTPClient.class);
                symbolGenerator = mock(SymbolGenerator.class);

                Clock clock = Clock.fixed(Instant.parse("2026-07-12T10:00:00Z"), ZoneOffset.UTC);
                handler = new SlotMachineHandler(
                        repository,
                        factory,
                        bankingClient,
                        symbolGenerator,
                        new PayoutPolicy(),
                        clock
                );

                 when(repository.save(any(SlotGame.class))).thenAnswer(invocation -> {
                        SlotGame game = invocation.getArgument(0);
                        setId(game, 42L);
                        return game;
                });
        }

        @Test
        void rulesExplainNetAmountAndHistoryDeletion() {
                assertThat(handler.getRules())
                        .contains("payout minus bet")
                        .contains("does not reverse");
        }

        @Test
        void chancesContainFormulaAndCalculatedRtp() {
                assertThat(handler.getChances())
                        .contains("P(three identical symbols)")
                        .contains("Theoretical RTP: 91.0845%")
                        .contains("Theoretical house edge: 8.9155%");
        }

        @Test
         void winningRoundUpdatesBankThenPersistsHistory() {
                when(bankingClient.getUser(7L))
                        .thenReturn(new IHTTPClient.UserAccount(7L, new BigDecimal("100.00")));
                when(symbolGenerator.spin()).thenReturn(List.of(
                        ESlotSymbol.CHERRY,
                        ESlotSymbol.CHERRY,
                        ESlotSymbol.CHERRY
                ));

                GameView response = handler.playGame(new PlayRequest(7L, new BigDecimal("2.00")));

                assertThat(response.getId()).isEqualTo(42L);
                assertThat(response.getWinning()).isTrue();
                assertThat(response.getAmount()).isEqualByComparingTo("20.00");
                assertThat(response.getSlotStates()).containsExactly("CHERRY", "CHERRY", "CHERRY");

                verify(bankingClient).createTransaction(7L, new BigDecimal("20.00"));
                verify(repository).save(any(SlotGame.class));
        }

        @Test
        void insufficientBalanceDoesNotSpinChargeOrPersist() {
                when(bankingClient.getUser(7L))
                        .thenReturn(new IHTTPClient.UserAccount(7L, new BigDecimal("1.99")));

                assertThatThrownBy(() -> handler.playGame(new PlayRequest(7L, new BigDecimal("2.00"))))
                        .isInstanceOf(InsufficientFundsException.class);

                verifyNoInteractions(symbolGenerator);
                verify(bankingClient, never()).createTransaction(anyLong(), any());
                verifyNoInteractions(repository);
    }

        @Test
        void failedBankTransactionDoesNotPersistGame() {
                when(bankingClient.getUser(7L))
                        .thenReturn(new IHTTPClient.UserAccount(7L, new BigDecimal("100.00")));
                when(symbolGenerator.spin()).thenReturn(List.of(
                        ESlotSymbol.CHERRY,
                        ESlotSymbol.LEMON,
                        ESlotSymbol.ORANGE
                ));
                doThrow(new BankingServiceException("down"))
                        .when(bankingClient).createTransaction(7L, new BigDecimal("-2.00"));

                assertThatThrownBy(() -> handler.playGame(new PlayRequest(7L, new BigDecimal("2.00"))))
                        .isInstanceOf(BankingServiceException.class);

                verify(repository, never()).save(any());
        }

        @Test
        void aggregatesHouseAndClientStatistics() {
                SlotGame loss = game(1L, "2.00", "0.00", "-2.00");
                SlotGame win = game(2L, "1.00", "11.00", "10.00");
                when(repository.findAllByOrderByIdAsc()).thenReturn(List.of(loss, win));

                StatsView stats = handler.getStats();

                assertThat(stats.total_client_count()).isEqualTo(2);
                assertThat(stats.total_games_count()).isEqualTo(2);
                assertThat(stats.total_profit()).isEqualByComparingTo("-8.00");
                assertThat(stats.total_cash_out()).isEqualByComparingTo("11.00");
                assertThat(stats.total_turnover()).isEqualByComparingTo("3.00");
        }

        @Test
        void returnsZeroHistoryForExistingBankingUser() {
                when(repository.findByUserIdOrderByIdAsc(9L)).thenReturn(List.of());
                when(bankingClient.getUser(9L))
                .thenReturn(new IHTTPClient.UserAccount(9L, BigDecimal.ZERO));

                UserStatsView stats = handler.getUserStatsById(9L);

                assertThat(stats.total_games_count()).isZero();
                assertThat(stats.total_client_profit()).isEqualByComparingTo("0.00");
                verify(bankingClient).getUser(9L);
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

        private static SlotGame game(Long userId, String bet, String payout, String amount) {
                return new SlotGame(
                        userId,
                        new BigDecimal(bet),
                        new BigDecimal(payout),
                        new BigDecimal(amount),
                        payout.equals("0.00")
                                ? List.of(ESlotSymbol.CHERRY, ESlotSymbol.LEMON, ESlotSymbol.ORANGE)
                                : List.of(ESlotSymbol.CHERRY, ESlotSymbol.CHERRY, ESlotSymbol.CHERRY),
                Instant.parse("2026-07-12T10:00:00Z")
                );
        }


}
