package slotmachine_service.View;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import slotmachine_service.Model.SlotGame;

public record GameView(
        Long id,
        Long user,
        boolean winning,
        BigDecimal amount,
        BigDecimal wager,
        BigDecimal payout,
        List<String> slotStates,
        Instant playedAt
) implements IGameView
{
        public static GameView of(SlotGame game) {
        return new GameView(
                game.getGameId(),
                game.getUserId(),
                game.isWinning(),
                game.getAmount(),
                game.getWager(),
                game.getPayout(),
                game.getSymbols().stream().map(Enum::name).toList(),
                game.getPlayedAt()
        );
    }

        @Override
        public Long getId() {return this.id;}

        @Override
        public Long getUser() {return this.user;}

        @Override
        public boolean getWinning() {return this.winning;}

        @Override
        public BigDecimal getAmount() {return this.amount;}

        @Override
        public BigDecimal getWager() {return this.wager;}

        @Override
        public BigDecimal getPayout() {return this.payout;}

        @Override
        public List<String> getSlotStates() {return this.slotStates;}

        @Override
        public Instant getPlayedAt() {return this.playedAt;}
}
