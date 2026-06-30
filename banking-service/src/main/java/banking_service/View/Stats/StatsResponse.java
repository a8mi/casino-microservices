package banking_service.View.Stats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatsResponse {
    private long totalUsers;
    private long totalTransactions;
    private BigDecimal totalTurnover;
    private BigDecimal totalHouseProfit;
    private BigDecimal totalClientLosses;
}
