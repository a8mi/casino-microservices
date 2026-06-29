package roulette_service.View;

public interface IRouletteGameView {
    String getBetType();
    int[] getBet();
    float getAmount();
    int getResult(); 
    boolean getIsWin();
    float getPay();
}
