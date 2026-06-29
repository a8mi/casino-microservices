package roulette_service.View;

public record RouletteGameView(String betType, int[] bet, float amount, int result, boolean isWin, float pay) implements IRouletteGameView {

    @Override
    public String getBetType() {
        return betType;
    }

    @Override
    public int[] getBet() {
        return bet;
    }

    @Override
    public float getAmount() {
        return amount;
    }

    @Override
    public int getResult() {
        return result;
    }

    @Override
    public boolean getIsWin() {
        return isWin;
    }

    @Override
    public float getPay() {
        return pay;
    }
    
}
