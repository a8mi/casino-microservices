package roulette_service.View;

import java.math.BigDecimal;

public record RouletteStatsView(Long total_users, Long total_games, BigDecimal total_profit, BigDecimal total_cashout, BigDecimal total_turnover) implements IRouletteStatsView{

        public static IRouletteStatsView of (Long total_users, Long total_games, BigDecimal total_profit, BigDecimal total_cashout, BigDecimal total_turnover) {
            return new RouletteStatsView(total_users, total_games, total_profit, total_cashout, total_turnover);
        }

    @Override
    public Long getTotalUsers() { return this.total_users; }

    @Override
    public Long getTotalGames() { return this.total_games; }

    @Override
    public BigDecimal getTotalProfit() { return this.total_profit; }

    @Override
    public BigDecimal getTotalCashout() { return this.total_cashout; }

    @Override
    public BigDecimal getTotalTurnover() { return this.total_turnover; }
    
}
