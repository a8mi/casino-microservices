package slotmachine_service.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import slotmachine_service.api.GameResponse;
import slotmachine_service.api.PlayRequest;
import slotmachine_service.client.BankingClient;
import slotmachine_service.exception.InsufficientFundsException;
import slotmachine_service.game.PayoutPolicy;
import slotmachine_service.game.SymbolGenerator;
import slotmachine_service.model.SlotGame;
import slotmachine_service.model.SlotSymbol;
import slotmachine_service.repository.SlotGameRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Service
public class SlotMachineService {

    private final SlotGameRepository repository;
    private final BankingClient bankingClient;
    private final SymbolGenerator symbolGenerator;
    private final PayoutPolicy payoutPolicy;
    private final SlotGameMapper mapper;
    private final Clock clock;

    public SlotMachineService(
            SlotGameRepository repository,
            BankingClient bankingClient,
            SymbolGenerator symbolGenerator,
            PayoutPolicy payoutPolicy,
            SlotGameMapper mapper
    ) {
        this(repository, bankingClient, symbolGenerator, payoutPolicy, mapper, Clock.systemUTC());
    }

    SlotMachineService(
            SlotGameRepository repository,
            BankingClient bankingClient,
            SymbolGenerator symbolGenerator,
            PayoutPolicy payoutPolicy,
            SlotGameMapper mapper,
            Clock clock
    ) {
        this.repository = Objects.requireNonNull(repository);
        this.bankingClient = Objects.requireNonNull(bankingClient);
        this.symbolGenerator = Objects.requireNonNull(symbolGenerator);
        this.payoutPolicy = Objects.requireNonNull(payoutPolicy);
        this.mapper = Objects.requireNonNull(mapper);
        this.clock = Objects.requireNonNull(clock);
    }

    @Transactional
    public GameResponse play(PlayRequest request) {
        Objects.requireNonNull(request, "request is required");

        BigDecimal bet = normalizeBet(request.bet());
        BankingClient.UserAccount account = bankingClient.getUser(request.user());

        if (account.balance().compareTo(bet) < 0) {
            throw new InsufficientFundsException(request.user(), account.balance(), bet);
        }

        List<SlotSymbol> symbols = symbolGenerator.spin();
        BigDecimal payout = payoutPolicy.calculatePayout(bet, symbols);
        BigDecimal netAmount = payoutPolicy.calculateNetAmount(bet, payout);

        // The banking service owns the account balance. Persist the game only after it accepts the transaction.
        bankingClient.createTransaction(request.user(), netAmount);

        SlotGame game = new SlotGame(
                request.user(),
                bet,
                payout,
                netAmount,
                symbols,
                Instant.now(clock)
        );
        return mapper.toResponse(repository.save(game));
    }

    private static BigDecimal normalizeBet(BigDecimal bet) {
        Objects.requireNonNull(bet, "bet is required");
        try {
            BigDecimal normalized = bet.setScale(2, RoundingMode.UNNECESSARY);
            if (normalized.compareTo(new BigDecimal("0.01")) < 0
                    || normalized.compareTo(new BigDecimal("1000.00")) > 0) {
                throw new IllegalArgumentException("bet must be between 0.01 and 1000.00");
            }
            return normalized;
        } catch (ArithmeticException exception) {
            throw new IllegalArgumentException("bet must have at most two decimal places", exception);
        }
    }
}
