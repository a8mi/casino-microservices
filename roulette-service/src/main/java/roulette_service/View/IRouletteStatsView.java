package roulette_service.View;

import java.math.BigDecimal;

public interface IRouletteStatsView {
    Long getTotalUsers();
    Long getTotalGames();
    BigDecimal getTotalProfit();
    BigDecimal getTotalCashout();
    BigDecimal getTotalTurnover();
}
