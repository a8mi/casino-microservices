package roulette_service.Model;

import java.math.BigDecimal;
import roulette_service.Gamelogic.ERouletteGameType;

public interface IRouletteGameFactory {

    IRouletteGame create(Long userId, ERouletteGameType betType, int[] bet, BigDecimal wager, int result, boolean isWin, BigDecimal betReturn);
}