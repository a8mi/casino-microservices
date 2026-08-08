package slotmachine_service.View;

import java.math.BigDecimal;

public interface IUserStatsView{
        Long getClient();
        long getTotalGamesCount();
        BigDecimal getTotalWinnings();
        BigDecimal getTotalLosses();
        BigDecimal getTotalClientProfit();
        BigDecimal getTotalHouseTurnoverFromClient();
        BigDecimal getTotalHouseProfitFromClient();
}
