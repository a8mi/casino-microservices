package roulette_service.Gamelogic;

public interface IRouletteGameLogic {

    void playGame();

    int[] getBet();

    boolean getIsWin();

    float getPayout();

    int getResult();

}
