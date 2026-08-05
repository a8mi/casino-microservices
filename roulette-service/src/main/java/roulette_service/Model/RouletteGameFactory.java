package roulette_service.Model;

import java.math.BigDecimal;

import roulette_service.Gamelogic.ERouletteGameType;

public class RouletteGameFactory implements IRouletteGameFactory {
    
    @Override
    public IRouletteGame create(Long userId, ERouletteGameType betType,
            int[] bet, BigDecimal amount, int result,
            boolean isWin, BigDecimal cashout) {
                return RouletteGame.create(userId, betType, bet, amount, result, isWin, cashout);
        }
    
}
