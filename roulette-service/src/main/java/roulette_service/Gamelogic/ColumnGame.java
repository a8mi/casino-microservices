package roulette_service.Gamelogic;

import java.util.Random;
import roulette_service.Requests.IRouletteGameStartRequest;
import roulette_service.Utils.RouletteValidation;

public class ColumnGame implements IRouletteGame{

    private IRouletteGameStartRequest rouletteGameStartRequest;
    private int[] bet;
    private boolean isWin;
    private float payout;
    private int result;

    private ColumnGame(IRouletteGameStartRequest rouletteGameStartRequest){
        this.rouletteGameStartRequest = rouletteGameStartRequest;
        this.bet = new int[] {rouletteGameStartRequest.getBet()[0]};
        this.isWin = false;
    }

    @Override
    public void playGame() {        

        float amount = rouletteGameStartRequest.getAmount();
        Random random = new Random();
        this.result = random.nextInt(37);
    
        if (rouletteGameStartRequest.getBet()[0] == 1){
            this.isWin = this.result % 3 == 1;
        } else if (rouletteGameStartRequest.getBet()[0] == 2){
            this.isWin = this.result % 3 == 2;
        } else if (rouletteGameStartRequest.getBet()[0] == 0){
            this.isWin = this.result % 3 == 0;
        } 

        if (this.result == 0){
            this.isWin = false;
        }

       this.payout = this.isWin? amount * 2 : - amount;
    }

    public static IRouletteGame create(IRouletteGameStartRequest rouletteGameStartRequest) {        
        int[] userBet = rouletteGameStartRequest.getBet();

        if (userBet.length != 1 || RouletteValidation.validNums(userBet, 1, 3)) return null;
        
        ColumnGame columnGame = new ColumnGame(rouletteGameStartRequest);
        return columnGame;
    }

    @Override
    public int[] getBet(){
        return this.bet;
    }

    @Override
    public boolean getIsWin() {
        return this.isWin;
    }

    @Override
    public float getPayout() {
        return this.payout;
    }

    @Override
    public int getResult(){
        return this.result;
    }
    


}
