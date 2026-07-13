package slotmachine_service.api;

import java.math.BigDecimal;

public record OverallStatsResponse(
        long total_client_count,
        long total_games_count,
        BigDecimal total_profit,
        BigDecimal total_cash_out,
        BigDecimal total_turnover
) {
}
