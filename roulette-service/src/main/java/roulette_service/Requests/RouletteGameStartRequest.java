package roulette_service.Requests;

public record RouletteGameStartRequest(String type, int[] bet, float amount) implements IRouletteGameStartRequest{

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
