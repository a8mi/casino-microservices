package roulette_service.Gamelogic;

import roulette_service.Requests.IRouletteGameStartRequest;
import roulette_service.Utils.RouletteGameValidation;

public class ColumnGame implements IRouletteGameLogic{

    private IRouletteGameStartRequest rouletteGameStartRequest;
    private int[] bet;
    private boolean isWin;
    private float betReturn;
    private int ballPosition;

    private ColumnGame(IRouletteGameStartRequest rouletteGameStartRequest, int ballPosition){
        this.rouletteGameStartRequest = rouletteGameStartRequest;
        this.bet = new int[] {rouletteGameStartRequest.getBet()[0]};
        this.ballPosition = ballPosition;
        this.isWin = false;
    }

    @Override
    public void playGame() {        

        float wager = rouletteGameStartRequest.getWager();
    
        if (rouletteGameStartRequest.getBet()[0] == 1){
            this.isWin = this.ballPosition % 3 == 1;
        } else if (rouletteGameStartRequest.getBet()[0] == 2){
            this.isWin = this.ballPosition % 3 == 2;
        } else if (rouletteGameStartRequest.getBet()[0] == 0){
            this.isWin = this.ballPosition % 3 == 0;
        } 

        if (this.ballPosition == 0){
            this.isWin = false;
        }

       this.betReturn = this.isWin? wager * 3 : 0;
    }

    public static IRouletteGameLogic create(IRouletteGameStartRequest rouletteGameStartRequest, int ballPosition) {        
        int[] userBet = rouletteGameStartRequest.getBet();

        if (userBet.length != 1 || RouletteGameValidation.validNums(userBet, 1, 3)) return null;
        
        ColumnGame columnGame = new ColumnGame(rouletteGameStartRequest, ballPosition);
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
    public float getBetReturn() {
        return this.betReturn;
    }

    @Override
    public int getBallPosition(){
        return this.ballPosition;
    }
    
}
