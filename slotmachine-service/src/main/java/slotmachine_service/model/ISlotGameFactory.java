package slotmachine_service.Model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public interface ISlotGameFactory {
      ISlotGame create (           
            Long userId,
            BigDecimal bet,
            BigDecimal payout,
            BigDecimal amount,
            List<ESlotSymbol> symbols,
            Instant playedAt);
}
