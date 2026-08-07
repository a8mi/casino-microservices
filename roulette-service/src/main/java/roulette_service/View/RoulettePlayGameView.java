package roulette_service.View;

import java.math.BigDecimal;

import roulette_service.Gamelogic.ERouletteGameType;
import roulette_service.Model.IRouletteGame;

public record RoulettePlayGameView(Long user, ERouletteGameType betType, int[] bet, BigDecimal wager,
                                   int ballPosition, boolean winning, BigDecimal amount) implements IRoulettePlayGameView {
    
    
    public static IRoulettePlayGameView of (IRouletteGame game){
        return new RoulettePlayGameView(
                game.getUserId(),
                game.getBetType(),
                game.getBet(),
                game.getWager(),
                game.getResult(),
                game.getIsWin(),
                game.getBetReturn().subtract(game.getWager())
            );
    }

    @Override
    public Long getUserId() {
       return user;
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
    public BigDecimal getWager() {
        return wager;
    }

    @Override
    public int getBallPosition() {
        return ballPosition;
    }

    @Override
    public boolean getIsWin() {
        return winning;
    }

    @Override
    public BigDecimal getAmount() {
        return amount;
    }
        
}
