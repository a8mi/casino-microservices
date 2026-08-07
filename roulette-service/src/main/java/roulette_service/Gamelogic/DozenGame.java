package roulette_service.Gamelogic;

import roulette_service.Requests.IRouletteGameStartRequest;
import roulette_service.Utils.RouletteGameValidation;

public class DozenGame implements IRouletteGameLogic{

    private IRouletteGameStartRequest rouletteGameStartRequest;
    private int[] bet;
    private boolean isWin;
    private float betReturn;
    private int ballPosition;

    private DozenGame(IRouletteGameStartRequest rouletteGameStartRequest, int ballPosition){
        this.rouletteGameStartRequest = rouletteGameStartRequest;
        this.bet = new int[] {rouletteGameStartRequest.getBet()[0]};
        this.ballPosition = ballPosition;
        this.isWin = false;
    }

    @Override
    public void playGame() {        

        float wager = rouletteGameStartRequest.getWager();
    
        if(this.ballPosition > 24 ){
            this.isWin = (this.bet[0] == 3);
        } else if (ballPosition > 12){
            this.isWin = (this.bet[0] == 2);
        } else if (ballPosition > 0){
            this.isWin = (this.bet[0] == 1);
        }
            this.betReturn = this.isWin? wager * 4 : 0;
    }

    public static IRouletteGameLogic create(IRouletteGameStartRequest rouletteGameStartRequest, int ballPosition) {        
        int[] userBet = rouletteGameStartRequest.getBet();

        if (userBet.length != 1 || RouletteGameValidation.validNums(userBet, 1, 3)) return null;
        
        DozenGame dozenGame = new DozenGame(rouletteGameStartRequest, ballPosition);
        return dozenGame;
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
