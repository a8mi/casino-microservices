package slotmachine_service.GameLogic;

import org.springframework.stereotype.Component;

import slotmachine_service.Model.ESlotSymbol;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

@Component
public class PayoutPolicy {

    public BigDecimal calculatePayout(BigDecimal bet, List<ESlotSymbol> symbols) {
        BigDecimal normalizedBet = normalizeBet(bet);
        validateSymbols(symbols);

        boolean allEqual = symbols.stream().allMatch(symbols.get(0)::equals);
        if (!allEqual) {
            return BigDecimal.ZERO.setScale(2);
        }

        return normalizedBet
                .multiply(symbols.get(0).payoutMultiplier())
                .setScale(2, RoundingMode.UNNECESSARY);
    }

    public BigDecimal calculateNetAmount(BigDecimal bet, BigDecimal payout) {
        BigDecimal normalizedBet = normalizeBet(bet);
        Objects.requireNonNull(payout, "payout is required");

        BigDecimal normalizedPayout;
        try {
            normalizedPayout = payout.setScale(2, RoundingMode.UNNECESSARY);
        } catch (ArithmeticException exception) {
            throw new IllegalArgumentException("payout must have at most two decimal places", exception);
        }

        if (normalizedPayout.signum() < 0) {
            throw new IllegalArgumentException("payout cannot be negative");
        }
        return normalizedPayout.subtract(normalizedBet).setScale(2);
    }

    private static BigDecimal normalizeBet(BigDecimal bet) {
        Objects.requireNonNull(bet, "bet is required");
        try {
            BigDecimal normalized = bet.setScale(2, RoundingMode.UNNECESSARY);
            if (normalized.signum() <= 0) {
                throw new IllegalArgumentException("bet must be positive");
            }
            return normalized;
        } catch (ArithmeticException exception) {
            throw new IllegalArgumentException("bet must have at most two decimal places", exception);
        }
    }

    private static void validateSymbols(List<ESlotSymbol> symbols) {
        if (symbols == null || symbols.size() != 3 || symbols.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("exactly three non-null symbols are required");
        }
    }
}
