package banking_service.View.Stats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStatsResponse {
    private Long userId;
    private String firstName;
    private String lastName;
    private BigDecimal currentBalance;
    private long totalTransactions;
    private BigDecimal totalTurnover;
    private BigDecimal totalWinnings;
    private BigDecimal totalLosses;
    private BigDecimal netProfit;
}
