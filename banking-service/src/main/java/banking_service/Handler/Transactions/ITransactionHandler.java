package banking_service.Handler.Transactions;

import banking_service.View.Transactions.TransactionRequest;
import banking_service.View.Transactions.TransactionResponse;

import java.util.List;

public interface ITransactionHandler {
    List<TransactionResponse> getAllTransactions();
    List<TransactionResponse> getTransactionsByUserId(Long userId);
    TransactionResponse createTransaction(Long userId, TransactionRequest request);
    TransactionResponse updateTransaction(Long transactionId, TransactionRequest request);
    TransactionResponse deleteTransaction(Long transactionId);
}