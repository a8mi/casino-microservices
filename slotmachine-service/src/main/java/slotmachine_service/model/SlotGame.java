package slotmachine_service.Model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Entity
@Table(
        name = "slot_games",
        indexes = {
                @Index(name = "idx_slot_games_user_id", columnList = "user_id"),
                @Index(name = "idx_slot_games_played_at", columnList = "played_at")
        }
)
public class SlotGame implements ISlotGame{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal wager;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal payout;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private boolean winning;

    @Enumerated(EnumType.STRING)
    @Column(name = "reel_one", nullable = false, length = 16)
    private ESlotSymbol reelOne;

    @Enumerated(EnumType.STRING)
    @Column(name = "reel_two", nullable = false, length = 16)
    private ESlotSymbol reelTwo;

    @Enumerated(EnumType.STRING)
    @Column(name = "reel_three", nullable = false, length = 16)
    private ESlotSymbol reelThree;

    @Column(name = "played_at", nullable = false)
    private Instant playedAt;

    protected SlotGame() {
        // Required by JPA.
    }

    public SlotGame(
            Long userId,
            BigDecimal bet,
            BigDecimal payout,
            BigDecimal amount,
            List<ESlotSymbol> symbols,
            Instant playedAt
    ) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("userId must be positive");
        }
        if (symbols == null || symbols.size() != 3 || symbols.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("exactly three non-null symbols are required");
        }

        this.userId = userId;
        this.wager = money(bet, "bet");
        this.payout = money(payout, "payout");
        this.amount = money(amount, "amount");
        this.playedAt = Objects.requireNonNull(playedAt, "playedAt is required");

        if (this.wager.signum() <= 0) {
            throw new IllegalArgumentException("bet must be positive");
        }
        if (this.payout.signum() < 0) {
            throw new IllegalArgumentException("payout cannot be negative");
        }
        if (this.amount.compareTo(this.payout.subtract(this.wager)) != 0) {
            throw new IllegalArgumentException("amount must equal payout minus bet");
        }

        boolean triple = symbols.stream().allMatch(symbols.get(0)::equals);
        BigDecimal expectedPayout = triple
                ? this.wager.multiply(symbols.get(0).payoutMultiplier()).setScale(2, RoundingMode.UNNECESSARY)
                : BigDecimal.ZERO.setScale(2);
        if (this.payout.compareTo(expectedPayout) != 0) {
            throw new IllegalArgumentException("payout does not match the symbols and payout table");
        }

        this.reelOne = symbols.get(0);
        this.reelTwo = symbols.get(1);
        this.reelThree = symbols.get(2);
        this.winning = this.amount.signum() > 0;
    }

    private static BigDecimal money(BigDecimal value, String name) {
        Objects.requireNonNull(value, name + " is required");
        try {
            return value.setScale(2, RoundingMode.UNNECESSARY);
        } catch (ArithmeticException exception) {
            throw new IllegalArgumentException(name + " must have at most two decimal places", exception);
        }
    }

    public Long getGameId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public BigDecimal getWager() {
        return wager;
    }

    public BigDecimal getPayout() {
        return payout;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public boolean isWinning() {
        return winning;
    }

    public List<ESlotSymbol> getSymbols() {
        return List.of(reelOne, reelTwo, reelThree);
    }

    public Instant getPlayedAt() {
        return playedAt;
    }

    public static ISlotGame create(
            Long userId,
            BigDecimal bet,
            BigDecimal payout,
            BigDecimal amount,
            List<ESlotSymbol> symbols,
            Instant playedAt) {
            
            return new SlotGame(userId, bet, payout, amount, symbols, playedAt);
    }
}
