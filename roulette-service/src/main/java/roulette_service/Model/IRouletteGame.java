package roulette_service.Model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import roulette_service.Gamelogic.ERouletteGameType;

public interface IRouletteGame {
    Long getGameId();
    Long getUserId();
    BigDecimal getWager();
    int getResult();
    ERouletteGameType getBetType();
    int[] getBet();
    boolean getIsWin();
    BigDecimal getBetReturn();
    LocalDateTime getDate();
}
