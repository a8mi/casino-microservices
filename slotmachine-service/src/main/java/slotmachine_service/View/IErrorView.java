package slotmachine_service.View;

import java.time.Instant;

public interface IErrorView {
    Instant getTimestamp();
    int getStatus();
    String getError();
    String getMessage();
    String getPath();
}
