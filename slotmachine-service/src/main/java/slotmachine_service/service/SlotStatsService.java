package slotmachine_service.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import slotmachine_service.api.GameResponse;
import slotmachine_service.api.OverallStatsResponse;
import slotmachine_service.api.UserStatsResponse;
import slotmachine_service.client.BankingClient;
import slotmachine_service.exception.GameNotFoundException;
import slotmachine_service.model.SlotGame;
import slotmachine_service.repository.SlotGameRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class SlotStatsService {

    private static final BigDecimal ZERO = BigDecimal.ZERO.setScale(2);

    private final SlotGameRepository repository;
    private final BankingClient bankingClient;
    private final SlotGameMapper mapper;

    public SlotStatsService(
            SlotGameRepository repository,
            BankingClient bankingClient,
            SlotGameMapper mapper
    ) {
        this.repository = repository;
        this.bankingClient = bankingClient;
        this.mapper = mapper;
    }

    public OverallStatsResponse getOverallStats() {
        List<SlotGame> games = repository.findAllByOrderByIdAsc();

        long clientCount = games.stream()
                .map(SlotGame::getUserId)
                .distinct()
                .count();
        BigDecimal clientNet = sumAmounts(games);
        BigDecimal cashOut = games.stream()
                .map(SlotGame::getPayout)
                .reduce(ZERO, BigDecimal::add);
        BigDecimal turnover = games.stream()
                .map(SlotGame::getBet)
                .reduce(ZERO, BigDecimal::add);

        return new OverallStatsResponse(
                clientCount,
                games.size(),
                clientNet.negate(),
                cashOut,
                turnover
        );
    }

    public UserStatsResponse getUserStats(Long userId) {
        validateUserId(userId);
        List<SlotGame> games = repository.findByUserIdOrderByIdAsc(userId);

        if (games.isEmpty()) {
            // Distinguish an existing player with no slot history from an unknown banking user.
            bankingClient.getUser(userId);
        }

        BigDecimal winnings = games.stream()
                .map(SlotGame::getAmount)
                .filter(amount -> amount.signum() > 0)
                .reduce(ZERO, BigDecimal::add);
        BigDecimal losses = games.stream()
                .map(SlotGame::getAmount)
                .filter(amount -> amount.signum() < 0)
                .map(BigDecimal::abs)
                .reduce(ZERO, BigDecimal::add);
        BigDecimal clientProfit = sumAmounts(games);
        BigDecimal turnover = games.stream()
                .map(SlotGame::getBet)
                .reduce(ZERO, BigDecimal::add);

        return new UserStatsResponse(
                userId,
                games.size(),
                winnings,
                losses,
                clientProfit,
                turnover,
                clientProfit.negate()
        );
    }

    public List<GameResponse> getGames() {
        return repository.findAllByOrderByIdAsc().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public GameResponse getGame(Long gameId) {
        validateGameId(gameId);
        return mapper.toResponse(repository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId)));
    }

    @Transactional
    public GameResponse deleteGame(Long gameId) {
        validateGameId(gameId);
        SlotGame game = repository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId));
        repository.delete(game);
        return mapper.toResponse(game);
    }

    private static BigDecimal sumAmounts(List<SlotGame> games) {
        return games.stream()
                .map(SlotGame::getAmount)
                .reduce(ZERO, BigDecimal::add);
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
