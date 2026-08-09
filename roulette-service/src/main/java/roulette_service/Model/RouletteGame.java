package roulette_service.Model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import roulette_service.Gamelogic.ERouletteGameType;

@Entity
@Table(name="rouletteGames")
public class RouletteGame implements IRouletteGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameId;

   @Column(nullable = false)
    private Long user_id;
    
    @Column(nullable = false)
    private ERouletteGameType bet_type;

    @Column
    private int[] bet;

    @Column(nullable = false)
    private BigDecimal wager;

    @Column(nullable = false)
    private int result;

    @Column(nullable = false)
    private boolean winning;

    @Column(nullable = false)
    private BigDecimal betReturn;

   @Column(nullable = false)
    private LocalDateTime date;

    protected RouletteGame(){}

    private RouletteGame(Long userId, ERouletteGameType betType, int[] bet, BigDecimal wager,
                        int result, boolean isWin, BigDecimal betReturn)
    {
        this.user_id = userId;
        this.bet_type = betType;
        this.bet = bet;
        this.wager = wager;
        this.result = result;
        this.winning = isWin;
        this.betReturn = betReturn;
        this.date = LocalDateTime.now();
    }


    public static IRouletteGame create(Long userId, ERouletteGameType betType, int[] bet, BigDecimal wager,
                                        int result, boolean isWin, BigDecimal betReturn) 
    {        
        return new RouletteGame(userId, betType, bet, wager, result, isWin, betReturn);
    }

    @Override
    public Long getGameId() {return gameId;}

    @Override
    public Long getUserId() {return user_id;}

    @Override
    public ERouletteGameType getBetType() {return bet_type;}

    @Override
    public int[] getBet() {return bet;}

    @Override
    public BigDecimal getWager() {return wager;}

    @Override
    public int getResult() {return result;}

    @Override
    public boolean getIsWin() {return winning;}

    @Override
    public BigDecimal getBetReturn() {return betReturn;}

    @Override
    public LocalDateTime getDate() {return date;}
    
}
