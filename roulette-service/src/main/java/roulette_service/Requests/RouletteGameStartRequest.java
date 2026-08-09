package roulette_service.Requests;

public record RouletteGameStartRequest(Long user, String bet_type, int[] bet, float wager) implements IRouletteGameStartRequest{
   
    @Override
    public Long getUserId() {
        return user;
    } 

    @Override
    public String getBetType() {
       return bet_type;
    }

    @Override
    public int[] getBet() {
        return bet;
    }

    @Override
    public float getWager() {
        return wager;
    }

}
