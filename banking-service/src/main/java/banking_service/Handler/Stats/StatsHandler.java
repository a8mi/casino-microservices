package banking_service.Handler.Stats;

import banking_service.Model.Transactions.Transaction;
import banking_service.Model.User.User;
import banking_service.Repository.Transactions.TransactionRepository;
import banking_service.Repository.User.IUserRepository;
import banking_service.View.Stats.StatsResponse;
import banking_service.View.Stats.UserStatsResponse;
import banking_service.Utils.ErrorMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsHandler implements IStatsHandler {

    private final IUserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public StatsResponse getGlobalStats() {
        List<User> users = userRepository.findAll();
        List<Transaction> transactions = transactionRepository.findAll();

        long totalUsers = users.size();
        long totalTransactions = transactions.size();

        BigDecimal totalTurnover = transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalHouseProfit = transactions.stream()
                .map(Transaction::getAmount)
                .filter(amount -> amount.compareTo(BigDecimal.ZERO) > 0)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalClientLosses = transactions.stream()
                .map(Transaction::getAmount)
                .filter(amount -> amount.compareTo(BigDecimal.ZERO) < 0)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .abs();

        return new StatsResponse(
                totalUsers,
                totalTransactions,
                totalTurnover,
                totalHouseProfit,
                totalClientLosses
        );
    }

    public UserStatsResponse getUserStats(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(banking_service.Utils.ErrorMessages.userNotFound(userId)));

        List<Transaction> transactions = transactionRepository.findByUserId(userId);

        long totalTransactions = transactions.size();

        BigDecimal totalTurnover = transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalWinnings = transactions.stream()
                .map(Transaction::getAmount)
                .filter(amount -> amount.compareTo(BigDecimal.ZERO) > 0)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalLosses = transactions.stream()
                .map(Transaction::getAmount)
                .filter(amount -> amount.compareTo(BigDecimal.ZERO) < 0)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .abs();

        BigDecimal netProfit = totalWinnings.subtract(totalLosses);

        return new UserStatsResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBalance(),
                totalTransactions,
                totalTurnover,
                totalWinnings,
                totalLosses,
                netProfit
        );
    }
}
