package slotmachine_service.Client;

import java.math.BigDecimal;

public interface BankingClient {

    UserAccount getUser(Long userId);

    void createTransaction(Long userId, BigDecimal amount);

    record UserAccount(Long id, BigDecimal balance) {
        public UserAccount {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("banking user id must be positive");
            }
            if (balance == null) {
                throw new IllegalArgumentException("banking balance is required");
            }
        }
    }
}
