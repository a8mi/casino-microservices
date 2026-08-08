package slotmachine_service.Model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public interface ISlotGame {
    Long getGameId();
    
    Long getUserId();

    BigDecimal getWager();

    BigDecimal getPayout();

    BigDecimal getAmount();

    boolean isWinning();

    List<ESlotSymbol> getSymbols();
    
    Instant getPlayedAt();
}
