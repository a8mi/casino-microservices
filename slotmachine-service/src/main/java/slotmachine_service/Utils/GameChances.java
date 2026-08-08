package slotmachine_service.Utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Locale;

import slotmachine_service.Model.ESlotSymbol;

public class GameChances {
    public static String getGameChances(){
        
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
}
