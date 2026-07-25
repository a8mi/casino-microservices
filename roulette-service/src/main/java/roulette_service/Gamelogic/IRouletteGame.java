package roulette_service.Gamelogic;

public interface IRouletteGame {

    void playGame();

    int[] getBet();

    boolean getIsWin();

    float getPayout();

    int getResult();

}
