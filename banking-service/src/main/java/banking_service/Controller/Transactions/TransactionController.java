package banking_service.Controller.Transactions;

import banking_service.Handler.Transactions.TransactionHandler;
import banking_service.View.Transactions.TransactionRequest;
import banking_service.View.Transactions.TransactionResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionHandler transactionHandler;

    @GetMapping("/transactions")
    public List<TransactionResponse> getAllTransactions() {
        return transactionHandler.getAllTransactions();
    }

    @GetMapping("/transactions/user/{id}")
    public List<TransactionResponse> getTransactionsByUserId(@PathVariable Long id) {
        return transactionHandler.getTransactionsByUserId(id);
    }

    @PostMapping("/transaction/user/{user_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse createTransaction(
            @PathVariable("user_id") Long userId,
            @Valid @RequestBody TransactionRequest request) {
        return transactionHandler.createTransaction(userId, request);
    }

    @PutMapping("/transaction/{transaction_id}")
    public TransactionResponse updateTransaction(
            @PathVariable("transaction_id") Long transactionId,
            @Valid @RequestBody TransactionRequest request) {
        return transactionHandler.updateTransaction(transactionId, request);
    }

    @DeleteMapping("/transaction/{transaction_id}")
    public TransactionResponse deleteTransaction(@PathVariable("transaction_id") Long transactionId) {
        return transactionHandler.deleteTransaction(transactionId);
    }
}