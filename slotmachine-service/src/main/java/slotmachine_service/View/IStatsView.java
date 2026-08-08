package slotmachine_service.View;

import java.math.BigDecimal;

public interface IStatsView{
        long getTotalClientCount();
        long getTotalGamesCount();
        BigDecimal getTotalProfit();
        BigDecimal getTotalCashOut();
        BigDecimal getTotalTurnover();
}
