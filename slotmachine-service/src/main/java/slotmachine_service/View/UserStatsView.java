package slotmachine_service.View;

import java.math.BigDecimal;

public record UserStatsView(
        Long client,
        long total_games_count,
        BigDecimal total_winnings,
        BigDecimal total_losses,
        BigDecimal total_client_profit,
        BigDecimal total_house_turnover_from_client,
        BigDecimal total_house_profit_from_client
) implements IUserStatsView{

        @Override
        public Long getClient() {return client; }

        @Override
        public long getTotalGamesCount() {return total_games_count; }

        @Override
        public BigDecimal getTotalWinnings() {return total_winnings; }

        @Override
        public BigDecimal getTotalLosses() {return total_losses; }

        @Override
        public BigDecimal getTotalClientProfit() {return total_client_profit;}

        @Override
        public BigDecimal getTotalHouseTurnoverFromClient() {return total_house_turnover_from_client; }

        @Override
        public BigDecimal getTotalHouseProfitFromClient() {return total_house_profit_from_client; }
}
