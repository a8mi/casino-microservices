package banking_service.View.Transactions;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
    @NotBlank(message = "invoicing_party is required")
    private String invoicingParty;

    @NotNull(message = "amount is required")
    @DecimalMin(value = "-999999.99", message = "amount must be >= -999999.99")
    private BigDecimal amount;
}