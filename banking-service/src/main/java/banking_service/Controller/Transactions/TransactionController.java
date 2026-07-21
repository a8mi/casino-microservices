package banking_service.Controller.Transactions;

import banking_service.Handler.Transactions.ITransactionHandler;
import banking_service.View.Transactions.TransactionRequest;
import banking_service.View.Transactions.TransactionResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController implements ITransactionController {

    private final ITransactionHandler transactionHandler;

    public TransactionController(ITransactionHandler transactionHandler) {
        this.transactionHandler = transactionHandler;
    }

    @Override
    public List<TransactionResponse> getAllTransactions() {
        return transactionHandler.getAllTransactions();
    }

    @Override
    public List<TransactionResponse> getTransactionsByUserId(Long id) {
        return transactionHandler.getTransactionsByUserId(id);
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse createTransaction(Long userId, TransactionRequest request) {
        return transactionHandler.createTransaction(userId, request);
    }

    @Override
    public TransactionResponse updateTransaction(Long transactionId, TransactionRequest request) {
        return transactionHandler.updateTransaction(transactionId, request);
    }

    @Override
    public TransactionResponse deleteTransaction(Long transactionId) {
        return transactionHandler.deleteTransaction(transactionId);
    }
}