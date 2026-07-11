package banking_service.Handler.Transactions;

import banking_service.Model.Transactions.ITransaction;
import banking_service.Model.Transactions.ITransactionFactory;
import banking_service.Model.Transactions.Transaction;
import banking_service.Model.User.User;
import banking_service.Repository.Transactions.TransactionRepository;
import banking_service.Repository.User.IUserRepository;
import banking_service.Utils.ErrorMessages;
import banking_service.View.Transactions.TransactionRequest;
import banking_service.View.Transactions.TransactionResponse;

import java.util.List;
import java.util.stream.Collectors;

public class TransactionHandler implements ITransactionHandler {

    private final TransactionRepository transactionRepository;
    private final IUserRepository userRepository;
    private final ITransactionFactory transactionFactory;

    public TransactionHandler(
            TransactionRepository transactionRepository,
            IUserRepository userRepository,
            ITransactionFactory transactionFactory) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.transactionFactory = transactionFactory;
    }

    @Override
    public List<TransactionResponse> getAllTransactions() {
        return transactionRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionResponse> getTransactionsByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException(ErrorMessages.userNotFound(userId));
        }
        return transactionRepository.findByUserId(userId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionResponse createTransaction(Long userId, TransactionRequest request) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException(ErrorMessages.userNotFound(userId));
        }

        ITransaction transaction = transactionFactory.create(
                userId, request.getInvoicingParty(), request.getAmount());

        return toResponse(transactionRepository.save((Transaction) transaction));
    }

    @Override
    public TransactionResponse updateTransaction(Long transactionId, TransactionRequest request) {
        Transaction existing = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException(ErrorMessages.transactionNotFound(transactionId)));

        User user = userRepository.findById(existing.getUserId())
                .orElseThrow(() -> new RuntimeException(ErrorMessages.userNotFound(existing.getUserId())));

        user.setBalance(user.getBalance().subtract(existing.getAmount()));
        existing.setInvoicingParty(request.getInvoicingParty());
        existing.setAmount(request.getAmount());
        user.setBalance(user.getBalance().add(existing.getAmount()));

        userRepository.save(user);
        return toResponse(transactionRepository.save(existing));
    }

    @Override
    public TransactionResponse deleteTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException(ErrorMessages.transactionNotFound(transactionId)));

        User user = userRepository.findById(transaction.getUserId())
                .orElseThrow(() -> new RuntimeException(ErrorMessages.userNotFound(transaction.getUserId())));

        user.setBalance(user.getBalance().subtract(transaction.getAmount()));
        userRepository.save(user);

        transactionRepository.delete(transaction);
        return toResponse(transaction);
    }

    private TransactionResponse toResponse(ITransaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getUserId(),
                transaction.getInvoicingParty(),
                transaction.getAmount()
        );
    }
}