package slotmachine_service.View;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public interface IGameView {
        Long getId();
        Long getUser();
        boolean getWinning();
        BigDecimal getAmount();
        BigDecimal getWager();
        BigDecimal getPayout();
        List<String> getSlotStates();
        Instant getPlayedAt();
    
}
