package roulette_service.Model;

import java.math.BigDecimal;

public class RouletteGameFactory implements IRouletteGameFactory {
    
    @Override
    public IRouletteGame create(Long userId, BigDecimal amount, boolean result) {
        return RouletteGame.create(userId, amount, result);
    }
    
}
