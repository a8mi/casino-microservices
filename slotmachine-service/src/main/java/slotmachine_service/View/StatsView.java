package slotmachine_service.View;

import java.math.BigDecimal;

public record StatsView(
        long total_client_count,
        long total_games_count,
        BigDecimal total_profit,
        BigDecimal total_cash_out,
        BigDecimal total_turnover
) 
{
}
