package banking_service.Model.Transactions;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TransactionFactory implements ITransactionFactory {

    @Override
    public ITransaction create(Long userId, String invoicingParty, BigDecimal amount) {
        return Transaction.create(userId, invoicingParty, amount);
    }
}