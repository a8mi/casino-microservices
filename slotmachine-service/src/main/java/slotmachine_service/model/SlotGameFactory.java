package slotmachine_service.Model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class SlotGameFactory implements ISlotGameFactory {

      @Override
      public ISlotGame create(
            Long userId,
            BigDecimal bet,
            BigDecimal payout,
            BigDecimal amount,
            List<ESlotSymbol> symbols,
            Instant playedAt) {
            
            return SlotGame.create(userId, bet, payout, amount, symbols, playedAt);
      }
      
      
}
