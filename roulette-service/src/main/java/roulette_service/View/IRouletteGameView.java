package roulette_service.View;
import java.math.BigDecimal;

import roulette_service.Gamelogic.ERouletteGameType;

public interface IRouletteGameView {
    Long getGameId();
    Long getUserId();
    BigDecimal getWager();
    ERouletteGameType getBetType();
    int getBallPosition();
    boolean getIsWin();
    BigDecimal getAmount();
    String getDate();
}