package roulette_service.Model;

import java.math.BigDecimal;

public interface IRouletteGameFactory {

    IRouletteGame create(Long userId, BigDecimal amount, boolean result);
}