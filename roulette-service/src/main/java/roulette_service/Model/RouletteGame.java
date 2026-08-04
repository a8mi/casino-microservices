package roulette_service.Model;

import java.math.BigDecimal;
import jakarta.persistence.*;

@Entity
@Table(name="rouletteGames")
public class RouletteGame implements IRouletteGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private boolean result;

    protected RouletteGame(){}

    private RouletteGame(Long userId, BigDecimal amount, boolean result){
        this.userId = userId;
        this.amount = amount;
        this.result = result;
    }

    public static IRouletteGame create(Long userId, BigDecimal amount, boolean result){
        return new RouletteGame(userId, amount, result);
    }

    @Override
    public Long getGameId(){ return gameId; }

    @Override
    public Long getUserId(){ return userId; }

    @Override
    public BigDecimal getAmount(){ return amount; }

    @Override
    public boolean getResult(){return result; }

    @Override
    public void setGameId(Long gameId){ this.gameId = gameId;}

    @Override
    public void setUserId(Long userId){ this.userId = userId;}

    @Override
    public void setAmount(BigDecimal amount){ this.amount = amount;}

    @Override
    public void setResult(boolean result){this.result = result;}

    
}
