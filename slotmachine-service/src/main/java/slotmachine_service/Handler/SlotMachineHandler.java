package slotmachine_service.Handler;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.Clock;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import jakarta.transaction.Transactional;
import slotmachine_service.Client.BankingClient;
import slotmachine_service.Exceptions.GameNotFoundException;
import slotmachine_service.Exceptions.InsufficientFundsException;
import slotmachine_service.GameLogic.ISymbolGenerator;
import slotmachine_service.GameLogic.PayoutPolicy;
import slotmachine_service.Model.SlotGame;
import slotmachine_service.Repository.ISlotGameRepository;
import slotmachine_service.View.GameView;
import slotmachine_service.View.StatsView;
import slotmachine_service.View.PlayRequest;
import slotmachine_service.View.UserStatsView;
import slotmachine_service.Model.ESlotSymbol;

public class SlotMachineHandler implements ISlotMachineHandler {

    private final ISlotGameRepository repository;
    private final BankingClient bankingClient;
    private final ISymbolGenerator symbolGenerator;
    private final PayoutPolicy payoutPolicy;
    private final Clock clock;

    public SlotMachineHandler(
            ISlotGameRepository repository,
            BankingClient bankingClient,
            ISymbolGenerator symbolGenerator,
            PayoutPolicy payoutPolicy,
            Clock clock
    )
    {
        this.repository = Objects.requireNonNull(repository);
        this.bankingClient = Objects.requireNonNull(bankingClient);
        this.symbolGenerator = Objects.requireNonNull(symbolGenerator);
        this.payoutPolicy = Objects.requireNonNull(payoutPolicy);
        this.clock = Objects.requireNonNull(clock);
    }

    @Transactional
    @Override
    public GameView playGame(PlayRequest request) {
                Objects.requireNonNull(request, "request is required");

        BigDecimal bet = normalizeBet(request.bet());
        BankingClient.UserAccount account = bankingClient.getUser(request.user());

        if (account.balance().compareTo(bet) < 0) {
            throw new InsufficientFundsException(request.user(), account.balance(), bet);
        }

        List<ESlotSymbol> symbols = symbolGenerator.spin();
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
        return GameView.of(repository.save(game));
    }

    @Override
    public String getRules(){
        return """
                SLOT MACHINE RULES
                1. Submit a positive bet from 0.01 to 1000.00 and an existing banking user id.
                2. Three independent reels are spun.
                3. A payout is awarded only when all three symbols are identical.
                4. The response amount is the player's net account change: payout minus bet.
                5. The banking service validates the user and applies that net transaction.
                6. Deleting a stat entry removes history only; it does not reverse the banking transaction.
                """;
    }

    @Override
    public String getChances() {

        BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);
        MathContext MC = new MathContext(12, RoundingMode.HALF_UP);

        StringBuilder table = new StringBuilder("""
                SYMBOL   REEL CHANCE   THREE-OF-A-KIND   PAYOUT
                """);

        BigDecimal returnToPlayer = BigDecimal.ZERO;
        int totalWeight = Arrays.stream(ESlotSymbol.values()).mapToInt(ESlotSymbol::weight).sum();

        for (ESlotSymbol symbol : ESlotSymbol.values()) {
            BigDecimal reelChance = BigDecimal.valueOf(symbol.weight())
                    .divide(BigDecimal.valueOf(totalWeight), MC);
            BigDecimal tripleChance = reelChance.pow(3, MC);
            returnToPlayer = returnToPlayer.add(
                    tripleChance.multiply(symbol.payoutMultiplier(), MC),
                    MC
            );

            table.append(String.format(
                    Locale.ROOT,
                    "%-8s %10.2f%% %16.4f%% %8sx%n",
                    symbol.name(),
                    reelChance.multiply(ONE_HUNDRED).doubleValue(),
                    tripleChance.multiply(ONE_HUNDRED).doubleValue(),
                    symbol.payoutMultiplier().stripTrailingZeros().toPlainString()
            ));
        }

        BigDecimal hitRate = Arrays.stream(ESlotSymbol.values())
                .map(symbol -> BigDecimal.valueOf(symbol.weight())
                        .divide(BigDecimal.valueOf(totalWeight), MC)
                        .pow(3, MC))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal houseEdge = BigDecimal.ONE.subtract(returnToPlayer, MC);

        table.append(String.format(
                Locale.ROOT,
                "%nFormula: P(three identical symbols) = (symbol weight / total weight)^3%n" +
                        "Hit rate: %.4f%%%n" +
                        "Theoretical RTP: %.4f%%%n" +
                        "Theoretical house edge: %.4f%%%n",
                hitRate.multiply(ONE_HUNDRED).doubleValue(),
                returnToPlayer.multiply(ONE_HUNDRED).doubleValue(),
                houseEdge.multiply(ONE_HUNDRED).doubleValue()
        ));

        return table.toString();
    }

    @Override
    public StatsView getStats() {
        List<SlotGame> games = repository.findAllByOrderByIdAsc();

        long clientCount = games.stream()
                .map(SlotGame::getUserId)
                .distinct()
                .count();
        BigDecimal clientNet = sumAmounts(games);
        BigDecimal cashOut = games.stream()
                .map(SlotGame::getPayout)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal turnover = games.stream()
                .map(SlotGame::getWager)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new StatsView(
                clientCount,
                games.size(),
                clientNet.negate(),
                cashOut,
                turnover
        );}

    @Override
    public UserStatsView getUserStatsById(Long userId) {
        
        validateUserId(userId);
        List<SlotGame> games = repository.findByUserIdOrderByIdAsc(userId);

        if (games.isEmpty()) {
            // Distinguish an existing player with no slot history from an unknown banking user.
            bankingClient.getUser(userId);
        }

        BigDecimal winnings = games.stream()
                .map(SlotGame::getAmount)
                .filter(amount -> amount.signum() > 0)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal losses = games.stream()
                .map(SlotGame::getAmount)
                .filter(amount -> amount.signum() < 0)
                .map(BigDecimal::abs)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal clientProfit = sumAmounts(games);
        BigDecimal turnover = games.stream()
                .map(SlotGame::getWager)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new UserStatsView(
                userId,
                games.size(),
                winnings,
                losses,
                clientProfit,
                turnover,
                clientProfit.negate()
        );
    }

    @Override
    public List<GameView> getAllGames() {
        return repository.findAllByOrderByIdAsc().stream()
                .map(GameView::of)
                .toList();
    }

    @Override
    public GameView getGameById(Long gameId) {
        validateGameId(gameId);
        return GameView.of(repository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId)));
    }

    @Override
    public GameView deleteGame(Long gameId) {
        validateGameId(gameId);
        SlotGame game = repository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId));
        repository.delete(game);
        return GameView.of(game);
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

    private static BigDecimal sumAmounts(List<SlotGame> games) {
        return games.stream()
                .map(SlotGame::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static void validateUserId(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("userId must be positive");
        }
    }

    private static void validateGameId(Long gameId) {
        if (gameId == null || gameId <= 0) {
            throw new IllegalArgumentException("gameId must be positive");
        }
    }
    
}
