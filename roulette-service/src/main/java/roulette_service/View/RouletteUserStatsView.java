package roulette_service.View;

import java.math.BigDecimal;

public record RouletteUserStatsView(
    Long client,
    Long total_games_count,
    Long total_winnings,
    Long total_losses,
    BigDecimal total_client_profit,
    BigDecimal total_house_turnover_from_client,
    BigDecimal total_house_profit_from_client
) implements IRouletteUserStatsView{

    public static RouletteUserStatsView of(
    Long client,
    Long total_games_count,
    Long total_winnings,
    Long total_losses,
    BigDecimal total_client_profit,
    BigDecimal total_house_turnover_from_client,
    BigDecimal total_house_profit_from_client){
        
        return new RouletteUserStatsView(
            client,
            total_games_count,
            total_winnings,
            total_losses,
            total_client_profit,
            total_house_turnover_from_client,
            total_house_profit_from_client
        );
    }


    @Override
    public Long getClient() { return this.client; }

    @Override
    public Long getTotalGamesCount() { return this.total_games_count; }

    @Override
    public Long getTotalWinnings() { return this.total_winnings; }

    @Override
    public Long getTotalLosses() { return this.total_losses; }

    @Override
    public BigDecimal getTotalClientProfit() { return this.total_client_profit;}

    @Override
    public BigDecimal getTotalHouseTurnoverFromClient() { return this.total_house_turnover_from_client;}

    @Override
    public BigDecimal getTotalHouseProfitFromClient() { return this.total_house_profit_from_client;}
}
