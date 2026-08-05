package roulette_service.View;
import java.math.BigDecimal;

import roulette_service.Gamelogic.ERouletteGameType;

public interface IRouletteGameView {
    Long getGameId();
    Long getUserId();
    BigDecimal getWager();
    ERouletteGameType getBetType();
    boolean getIsWin();
    BigDecimal getBetReturn();
    String getDate();
}