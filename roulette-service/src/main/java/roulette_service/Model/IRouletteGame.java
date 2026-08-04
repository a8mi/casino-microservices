package roulette_service.Model;

import java.math.BigDecimal;

public interface IRouletteGame {
    Long getGameId();
    Long getUserId();
    BigDecimal getAmount();
    boolean getResult();


    void setGameId(Long gameId);
    void setUserId(Long userId);
    void setAmount(BigDecimal amount);
    void setResult(boolean result);
}
