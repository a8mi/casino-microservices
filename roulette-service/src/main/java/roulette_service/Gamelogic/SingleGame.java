package roulette_service.Gamelogic;

import roulette_service.Requests.IRouletteGameStartRequest;

public class SingleGame implements IRouletteGameLogic{

    private IRouletteGameStartRequest rouletteGameStartRequest;
    private int[] bet;
    private boolean isWin;
    private float betReturn;
    private int ballPosition;

    private SingleGame(IRouletteGameStartRequest rouletteGameStartRequest, int ballPosition){
        this.rouletteGameStartRequest = rouletteGameStartRequest;
        this.bet = new int[] {rouletteGameStartRequest.getBet()[0]};
        this.ballPosition = ballPosition;
        this.isWin = false;
    }

    @Override
    public void playGame() {        

        float wager = rouletteGameStartRequest.getWager();
        this.isWin = (this.ballPosition == this.bet[0]);
        this.betReturn = this.isWin? wager * 36 : 0;
    }

    public static IRouletteGameLogic create(IRouletteGameStartRequest rouletteGameStartRequest, int ballPosition) {
        SingleGame singleGame = new SingleGame(rouletteGameStartRequest, ballPosition);
        return singleGame;
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
