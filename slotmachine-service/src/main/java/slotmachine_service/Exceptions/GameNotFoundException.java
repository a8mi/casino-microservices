package slotmachine_service.Exceptions;

public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException(Long gameId) {
        super("Slot game not found: " + gameId);
    }
}
