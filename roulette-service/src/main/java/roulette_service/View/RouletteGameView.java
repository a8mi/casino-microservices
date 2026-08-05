package roulette_service.View;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

import roulette_service.Gamelogic.ERouletteGameType;
import roulette_service.Model.IRouletteGame;

public record RouletteGameView(Long gameId, Long userId, BigDecimal wager,
                               ERouletteGameType betType, boolean isWin,
                                BigDecimal betReturn, String date) implements IRouletteGameView{

        public static IRouletteGameView of(IRouletteGame game) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy, HH:mm:ss");
            return new RouletteGameView(
                game.getGameId(),
                game.getUserId(),
                game.getWager(),
                game.getBetType(),
                game.getIsWin(),
                game.getBetReturn(),
                game.getDate().format(formatter));
    }

    @Override
    public BigDecimal getWager() { return this.wager; }

    @Override
    public Long getUserId() {
        return this.userId;
    }

    @Override
    public Long getGameId() {return this.gameId; }

    @Override
    public ERouletteGameType getBetType() {return betType;}
    
    @Override
    public boolean getIsWin() {return isWin; }

    @Override
    public BigDecimal getBetReturn() {return betReturn; }

    @Override
    public String getDate() {return date; }
    
}
