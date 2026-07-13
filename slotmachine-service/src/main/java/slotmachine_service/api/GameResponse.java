package slotmachine_service.api;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record GameResponse(
        Long id,
        Long user,
        boolean winning,
        BigDecimal amount,
        BigDecimal bet,
        BigDecimal payout,
        List<String> slot_states,
        Instant played_at
) {
}
