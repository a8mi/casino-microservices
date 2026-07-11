package banking_service.Utils;

import banking_service.View.Transactions.TransactionResponse;
import banking_service.View.User.UserView;

import java.math.BigDecimal;
import java.util.List;

public interface IInternalBankingClient {

    boolean userExists(Long id);

    UserView getUser(Long id);

    List<UserView> getAllUsers();

    List<TransactionResponse> getAllTransactions();

    List<TransactionResponse> getTransactionsByUserId(Long userId);

    void depositToUser(Long id, BigDecimal amount);
}