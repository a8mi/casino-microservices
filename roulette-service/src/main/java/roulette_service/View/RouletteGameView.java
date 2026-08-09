package roulette_service.View;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

import roulette_service.Gamelogic.ERouletteGameType;
import roulette_service.Model.IRouletteGame;

public record RouletteGameView(Long game_id, Long user_id, BigDecimal wager, int[] bet, int ball_position,
                               ERouletteGameType bet_type, boolean winning,
                                BigDecimal amount, String played_at) implements IRouletteGameView{

        public static IRouletteGameView of(IRouletteGame game) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy, HH:mm:ss");
            return new RouletteGameView(
                game.getGameId(),
                game.getUserId(),
                game.getWager(),
                game.getBet(),
                game.getResult(),
                game.getBetType(),
                game.getIsWin(),
                game.getBetReturn().subtract(game.getWager()),
                game.getDate().format(formatter));
    }

    @Override
    public BigDecimal getWager() { return this.wager; }

    @Override
    public Long getUserId() {
        return this.user_id;
    }

    @Override
    public int getBallPosition() {
        return this.ball_position;
    }

    @Override
    public int[] getBet() {
        return bet;
    }

    @Override
    public Long getGameId() {return this.game_id; }

    @Override
    public ERouletteGameType getBetType() {return bet_type;}
    
    @Override
    public boolean getIsWin() {return winning; }

    @Override
    public BigDecimal getAmount() {return amount; }

    @Override
    public String getDate() {return played_at; }
    
}
