package slotmachine_service.Exceptions;

public class BankingServiceException extends RuntimeException {
    public BankingServiceException(String message) {
        super(message);
    }

    public BankingServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
