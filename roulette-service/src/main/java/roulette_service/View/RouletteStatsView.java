package roulette_service.View;

import java.math.BigDecimal;

public record RouletteStatsView(Long totalUsers, Long totalGames, BigDecimal totalProfit, BigDecimal totalCashout, BigDecimal totalTurnover) implements IRouletteStatsView{

        public static IRouletteStatsView of (Long totalUsers, Long totalGames, BigDecimal totalProfit, BigDecimal totalCashout, BigDecimal totalTurnover) {
            return new RouletteStatsView(totalUsers, totalGames, totalProfit, totalCashout, totalTurnover);
        }

    @Override
    public Long getTotalUsers() { return this.totalUsers; }

    @Override
    public Long getTotalGames() { return this.totalGames; }

    @Override
    public BigDecimal getTotalProfit() { return this.totalProfit; }

    @Override
    public BigDecimal getTotalCashout() { return this.totalCashout; }

    @Override
    public BigDecimal getTotalTurnover() { return this.totalTurnover; }
    
}
