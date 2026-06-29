package roulette_service.Requests;

public interface IRouletteGameStartRequest {
    
    String getBetType();

    int[] getBet();
    
    float getAmount();
}
