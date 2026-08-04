package roulette_service.View;
import java.math.BigDecimal;

public interface IRouletteGameView {
    Long getUserId();
    Long getGameId();
    BigDecimal getAmount();
    boolean getResult();

}