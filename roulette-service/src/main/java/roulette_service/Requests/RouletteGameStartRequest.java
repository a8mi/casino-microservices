package roulette_service.Requests;

public record RouletteGameStartRequest(Long userId, String type, int[] bet, float amount) implements IRouletteGameStartRequest{
   
    @Override
    public Long getUserId() {
        return userId;
    } 

    @Override
    public String getBetType() {
       return type;
    }

    @Override
    public int[] getBet() {
        return bet;
    }

    @Override
    public float getAmount() {
        return amount;
    }

}
