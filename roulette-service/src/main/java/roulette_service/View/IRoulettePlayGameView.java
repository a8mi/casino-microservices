package roulette_service.View;

import roulette_service.Gamelogic.ERouletteGameType;

public interface IRoulettePlayGameView {
    Long getUserId();
    ERouletteGameType getBetType();
    int[] getBet();
    float getWager();
    int getResult(); 
    boolean getIsWin();
    float getBetReturn();
}
