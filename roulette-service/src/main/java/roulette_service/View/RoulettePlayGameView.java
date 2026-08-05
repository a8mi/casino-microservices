package roulette_service.View;

import roulette_service.Gamelogic.ERouletteGameType;

public record RoulettePlayGameView(Long userId, ERouletteGameType betType, int[] bet, float wager,
                                   int result, boolean isWin, float betReturn) implements IRoulettePlayGameView {
    @Override
    public Long getUserId() {
       return userId;
    }
    @Override
    public ERouletteGameType getBetType() {
        return betType;
    }

    @Override
    public int[] getBet() {
        return bet;
    }

    @Override
    public float getWager() {
        return wager;
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
    public float getBetReturn() {
        return betReturn;
    }
        
}
