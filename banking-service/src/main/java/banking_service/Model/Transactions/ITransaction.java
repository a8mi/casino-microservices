package banking_service.Model.Transactions;

import java.math.BigDecimal;

public interface ITransaction {
    Long getId();
    Long getUserId();
    String getInvoicingParty();
    BigDecimal getAmount();
}