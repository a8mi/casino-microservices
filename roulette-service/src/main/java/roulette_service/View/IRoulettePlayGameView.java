package roulette_service.View;

public interface IRoulettePlayGameView {
    Long getUserId();
    String getBetType();
    int[] getBet();
    float getAmount();
    int getResult(); 
    boolean getIsWin();
    float getPay();
}
