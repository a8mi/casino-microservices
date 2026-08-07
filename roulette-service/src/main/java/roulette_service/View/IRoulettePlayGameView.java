package roulette_service.View;

import java.math.BigDecimal;

import roulette_service.Gamelogic.ERouletteGameType;

public interface IRoulettePlayGameView {
    Long getUserId();
    ERouletteGameType getBetType();
    int[] getBet();
    BigDecimal getWager();
    int getBallPosition(); 
    boolean getIsWin();
    BigDecimal getAmount();
}
