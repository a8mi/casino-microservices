package roulette_service.Requests;

public interface IRouletteGameStartRequest {
    
    Long getUserId();

    String getBetType();

    int[] getBet();
    
    float getWager();
}
