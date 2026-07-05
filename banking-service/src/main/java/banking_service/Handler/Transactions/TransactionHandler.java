package banking_service.Handler.Transactions;

import banking_service.Model.Transactions.Transaction;
import banking_service.Model.User.User;
import banking_service.Repository.Transactions.TransactionRepository;
import banking_service.Repository.User.IUserRepository;
import banking_service.View.Transactions.TransactionRequest;
import banking_service.View.Transactions.TransactionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionHandler implements ITransactionHandler {

    private final TransactionRepository transactionRepository;
    private final IUserRepository userRepository;

    public List<TransactionResponse> getAllTransactions() {
        return transactionRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<TransactionResponse> getTransactionsByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found: " + userId);
        }
        return transactionRepository.findByUserId(userId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public TransactionResponse createTransaction(Long userId, TransactionRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        Transaction transaction = new Transaction();
        transaction.setUserId(userId);
        transaction.setInvoicingParty(request.getInvoicingParty());
        transaction.setAmount(request.getAmount());

        user.setBalance(user.getBalance().add(request.getAmount()));
        userRepository.save(user);

        return toResponse(transactionRepository.save(transaction));
    }

    public TransactionResponse updateTransaction(Long transactionId, TransactionRequest request) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found: " + transactionId));

        User user = userRepository.findById(transaction.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found: " + transaction.getUserId()));

        user.setBalance(user.getBalance().subtract(transaction.getAmount()));
        transaction.setInvoicingParty(request.getInvoicingParty());
        transaction.setAmount(request.getAmount());
        user.setBalance(user.getBalance().add(request.getAmount()));

        userRepository.save(user);
        return toResponse(transactionRepository.save(transaction));
    }

    public TransactionResponse deleteTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found: " + transactionId));

        User user = userRepository.findById(transaction.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found: " + transaction.getUserId()));

        user.setBalance(user.getBalance().subtract(transaction.getAmount()));
        userRepository.save(user);

        transactionRepository.delete(transaction);
        return toResponse(transaction);
    }

    private TransactionResponse toResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getUserId(),
                transaction.getInvoicingParty(),
                transaction.getAmount()
        );
    }
}