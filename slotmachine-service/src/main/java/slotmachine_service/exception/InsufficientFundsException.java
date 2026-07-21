package slotmachine_service.exception;

import java.math.BigDecimal;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(Long userId, BigDecimal balance, BigDecimal bet) {
        super("User %d has balance %s but bet %s is required".formatted(userId, balance, bet));
    }
}
