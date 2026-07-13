package slotmachine_service.service;

import org.springframework.stereotype.Service;
import slotmachine_service.model.SlotSymbol;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Locale;

@Service
public class GameInfoService {

    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);
    private static final MathContext MC = new MathContext(12, RoundingMode.HALF_UP);

    public String rules() {
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

    public String chances() {
        StringBuilder table = new StringBuilder("""
                SYMBOL   REEL CHANCE   THREE-OF-A-KIND   PAYOUT
                """);

        BigDecimal returnToPlayer = BigDecimal.ZERO;
        int totalWeight = Arrays.stream(SlotSymbol.values()).mapToInt(SlotSymbol::weight).sum();

        for (SlotSymbol symbol : SlotSymbol.values()) {
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

        BigDecimal hitRate = Arrays.stream(SlotSymbol.values())
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
}
