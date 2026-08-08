package slotmachine_service.Handler;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.transaction.Transactional;
import slotmachine_service.Client.IHttpBankingClient;
import slotmachine_service.Exceptions.GameNotFoundException;
import slotmachine_service.Exceptions.InsufficientFundsException;
import slotmachine_service.GameLogic.ISymbolGenerator;
import slotmachine_service.GameLogic.PayoutPolicy;
import slotmachine_service.Model.SlotGame;
import slotmachine_service.Model.ISlotGame;
import slotmachine_service.Repository.ISlotGameRepository;
import slotmachine_service.Utils.GameChances;
import slotmachine_service.View.GameView;
import slotmachine_service.View.IGameView;
import slotmachine_service.View.PlayRequest;
import slotmachine_service.View.StatsView;
import slotmachine_service.View.UserStatsView;
import slotmachine_service.Model.ESlotSymbol;

public class SlotMachineHandler implements ISlotMachineHandler {

    private final ISlotGameRepository repository;
    private final IHttpBankingClient bankingClient;
    private final ISymbolGenerator symbolGenerator;
    private final PayoutPolicy payoutPolicy;
    private final Clock clock;

    public SlotMachineHandler(
            ISlotGameRepository repository,
            IHttpBankingClient bankingClient,
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

        public SlotMachineHandler(
            ISlotGameRepository repository,
            IHttpBankingClient bankingClient,
            ISymbolGenerator symbolGenerator,
            PayoutPolicy payoutPolicy
    )
    {
        this.repository = Objects.requireNonNull(repository);
        this.bankingClient = Objects.requireNonNull(bankingClient);
        this.symbolGenerator = Objects.requireNonNull(symbolGenerator);
        this.payoutPolicy = Objects.requireNonNull(payoutPolicy);
        this.clock = Clock.systemUTC();
    }


    @Transactional
    @Override
    public GameView playGame(PlayRequest request) {
                Objects.requireNonNull(request, "request is required");

        BigDecimal bet = normalizeBet(request.bet());
        IHttpBankingClient.UserAccount account = bankingClient.getUser(request.user());

        if (account.balance().compareTo(bet) < 0) {
            throw new InsufficientFundsException(request.user(), account.balance(), bet);
        }

        List<ESlotSymbol> symbols = symbolGenerator.spin();
        BigDecimal payout = payoutPolicy.calculatePayout(bet, symbols);
        BigDecimal netAmount = payoutPolicy.calculateNetAmount(bet, payout);

        // The banking service owns the account balance. Persist the game only after it accepts the transaction.
        bankingClient.createTransaction(request.user(), netAmount);

        ISlotGame game = new SlotGame(
                request.user(),
                bet,
                payout,
                netAmount,
                symbols,
                clock.instant()
        );
        return GameView.of(repository.save((SlotGame) game));
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
        return GameChances.getGameChances();
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
    public List<IGameView> getAllGames() {
        List <IGameView> result = new ArrayList<IGameView>();

        List<SlotGame> repositoryGames = repository.findAll();

        for (SlotGame game : repositoryGames){
                result.add(GameView.of(game));
        }

        return result;
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
