package roulette_service.View;

import java.math.BigDecimal;

public record RouletteUserStatsView(
    Long client,
    Long totalGamesCount,
    Long totalWinnings,
    Long totalLosses,
    BigDecimal totalClientProfit,
    BigDecimal totalHouseTurnoverFromClient,
    BigDecimal totalHouseProfitFromClient
) implements IRouletteUserStatsView{

    public static RouletteUserStatsView of(
    Long client,
    Long totalGamesCount,
    Long totalWinnings,
    Long totalLosses,
    BigDecimal totalClientProfit,
    BigDecimal totalHouseTurnoverFromClient,
    BigDecimal totalHouseProfitFromClient){
        
        return new RouletteUserStatsView(
            client,
            totalGamesCount,
            totalWinnings,
            totalLosses,
            totalClientProfit,
            totalHouseTurnoverFromClient,
            totalHouseProfitFromClient
        );
    }


    @Override
    public Long getClient() { return this.client; }

    @Override
    public Long getTotalGamesCount() { return this.totalGamesCount; }

    @Override
    public Long getTotalWinnings() { return this.totalWinnings; }

    @Override
    public Long getTotalLosses() { return this.totalLosses; }

    @Override
    public BigDecimal getTotalClientProfit() { return this.totalClientProfit;}

    @Override
    public BigDecimal getTotalHouseTurnoverFromClient() { return this.totalHouseTurnoverFromClient;}

    @Override
    public BigDecimal getTotalHouseProfitFromClient() { return this.totalHouseProfitFromClient;}
}
