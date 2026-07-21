package slotmachine_service.exception;

public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException(Long gameId) {
        super("Slot game not found: " + gameId);
    }
}
