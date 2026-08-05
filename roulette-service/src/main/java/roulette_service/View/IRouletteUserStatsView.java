package roulette_service.View;

import java.math.BigDecimal;

public interface IRouletteUserStatsView {
    Long getClient();
    Long getTotalGamesCount();
    Long getTotalWinnings();
    Long getTotalLosses();
    BigDecimal getTotalClientProfit();
    BigDecimal getTotalHouseTurnoverFromClient();
    BigDecimal getTotalHouseProfitFromClient();
}
