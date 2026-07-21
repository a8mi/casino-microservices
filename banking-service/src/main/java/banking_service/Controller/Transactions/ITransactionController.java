package banking_service.Controller.Transactions;

import banking_service.View.Transactions.TransactionRequest;
import banking_service.View.Transactions.TransactionResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/casino/bank/api")
public interface ITransactionController {

    @GetMapping("/transactions")
    List<TransactionResponse> getAllTransactions();

    @GetMapping("/transactions/user/{id}")
    List<TransactionResponse> getTransactionsByUserId(@PathVariable Long id);

    @PostMapping("/transaction/user/{user_id}")
    @ResponseStatus(HttpStatus.CREATED)
    TransactionResponse createTransaction(@PathVariable("user_id") Long userId,
                                          @Valid @RequestBody TransactionRequest request);

    @PutMapping("/transaction/{transaction_id}")
    TransactionResponse updateTransaction(@PathVariable("transaction_id") Long transactionId,
                                          @Valid @RequestBody TransactionRequest request);

    @DeleteMapping("/transaction/{transaction_id}")
    TransactionResponse deleteTransaction(@PathVariable("transaction_id") Long transactionId);
}