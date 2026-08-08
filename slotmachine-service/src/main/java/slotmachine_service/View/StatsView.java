package slotmachine_service.View;

import java.math.BigDecimal;

public record StatsView (
        long total_client_count,
        long total_games_count,
        BigDecimal total_profit,
        BigDecimal total_cash_out,
        BigDecimal total_turnover
) implements IStatsView {

        @Override
        public long getTotalClientCount() {return total_client_count;}

        @Override
        public long getTotalGamesCount() {return total_games_count;}

        @Override
        public BigDecimal getTotalProfit() {return total_profit;}

        @Override
        public BigDecimal getTotalCashOut() {return total_cash_out;}

        @Override
        public BigDecimal getTotalTurnover() {return total_turnover;}
        
}
