package slotmachine_service.api;

import java.math.BigDecimal;

public record UserStatsResponse(
        Long client,
        long total_games_count,
        BigDecimal total_winnings,
        BigDecimal total_losses,
        BigDecimal total_client_profit,
        BigDecimal total_house_turnover_from_client,
        BigDecimal total_house_profit_from_client
) {
}
