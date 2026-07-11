package banking_service.Model.Transactions;

import java.math.BigDecimal;

public interface ITransactionFactory {
    ITransaction create(Long userId, String invoicingParty, BigDecimal amount);
}