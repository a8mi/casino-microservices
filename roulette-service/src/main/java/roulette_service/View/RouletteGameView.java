package roulette_service.View;

import java.math.BigDecimal;

import roulette_service.Model.IRouletteGame;

public record RouletteGameView(Long userId, Long gameId, BigDecimal amount, boolean result) implements IRouletteGameView{


        public static IRouletteGameView of(IRouletteGame game) {
        return new RouletteGameView(
                game.getUserId(),
                game.getGameId(),
                game.getAmount(),
                game.getResult()
        );
    }

    @Override
    public BigDecimal getAmount() {
        return this.amount;
    }

    @Override
    public boolean getResult() {
        return this.result;
    }

    @Override
    public Long getUserId() {
        return this.userId;
    }

    @Override
    public Long getGameId() {
        return this.gameId;
    }
    
}
